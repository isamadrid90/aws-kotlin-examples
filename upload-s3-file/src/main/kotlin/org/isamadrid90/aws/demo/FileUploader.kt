package org.isamadrid90.aws.demo

import java.io.File

interface FileUploader {
    suspend operator fun invoke(file: File): Result<Unit>
}
