package com.jdhb.game

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RandomNumberGameApiApplication

fun main(args: Array<String>) {
	runApplication<RandomNumberGameApiApplication>(*args)
}
