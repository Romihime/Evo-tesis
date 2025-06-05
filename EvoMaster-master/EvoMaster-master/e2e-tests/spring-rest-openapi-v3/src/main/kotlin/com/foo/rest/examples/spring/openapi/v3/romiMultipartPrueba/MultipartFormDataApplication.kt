package com.foo.rest.examples.spring.openapi.v3.romiMultipartPrueba

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@RestController
@RequestMapping("/api/multi")
open class MultipartFormDataApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(MultipartFormDataApplication::class.java, *args)
        }
    }

    // 1. Recibe multipart (archivo + texto), responde STRING
    @PostMapping(
        path = ["/upload"],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.TEXT_PLAIN_VALUE]
    )
    fun handleUpload(
        @RequestPart("file") file: MultipartFile,
        @RequestPart("description") description: String
    ): ResponseEntity<String> {
        return if (!file.isEmpty && description.isNotBlank()) {
            val msg = "File: ${file.originalFilename}, Description: $description"
            ResponseEntity.ok(msg)
        } else {
            ResponseEntity.badRequest().body("Invalid multipart content")
        }
    }
}
