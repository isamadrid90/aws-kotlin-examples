package org.isamadrid90.aws.demo

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.PutObjectRequest
import aws.smithy.kotlin.runtime.auth.awscredentials.CredentialsProvider
import aws.smithy.kotlin.runtime.content.asByteStream
import aws.smithy.kotlin.runtime.net.Host
import aws.smithy.kotlin.runtime.net.Scheme
import aws.smithy.kotlin.runtime.net.url.Url
import java.io.File
import java.net.URL

class S3FileUploader(private val s3ClientConfig: S3ClientConfig) : FileUploader {
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

    override suspend fun invoke(file: File): Result<Unit> {
        return runCatching {
            client.use {
                it.putObject(
                    PutObjectRequest {
                        bucket = s3ClientConfig.bucketName
                        key = file.name
                        metadata = mapOf()
                        body = file.asByteStream()
                    },
                )
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
