package org.isamadrid90.aws.demo

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.PutObjectRequest
import aws.smithy.kotlin.runtime.content.asByteStream
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
import java.net.URI
import java.nio.charset.StandardCharsets
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Testcontainers
class DownloadFileIntegrationTest {
    @Test
    fun `should download file successfully`() {
        val s3Endpoint = localstack.getEndpointOverride(LocalStackContainer.Service.S3)
        `given there is a uploaded file in`("file.txt", s3Endpoint)
        val result =
            runBlocking {
                DownloadFile(
                    S3FileDownloader(
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
                        path = "file.txt",
                    )
                }
            }
        `then the file downloaded content is correct`(result)
    }

    private fun `then the file downloaded content is correct`(response: Result<String>) {
        assertTrue(response.isSuccess)
        assertEquals(
            InputStreamReader(file.inputStream(), StandardCharsets.UTF_8).readText(),
            response.getOrNull(),
        )
    }

    private fun `given there is a uploaded file in`(
        path: String,
        s3Endpoint: URI,
    ) {
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
                val res =
                    it.putObject(
                        PutObjectRequest {
                            bucket = "my-bucket"
                            key = "file.txt"
                            body = file.asByteStream()
                        },
                    )
            }
        }
    }

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

        private val file = File("./src/test/resources/file.txt")
    }
}
