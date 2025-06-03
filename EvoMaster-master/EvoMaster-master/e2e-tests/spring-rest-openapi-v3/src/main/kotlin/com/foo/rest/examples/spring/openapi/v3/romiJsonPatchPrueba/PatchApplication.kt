package com.foo.rest.examples.spring.openapi.v3.romiJsonPatchPrueba

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.github.fge.jsonpatch.JsonPatch
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@RestController
@RequestMapping("/api/patch")
open class JsonPatchApplication {

    data class Person(
        var name: String = "",
        var age: Int = 0
    )

    @PatchMapping(consumes = ["application/json-patch+json"], produces = ["application/json"])
    fun patchPerson(@RequestBody patch: JsonPatch): ResponseEntity<Person> {
        val original = Person("Alice", 30)

        // Convertir original a JSON Node
        val mapper = ObjectMapper()
        val originalNode = mapper.convertValue(original, JsonNode::class.java)

        // Aplicar el patch
        val patchedNode = patch.apply(originalNode)

        // Convertir el resultado de nuevo a Person
        val patchedPerson = mapper.treeToValue(patchedNode, Person::class.java)

        return ResponseEntity.ok(patchedPerson)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<JsonPatchApplication>(*args)
        }
    }
}

