package org.isamadrid90.aws.demo

import io.mockk.called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class UploadFileTest {
    private lateinit var uploader: FileUploader
    private lateinit var uploadFile: UploadFile

    @BeforeEach
    fun setUp() {
        uploader = mockk()
        uploadFile = UploadFile(uploader)
    }

    @Test
    fun `should upload the file successfully`() {
        runBlocking {
            val file = `given an existing file`()
            `given file uploader always is successful`()
            val result = `when an existing file is been uploaded`(file)
            `then the result should be successful`(result)
            `then the file uploader should have been called`()
        }
    }

    @Test
    fun `should fail when file not exists`() {
        runBlocking {
            val file = `given an not existing file`()
            val result = `when an existing file is been uploaded`(file)
            `then the result should be failed`(result)
            `then the file uploader should not have been called`()
        }
    }

    private fun `given an existing file`(): File {
        return ExistingFile("path/to/file.txt")
    }

    private fun `given an not existing file`(): File {
        return NotExistingFile("path/to/file.txt")
    }

    private fun `given file uploader always is successful`() {
        coEvery { uploader(any()) } returns Result.success(Unit)
    }

    private suspend fun `when an existing file is been uploaded`(file: File) = uploadFile(file)

    private fun `then the file uploader should have been called`() {
        coVerify { uploader(File("path/to/file.txt")) }
    }

    private fun `then the file uploader should not have been called`() {
        verify { uploader wasNot called }
    }

    private fun `then the result should be successful`(result: Result<Unit>) {
        assertEquals(Result.success(Unit), result)
    }

    private fun `then the result should be failed`(result: Result<Unit>) {
        assertEquals(Result.failure(FilePathNotExists("path/to/file.txt")), result)
    }
}
