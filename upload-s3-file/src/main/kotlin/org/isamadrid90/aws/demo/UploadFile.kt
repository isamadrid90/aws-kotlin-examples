package org.isamadrid90.aws.demo

import java.io.File

class UploadFile(private val uploader: FileUploader) {
    suspend operator fun invoke(file: File): Result<Unit> {
        return file
            .takeIf { it.exists() }
            ?.run { uploader(File(path)) }
            ?: Result.failure(FilePathNotExists(file.path))
    }
}

data class FilePathNotExists(val path: String) : Throwable("The path $path doesn't exists")
