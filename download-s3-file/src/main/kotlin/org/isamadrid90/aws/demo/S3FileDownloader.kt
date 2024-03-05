package org.isamadrid90.aws.demo

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.smithy.kotlin.runtime.auth.awscredentials.CredentialsProvider
import aws.smithy.kotlin.runtime.content.decodeToString
import aws.smithy.kotlin.runtime.net.Host
import aws.smithy.kotlin.runtime.net.Scheme
import aws.smithy.kotlin.runtime.net.url.Url
import java.net.URL

class S3FileDownloader(private val s3ClientConfig: S3ClientConfig) : FileDownloader {
    private val client =
        S3Client {
            region = s3ClientConfig.region
            endpointUrl =
                Url {
                    scheme = Scheme.parse(s3ClientConfig.url.protocol)
                    host = Host.parse(s3ClientConfig.url.host)
                    port = s3ClientConfig.url.port
                }
            credentialsProvider = s3ClientConfig.credentials
        }

    override suspend fun invoke(path: String): Result<String?> {
        return runCatching {
            client.getObject(
                GetObjectRequest {
                    bucket = s3ClientConfig.bucketName
                    key = path
                },
            ) { response ->
                response.body?.decodeToString()
            }
        }
    }
}

data class S3ClientConfig(
    val bucketName: String,
    val region: String,
    val url: URL,
    val credentials: CredentialsProvider,
)
