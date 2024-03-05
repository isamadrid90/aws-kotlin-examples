package org.isamadrid90.aws.demo

class DownloadFile(private val downloader: FileDownloader) {
    suspend operator fun invoke(path: String): Result<String> =
        downloader(path).fold(
            onFailure = {
                Result.failure(it)
            },
            onSuccess = {
                it?.let {
                    Result.success(it)
                } ?: Result.failure(FileNotExists(path))
            },
        )
}

data class FileNotExists(val path: String) : Throwable("The file at $path doesn't exists")
