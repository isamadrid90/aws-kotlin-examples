package org.isamadrid90.aws.demo

interface FileDownloader {
    suspend operator fun invoke(path: String): Result<String?>
}
