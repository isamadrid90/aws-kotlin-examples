package org.isamadrid90.aws.demo

import java.io.File

data class ExistingFile(private val path: String) : File(path) {
    override fun exists() = true
}
