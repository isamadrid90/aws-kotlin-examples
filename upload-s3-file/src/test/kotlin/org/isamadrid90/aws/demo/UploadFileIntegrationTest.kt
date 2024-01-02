package org.isamadrid90.aws.demo

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.smithy.kotlin.runtime.content.toInputStream
import aws.smithy.kotlin.runtime.net.Host
import aws.smithy.kotlin.runtime.net.Scheme
import aws.smithy.kotlin.runtime.net.url.Url
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.io.File
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import kotlin.test.assertEquals

@Testcontainers
class UploadFileIntegrationTest {
    companion object {
        @Container
        val localstack =
            LocalStackContainer(DockerImageName.parse("localstack/localstack:3.0"))
                .withServices(LocalStackContainer.Service.S3)

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            localstack.execInContainer("awslocal", "s3", "mb", "s3://my-bucket")
        }
    }

    @Test
    fun `should upload file successfully`() {
        val file = File("./src/test/resources/file.txt")
        val s3Endpoint = localstack.getEndpointOverride(LocalStackContainer.Service.S3)
        val result =
            runBlocking {
                UploadFile(
                    S3FileUploader(
                        S3ClientConfig(
                            bucketName = "my-bucket",
                            region = localstack.region,
                            url = s3Endpoint.toURL(),
                            credentials =
                                StaticCredentialsProvider {
                                    accessKeyId = localstack.accessKey
                                    secretAccessKey = localstack.secretKey
                                },
                        ),
                    ),
                ).run {
                    this(
                        file = file,
                    )
                }
            }
        assertEquals(Result.success(Unit), result)
        val s3Client =
            S3Client {
                region = localstack.region
                endpointUrl =
                    Url {
                        scheme = Scheme.parse(s3Endpoint.toURL().protocol)
                        host = Host.parse(s3Endpoint.toURL().host)
                        port = s3Endpoint.toURL().port
                    }
                credentialsProvider =
                    StaticCredentialsProvider {
                        accessKeyId = localstack.accessKey
                        secretAccessKey = localstack.secretKey
                    }
            }

        runBlocking {
            s3Client.use {
                it.getObject(
                    GetObjectRequest {
                        bucket = "my-bucket"
                        key = "file.txt"
                    },
                ) { response ->
                    assertEquals(
                        InputStreamReader(file.inputStream(), StandardCharsets.UTF_8).readText(),
                        InputStreamReader(response.body?.toInputStream(), StandardCharsets.UTF_8).readText(),
                    )
                }
            }
        }
    }
}
