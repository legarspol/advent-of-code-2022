package org.example

import java.awt.Point
import java.io.File
import java.nio.file.Files


lateinit var dirs: String
var index = 0
lateinit var pos: Point

enum class Direction(val value: Int) {
    RIGHT(0),
    BOTTOM(1),
    LEFT(2),
    TOP(3);

    companion object {
        fun fromInt(value: Int) = Direction.values().first { it.value == value }
    }
}

fun Char.toDir(): Direction {
    var i = when (this) {
        'L' -> direction.value - 1
        'R' -> direction.value + 1
        else -> throw IllegalArgumentException()
    }
    if (i < 0) i += 4
    i %= 4
    return Direction.fromInt(i)
}

var direction = Direction.RIGHT

fun Point.outOfBound(): Boolean {
    return this.x < 0 || this.y < 0 || this.y >= map.size || this.x >= map[this.y].length ||
            map[this.y][this.x] == ' '

}

lateinit var map: List<String>
fun main() {
    val file = String(Files.readAllBytes(File("day22").toPath()))
    val input = file.split("\n\n")
    map = input[0].split("\n")
    dirs = input[1]
    println(map)
    println("--")
    println(dirs)

    //find first pos
    var i = 0
    for (c in map[0]) {

        if (c == '.') {
            break
        }
        i++
    }
    println(i)

    //0 for right (>), 1 for down (v), 2 for left (<), and 3 for up (^)
    pos = Point(i, 0)

    while (execNextCommand()) {
        printPost()
    }
    val score = getScore(pos, direction.value)
    println(score)
}

private fun printPost() {
    println(pos.toString() + " dir: " + direction)
}

private fun execNextCommand(): Boolean {
    var number = ""
    var nextDir: Direction? = null
    if (index >= dirs.length)
        return false
//    for (i in index..dirs.length) {
    if (dirs[index] in '0'..'9') {
        while (index < dirs.length && dirs[index] >= '0' && dirs[index] <= '9') {
            number += dirs[index]
            index++
        }
    } else {
        nextDir = dirs[index].toDir()
        index++
    }

    if (nextDir != null) {
        direction = nextDir
    } else if (number != "") {
        val move = number.toInt()
        for (i in 0 until move) {
            pos = nextPos(pos)
            printPost()


        }
    } else {
        throw java.lang.IllegalArgumentException("What was that command?")
    }
    return true
}

private fun nextPos(pos: Point): Point {
    var nextPoint = when (direction) {
        Direction.RIGHT -> Point(pos.x + 1, pos.y)
        Direction.LEFT -> Point(pos.x - 1, pos.y)
        Direction.BOTTOM -> Point(pos.x, pos.y + 1)
        Direction.TOP -> Point(pos.x, pos.y - 1)
    }

    if (nextPoint.outOfBound()) {
        nextPoint = when (direction) {
            Direction.RIGHT -> wrapAround(nextPoint) { pos: Point -> Point(pos.x - 1, pos.y) }
            Direction.LEFT -> wrapAround(nextPoint) { pos: Point -> Point(pos.x + 1, pos.y) }
            Direction.TOP -> wrapAround(nextPoint) { pos: Point -> Point(pos.x, pos.y + 1) }
            Direction.BOTTOM -> wrapAround(nextPoint) { pos: Point -> Point(pos.x, pos.y - 1) }
        }
    }
    if (map[nextPoint.y][nextPoint.x] == '#')
        return pos
    return nextPoint
}


fun wrapAround(pos: Point, function: (Point) -> (Point)): Point {
    var temp = pos
    while (true) {
        val nextPos = function(temp)
        if (nextPos.outOfBound()) {
            return temp
        } else {
            temp = nextPos
        }
    }
}


private fun getScore(pos: Point, direction: Int) =
    (pos.y + 1) * 1000 + (pos.x + 1) * 4 + direction