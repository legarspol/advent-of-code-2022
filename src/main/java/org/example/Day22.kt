package org.example

import java.awt.Point
import java.io.File
import java.nio.file.Files


lateinit var dirs: String
var index = 0
lateinit var pos: Point


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

val SIZE = 50 - 1
//val SIZE = 4 - 1
fun main() {
    val file = String(Files.readAllBytes(File("day22").toPath()))
    val input = file.split("\n\n")
    map = input[0].split("\n")
    dirs = input[1]

    //find first pos
    var i = 0
    for (c in map[0]) {

        if (c == '.') {
            break
        }
        i++
    }
    //0 for right (>), 1 for down (v), 2 for left (<), and 3 for up (^)
    pos = Point(i, 0)

    while (execNextCommand()) {
        print("command executed: ")
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
        println("command: " + nextDir)
        direction = nextDir

    } else if (number != "") {
        val move = number.toInt()
        println("command: move " + move)

        for (i in 0 until move) {
            nextPos()
            printPost()


        }
    } else {
        throw java.lang.IllegalArgumentException("What was that command?")
    }
    return true
}

fun Point.getFace(): Face {
    for (face in Face.values()) {
        val min = face.min
        if (this.x in min.x..(min.x + SIZE) && this.y in min.y..(min.y + SIZE))
            return face
    }
    throw java.lang.IllegalArgumentException("Facenot found" + this)
}


enum class Direction(val value: Int) {
    RIGHT(0),
    BOTTOM(1),
    LEFT(2),
    TOP(3);

    companion object {
        fun fromInt(value: Int) = Direction.values().first { it.value == value }
    }
}

class Trans(val face: Face, val direction: Direction)
//enum class Face(val min: Point) {
//    One(Point(8, 0)),
//    Two(Point(0, 4)),
//    Three(Point(4, 4)),
//    Four(Point(8, 4)),
//    Five(Point(8, 8)),
//    Six(Point(12, 8));
//
//    fun getTrans(direction: Direction): Trans? = when (this) {
//        One -> listOf(Trans(Six, Direction.LEFT), null, Trans(Three, Direction.BOTTOM), Trans(Two, Direction.BOTTOM))
//        Two -> listOf(null, Trans(Five, Direction.TOP), Trans(Six, Direction.TOP), Trans(One, Direction.TOP))
//        Three -> listOf(null, Trans(Five, Direction.RIGHT), null, Trans(One, Direction.RIGHT))
//        Four -> listOf(Trans(Six, Direction.BOTTOM), null, null, null)
//        Five -> listOf(null, Trans(Two, Direction.TOP), Trans(Three, Direction.TOP), null)
//        Six -> listOf(Trans(One, Direction.LEFT), Trans(Two, Direction.RIGHT), null, Trans(Four, Direction.LEFT))
//    }[direction.value]
//
//}

enum class Face(val min: Point) {
    One(Point(50, 0)),
    Two(Point(100, 0)),
    Three(Point(50, 50)),
    Four(Point(0, 100)),
    Five(Point(50, 100)),
    Six(Point(0, 150));

    fun getTrans(direction: Direction): Trans? = when (this) {
        One -> listOf(null, null, Trans(Four, Direction.RIGHT), Trans(Six, Direction.RIGHT))
        Two -> listOf(Trans(Five, Direction.LEFT), Trans(Three, Direction.LEFT), null, Trans(Six, Direction.TOP))
        Three -> listOf(Trans(Two, Direction.TOP), null, Trans(Four, Direction.BOTTOM), null)
        Four -> listOf(null, null, Trans(One, Direction.RIGHT), Trans(Three, Direction.RIGHT))
        Five -> listOf(Trans(Two, Direction.LEFT), Trans(Six, Direction.LEFT), null, null)
        Six -> listOf(Trans(Five, Direction.TOP), Trans(Two, Direction.BOTTOM), Trans(One, Direction.BOTTOM), null)
    }[direction.value]

}

private fun nextPos() {

    var nextPoint = when (direction) {
        Direction.RIGHT -> Point(pos.x + 1, pos.y)
        Direction.LEFT -> Point(pos.x - 1, pos.y)
        Direction.BOTTOM -> Point(pos.x, pos.y + 1)
        Direction.TOP -> Point(pos.x, pos.y - 1)
    }
    var nextDirection = direction;
    if (nextPoint.outOfBound()) {
        // which face is the dude comming from
        val face = pos.getFace()
        val trans = face.getTrans(direction)
        val relx = pos.x - face.min.x;
        val rely = pos.y - face.min.y;

        val dest = trans!!.face.min
        val maxDestY = dest.y + SIZE
        val maxDestX = dest.x + SIZE
        nextPoint = when (direction) {
            Direction.TOP -> {
                when (trans.direction) {
                    Direction.RIGHT -> Point(dest.x, dest.y + relx)
                    Direction.BOTTOM -> Point(maxDestX - relx, dest.y)
                    Direction.LEFT -> Point(maxDestX, maxDestY - relx)
                    Direction.TOP -> Point(dest.x + relx, maxDestY)
                }
            }

            Direction.BOTTOM -> {
                when (trans.direction) {
                    Direction.RIGHT -> Point(dest.x, dest.y + relx)
                    Direction.BOTTOM -> Point(dest.x + relx, dest.y)
                    Direction.LEFT -> Point(maxDestX,  dest.y + relx)
                    Direction.TOP -> Point(maxDestX - relx, maxDestY)
                }
            }

            Direction.RIGHT -> {
                when (trans.direction) {
                    Direction.RIGHT -> Point(dest.x, dest.y + rely)
                    Direction.BOTTOM -> Point(maxDestX - rely, dest.y)
                    Direction.LEFT -> Point(maxDestX, maxDestY - rely)
                    Direction.TOP -> Point(dest.x + rely, maxDestY)
                }
            }

            Direction.LEFT -> {
                when (trans.direction) {
                    Direction.RIGHT -> Point(dest.x, maxDestY - rely)
                    Direction.BOTTOM -> Point(dest.x+rely, dest.y)
                    Direction.LEFT -> Point(maxDestX, rely)
                    Direction.TOP -> Point(maxDestX - rely, maxDestY)
                }
            }
        }
        nextDirection = trans.direction

    }
    if (map[nextPoint.y][nextPoint.x] != '#') {
        pos = nextPoint
        direction = nextDirection
    }
}


private fun getScore(pos: Point, direction: Int) =
    (pos.y + 1) * 1000 + (pos.x + 1) * 4 + direction