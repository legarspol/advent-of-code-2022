package org.example

import java.io.File
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

        val monkey = map["root"]!! as MonkeyOp
        var unk: MonkeyOp
        val op1 = map.get(monkey.op1)!!
        var known: Long
        try {
            known = op1.eval();
            unk = (map[monkey.op2] as MonkeyOp?)!!
        } catch (e: IllegalArgumentException) {
            unk = op1 as MonkeyOp;
            known = map[monkey.op2]!!.eval()
        }
        println(known)
        println(unk)
        simplify(unk, known)
    }

    private fun simplify(unkStart: MonkeyOp, knownStart: Long) {
        var unk = unkStart
        var known = knownStart
        var next: Monkey = unk
        while (next.name != "humn") {
            unk = next as MonkeyOp
            try {
                val op2 = map.get(unk.op2)!!.eval()
                known = when (unk.operation) {
                    '/' -> known * op2
                    '*' -> known / op2
                    '+' -> known - op2
                    '-' -> known + op2
                    else -> throw IllegalStateException("unknown operation")
                }
                next = map[unk.op1]!!
            } catch (e: java.lang.IllegalArgumentException) {
                val op1 = map.get(unk.op1)!!.eval()
                known = when (unk.operation) {
                    '/' -> op1 / known
                    '*' -> known / op1
                    '+' -> known - op1
                    '-' -> op1 -known
                    else -> throw IllegalStateException("unknown operation")
                }
                next = map.get(unk.op2)!!
            }
        }
        println("humn = " + known)
    }

    private fun Monkey.eval(): Long {
        return when (this) {
            is MonkeyValue -> this.value
            is MonkeyOp -> {

                val monkey1 = map[this.op1]!!
                val monkey2 = map[this.op2]!!
                if (monkey2.name == "humn" || monkey1.name == "humn") {
                    throw IllegalArgumentException();
                }
                val op1 = monkey1.eval()
                val op2 = monkey2.eval()
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