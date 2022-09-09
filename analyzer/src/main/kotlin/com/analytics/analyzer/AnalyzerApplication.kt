package com.analytics.analyzer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class AnalyzerApplication

fun main(args: Array<String>) {
    runApplication<AnalyzerApplication>(*args)
}
