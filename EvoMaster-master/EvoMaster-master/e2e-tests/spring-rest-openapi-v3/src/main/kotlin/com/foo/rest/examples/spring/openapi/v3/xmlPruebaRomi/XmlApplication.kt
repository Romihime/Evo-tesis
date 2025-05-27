
package com.foo.rest.examples.spring.openapi.v3.xmlPruebaRomi

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.xml.bind.annotation.XmlRootElement

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@RestController
@RequestMapping("/api/xml")
open class XmlApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(XmlApplication::class.java, *args)
        }
    }

    @PostMapping(
        consumes = [MediaType.APPLICATION_XML_VALUE],
        produces = [MediaType.APPLICATION_XML_VALUE]
    )
    fun recibirXml(@RequestBody input: Person): ResponseEntity<Person> {
        return if (input.age in 20..30) {
            ResponseEntity.ok(input.copy(name = input.name + "_aceptado"))
        } else {
            ResponseEntity.status(400).build()
        }
    }
}

@XmlRootElement
data class Person(
    var name: String = "",
    var age: Int = 0
)