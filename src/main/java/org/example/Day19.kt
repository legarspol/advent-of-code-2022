package org.example

import java.io.File
import java.nio.file.Files


fun main() {
    val file = String(Files.readAllBytes(File("day19").toPath()))
    Day19().test(file)
}


class Day19 {
    class Cost(val ore: Int, val clay: Int, val obsidian: Int)
    class Blueprint(
        val name: Int,
        val oreRobCost: Cost,
        val clayRobCost: Cost,
        val obsRobCost: Cost,
        val geoRobCost: Cost
    )

    fun test(file: String) {
        val lines = file.split("\n")
        for (l in lines) {

        }
    }

}