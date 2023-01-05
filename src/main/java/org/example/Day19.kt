package org.example

import java.io.File
import java.nio.file.Files
import java.util.regex.Pattern


fun main() {
    val file = String(Files.readAllBytes(File("day19t").toPath()))
    Day19().test(file)
}


class Day19 {

    var pattern =
        Pattern.compile("Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.");


    data class Cost(val ore: Int, val clay: Int, val obsidian: Int)
    data class Blueprint(
        val name: Int,
        val costs: List<List<Int>>
    )

// geo  = 7 obs + 2 ore
// geo  = 7 (8 clay + 3 ore) + 2 ore
// geo  = 7 (8 (3 ore) + 3 ore) + 2 ore
// geo  = 7 obs
// geo  = 56 clay
// geo  = 191 ore


    fun test(file: String) {
        val lines = file.split("\n")
        for (l in lines) {
            val matcher = pattern.matcher(l);
            matcher.lookingAt();
            println(matcher.group(1).toInt())

            qualityLevel(
                Blueprint(
                    matcher.group(1).toInt(),
                    listOf(
                        listOf<Int>(matcher.group(2).toInt(), 0, 0, 0),
                        listOf<Int>(matcher.group(3).toInt(), 0, 0, 0),
                        listOf<Int>(matcher.group(4).toInt(), matcher.group(5).toInt(), 0, 0),
                        listOf<Int>(matcher.group(6).toInt(), 0, matcher.group(7).toInt(), 0),
                    )
                )
            )
        }
    }

    var stock = mutableListOf<Int>(0, 0, 0, 0)
    var macs = mutableListOf<Int>(1, 0, 0, 0)

    private fun qualityLevel(blueprint: Blueprint): Int {
        if (blueprint.name == 2)
            return 0;
        var ORE = 0;
        val CLAY = 1
        var OBS = 2
        val GEO = 3

        stock = mutableListOf<Int>(0, 0, 0, 0)
        macs = mutableListOf<Int>(1, 0, 0, 0)

        for (day in 1..24) {
            println("=== Minute $day ===")
            macsAtEnd = macs.toMutableList()
            decision(blueprint)

            for ((i, mac) in macs.withIndex()) {
                stock[i] += mac
            }
            println("macs: $macs")
            println("stock: $stock")
            macs = macsAtEnd
            println()

            // how long will it take me to make a geode?
            // 7 x obsidianprod + 2 geod prod
            // obsidianProd == 0
        }
        println(blueprint)
        return blueprint.name
    }

    var macsAtEnd = mutableListOf<Int>()

    private fun decision(blueprint: Blueprint) {

        // do we have a proper pipeline?
        for ((i, mac) in macs.withIndex()) {
            if (mac == 0) {
                // can I buy it?
                val cost = blueprint.costs[i]
                var newStock = mutableListOf<Int>()
                for (j in 0..3) {
                    newStock.add(stock[j] - cost[j])
                }
                val allPos: Boolean = newStock.count { a -> a >= 0 } == 4
                if (allPos) {
                    stock = newStock
                    macsAtEnd[i] += 1
                    return
                }
            }
        }
    }

}