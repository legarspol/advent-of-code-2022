package org.example

import java.awt.Point
import java.io.File
import java.lang.Integer.min
import java.nio.file.Files
import kotlin.math.max

fun main() {
    val file = String(Files.readAllBytes(File("day23").toPath()))
    Day23().test(file)
}


class Day23 {

    val map: MutableSet<Point> = mutableSetOf()
    fun test(file: String) {
        val lines = file.split("\n")
        for ((y, l) in lines.withIndex()) {
            for ((x, c) in l.withIndex()) {
                if (c == '#') {
                    map.add(Point(x, y))
                }
            }
        }

        val prop: MutableMap<Point, Point> = mutableMapOf();
        val ban: MutableSet<Point> = mutableSetOf();
        fun moveTo(nextPoint: Point, e: Point) {
            if (!ban.contains(nextPoint) && prop[nextPoint] == null) prop[nextPoint] = e
            else {
                prop.remove(nextPoint)
                ban.add(nextPoint)

            }
        }

        data class Move(val check: (Point) -> Boolean, val action: (Point) -> Unit, val name: String)

        val moves = mutableListOf<Move>(
            //top
            Move(
                { e ->
                    !(map.contains(Point(e.x - 1, e.y - 1)) ||
                            map.contains(Point(e.x, e.y - 1)) ||
                            map.contains(Point(e.x + 1, e.y - 1)))
                },
                { e -> moveTo(Point(e.x, e.y - 1), e) },
                "North"
            ),
            //bottom
            Move({ e ->
                !(map.contains(Point(e.x + 1, e.y + 1)) ||
                        map.contains(Point(e.x, e.y + 1)) ||
                        map.contains(Point(e.x - 1, e.y + 1)))
            },
                { e -> moveTo(Point(e.x, e.y + 1), e) }, "South"
            ),
            //left
            Move({ e ->
                !(map.contains(Point(e.x - 1, e.y + 1)) ||
                        map.contains(Point(e.x - 1, e.y)) ||
                        map.contains(Point(e.x - 1, e.y - 1)))
            },
                { e -> moveTo(Point(e.x - 1, e.y), e) }, "West"
            ),
            //right
            Move({ e ->
                !(map.contains(Point(e.x + 1, e.y - 1)) ||
                        map.contains(Point(e.x + 1, e.y)) ||
                        map.contains(Point(e.x + 1, e.y + 1)))
            },
                { e -> moveTo(Point(e.x + 1, e.y), e) }, "East"
            ),

            )


        println(map)
        showMap()
        for (i in 0..1000) {
            println("new round")
            for (e in map) {

                val hasANeighbour = map.contains(Point(e.x, e.y - 1)) ||
                        map.contains(Point(e.x + 1, e.y - 1)) ||
                        map.contains(Point(e.x + 1, e.y)) ||
                        map.contains(Point(e.x + 1, e.y + 1)) ||
                        map.contains(Point(e.x, e.y + 1)) ||
                        map.contains(Point(e.x - 1, e.y + 1)) ||
                        map.contains(Point(e.x - 1, e.y)) ||
                        map.contains(Point(e.x - 1, e.y - 1))
                if (hasANeighbour) {
                    for (move in moves) {
                        if (move.check(e)) {
                            move.action(e)
//                            println("$e moves ${move.name}")
                            break
                        }
                    }

                }

            }
            if (prop.size == 0 && ban.size == 0) {
                println("turn = ${i + 1}")
                return
            }
            prop.forEach { (dest, from) ->
                map.remove(from)
                map.add(dest)
                                       println("$from moves to ${dest}")

            }

            ban.clear()
            prop.clear()
            showMap()
            val first = moves.first()
            moves.remove(first)
            moves.add(first)
        }
        println(map)
        val pmin = map.reduce { e1: Point, e2: Point -> Point(min(e1.x, e2.x), min(e1.y, e2.y)) }
        val pmax = map.reduce { e1: Point, e2: Point -> Point(max(e1.x, e2.x), max(e1.y, e2.y)) }
        println(pmin)
        println(pmax)
        val i = (pmax.x - pmin.x) * (pmax.y - pmin.y)
        println(map.size)
        println(i - map.size)
    }

    private fun showMap() {
        val pmin = map.reduce { e1: Point, e2: Point -> Point(min(e1.x, e2.x), min(e1.y, e2.y)) }
        val pmax = map.reduce { e1: Point, e2: Point -> Point(max(e1.x, e2.x), max(e1.y, e2.y)) }
        for (y in pmin.y..pmax.y) {


            for (x in pmin.x..pmax.x) {
                print(if (map.contains(Point(x, y))) "#" else ".")


            }
            println()
        }
    }
}