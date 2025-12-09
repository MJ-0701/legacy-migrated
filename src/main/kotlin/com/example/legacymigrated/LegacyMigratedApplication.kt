package com.example.legacymigrated

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LegacyMigratedApplication

fun main(args: Array<String>) {
    runApplication<LegacyMigratedApplication>(*args)
}
