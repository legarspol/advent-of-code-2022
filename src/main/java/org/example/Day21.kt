package org.example

import java.io.File
import java.lang.IllegalArgumentException
import java.nio.file.Files

sealed class Monkey(val name: String)
class MonkeyValue(name: String, val value: Long) : Monkey(name)
class MonkeyOp(name: String, val operation: Char, val op1: String, val op2: String) :
    Monkey(name)

fun main() {
    Day21().test();
}

class Day21 {
    val map = HashMap<String, Monkey>();
    fun test() {

//        BufferedReader br = new BufferedReader(
//                new InputStreamReader(new FileInputStream("day3")));
//
        val file = String(Files.readAllBytes(File("day21").toPath()))
        file.split("\n")
            .map { line ->
//                println(line)
                val split = line.split(": ").map { a -> a.trim() }.toList()
                val name = split[0]
                if (split[1].count { a -> a == ' ' } == 0) {
                    map[name] = MonkeyValue(name, split[1].toLong())
                } else {
                    val ops = split[1].split(" ")
                    map[name] = MonkeyOp(name, ops[1][0], ops[0], ops[2])
                }

            }
//        println(map)

        println((map["root"]!!.eval()))
    }

    private fun Monkey.eval(): Long {
        return when (this) {
            is MonkeyValue -> this.value
            is MonkeyOp -> {
                val op1 = map[this.op1]!!.eval()
                val op2 = map[this.op2]!!.eval()
                when (this.operation) {
                    '/' -> op1 / op2
                    '*' -> op1 * op2
                    '+' -> op1 + op2
                    '-' -> op1 - op2
                    else -> throw IllegalArgumentException()
                }
            }
        }
    }
}