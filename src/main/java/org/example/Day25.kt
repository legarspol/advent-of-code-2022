package org.example

import java.io.File
import java.nio.file.Files


fun main() {
    val file = String(Files.readAllBytes(File("day25").toPath()))
    Day25().test(file)
}

class Day25 {
    fun test(file: String) {
        var total: Long = 0;

        val lines = file.split("\n")
        for (line in lines) {
            total += toDec(line)

        }
        println(total)
        println(toSNAFU(total))
    }

    private fun toSNAFU(n: Long): String {
        if (n == 0L) {
            return ""
        }
        var l = n % 5
        var next = n / 5
        if (l == 4L || l == 3L) {
            next ++
            l -= 5
        }
        return toSNAFU(next) + when (l) {
            2L -> "2"
            1L -> "1"
            0L -> "0"
            -1L -> "-"
            -2L -> "="
            else -> throw IllegalArgumentException(""+ l)
        }
    }


    private fun toDec(line: String): Long {
        var number: Long = 0;
        for (c in line) {
            val value = when (c) {
                '2' -> 2
                '1' -> 1
                '0' -> 0
                '-' -> -1
                '=' -> -2
                else -> throw IllegalArgumentException("$c")
            }
            number *= 5
            number += value
        }
        return number
    }
}