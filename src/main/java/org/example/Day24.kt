package org.example

import java.io.File
import java.lang.Integer.min
import java.nio.file.Files


fun main() {
    val file = String(Files.readAllBytes(File("day24").toPath()))
    Day24().test(file)
}

data class Bliz(val pos: Int, val direction: Int)
class Day24 {
    val columns = mutableMapOf<Int, MutableList<Bliz>>()
    val rows = mutableMapOf<Int, MutableList<Bliz>>()
    var max = Int.MAX_VALUE
    var dimy = 0;
    var dimx = 0;

    fun test(file: String) {
        createMap(file)

        val cycle = cycle(0, -1, 1)
        println(max)
        println(cycle)
    }

    val m = mutableMapOf<Int, Int>()
    private fun cycle(posx: Int, posy: Int, cycle: Int): Int {
        if (posx == dimx - 1 && posy == dimy) {
            max = min(max, cycle - 1)
            return 0;
        }
        val key = ((cycle * 1000) + posx) * 1000 + posx
        val get = m.get(key)
        if (get != null)
            return get
        if (cycle >= max) {
            m.put(key, -1)
            return -1;
        }

        val nextCycle = cycle + 1

        if (posx == dimx - 1 && posy == dimy - 1) {
            return cycle(posx, posy + 1, nextCycle) + 1
        }
        println("${cycle - 1} $posx-$posy max:$max")


        var mins = mutableListOf<Int>()
        if (posx == 0 && posy == -1) {
            if (isFree(0, 0, cycle))
                mins.add(cycle(0, 0, nextCycle))
            else
                mins.add(cycle(0, -1, nextCycle))
        } else {
            if (isFree(posx + 1, posy, cycle))
                mins.add(cycle(posx + 1, posy, nextCycle))
            if (isFree(posx, posy + 1, cycle))
                mins.add(cycle(posx, posy + 1, nextCycle))
            if (isFree(posx - 1, posy, cycle))
                mins.add(cycle(posx - 1, posy, nextCycle))

            if (isFree(posx, posy - 1, cycle))
                mins.add(cycle(posx, posy - 1, nextCycle))
            if (isFree(posx, posy, cycle))
                mins.add(cycle(posx, posy, nextCycle))
        }
        mins = mins.filter { a -> a >= 0 }.toMutableList()
        var value: Int;
        if (mins.size == 0) {
            value = -1;
        } else {
            value = mins.min() + 1
        }
        m.put(key, value)
        return value
    }

    private fun isFree(
        posx: Int,
        posy: Int,
        cycle: Int
    ): Boolean {
        if (posx < 0)
            return false;
        if (posx >= dimx)
            return false
        if (posy < 0)
            return false
        if (posy >= dimy)
            return false
        val setRows = (rows[posy]?.map { b -> (b.pos + b.direction * cycle) % dimx }
            ?: mutableListOf()).map { b -> if (b < 0) b + dimx else b }.toSet()
        val setCol = (columns[posx]?.map { b -> (b.pos + b.direction * cycle) % dimy }
            ?: mutableListOf()).map { b -> if (b < 0) b + dimy else b }.toSet()
        return !setRows.contains(posx) && !setCol.contains(posy)
    }

    private fun createMap(file: String) {
        val lines = file.split("\n")
        dimy = lines.size - 2
        for ((y, line) in lines.withIndex()) {
            val trimmed = line.substring(1, line.length - 1)
//            println(trimmed)
            dimx = trimmed.length
            for ((i, c) in trimmed.withIndex()) {
                if (c == '<' || c == '>') {
                    if (rows[y - 1] == null) {
                        rows[y - 1] = mutableListOf()
                    }
                    rows[y - 1]!!.add(Bliz(i, if (c == '<') -1 else 1))
                } else if (c == 'v' || c == '^') {
                    if (columns[i] == null) {
                        columns[i] = mutableListOf()
                    }
                    columns[i]!!.add(Bliz(y - 1, if (c == '^') -1 else 1))
                }
            }
        }
//        println(vert)
//        println(hori)
    }
}