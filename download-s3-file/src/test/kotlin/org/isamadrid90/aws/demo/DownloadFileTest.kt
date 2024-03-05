package org.isamadrid90.aws.demo

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DownloadFileTest {
    private lateinit var fileDownloader: FileDownloader
    private lateinit var downloadFile: DownloadFile

    @BeforeEach
    fun setUp() {
        fileDownloader = mockk()
        downloadFile = DownloadFile(fileDownloader)
    }

    @Test
    fun `should successfully access file content`() {
        runBlocking {
            val path = "path/to/s3/file.txt"
            `given there is a file at given path`()
            val result = `when is access with path`(path)
            `then the file downloader should have been called`(path)
            `then the result content is correct`(result)
        }
    }

    @Test
    fun `should fail when file does not exist`() {
        runBlocking {
            val path = "path/to/non/existing/s3/file.txt"
            `given there is no file at given path`(path)
            val result = `when is access with path`(path)
            `then the file downloader should have been called`(path)
            `then the result is failure`(result, path)
        }
    }

    private fun `given there is a file at given path`() {
        coEvery { fileDownloader.invoke(any()) } returns Result.success(FILE_CONTENT)
    }

    private fun `given there is no file at given path`(path: String) {
        coEvery { fileDownloader.invoke(any()) } returns Result.failure(FileNotExists(path))
    }

    private suspend fun `when is access with path`(path: String): Result<String> {
        return downloadFile(path)
    }

    private fun `then the result content is correct`(result: Result<String>) {
        assertEquals(Result.success(FILE_CONTENT), result)
    }

    private fun `then the result is failure`(
        result: Result<String>,
        path: String,
    ) {
        assertEquals(Result.failure(FileNotExists(path)), result)
    }

    private fun `then the file downloader should have been called`(path: String) {
        coVerify { fileDownloader(path) }
    }

    companion object {
        private const val FILE_CONTENT = "Download file with content from S3\n"
    }
}
