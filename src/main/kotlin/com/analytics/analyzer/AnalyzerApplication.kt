package com.analytics.analyzer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AnalyzerApplication

fun main(args: Array<String>) {
    runApplication<AnalyzerApplication>(*args)
}
