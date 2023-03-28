package de.nikstack.niklist_server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NiklistServerApplication

fun main(args: Array<String>) {
    runApplication<NiklistServerApplication>(*args)
}
