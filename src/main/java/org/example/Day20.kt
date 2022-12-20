package org.example

import java.io.File
import java.nio.file.Files
import java.util.*

//class Day20 {

data class Node(val value: Long, val order: Int)

fun main() {
//        BufferedReader br = new BufferedReader(
//                new InputStreamReader(new FileInputStream("day3")));
//
    val file = String(Files.readAllBytes(File("day20").toPath()))
    var order = 0;
    val list = LinkedList(
        file.split("\n")
            .map { a: String ->
                Node(a.toLong() * 811589153, order++)

            }.toList()
    )
    println(list.map { a -> a.value }.toList())

    for (j in 0 until 10) {
        for (tofind in 0 until order) {
            for ((i, node) in list.withIndex()) {
                if (node.order == tofind) {
                    var index = (i + node.value ) % (list.size - 1);
                    index = if (index < 0) list.size - 1 + index else index
                    list.removeAt(i)
                    list.add(index.toInt(), node)
                    break;
                }
            }
        }
        println(list.map { a -> a.value }.toList())

    }

    val indexOf = list.indexOf(list.first { n -> n.value == 0L; })
    val i =
        list[(indexOf + 1000) % list.size].value  +
                list[(indexOf + 2000) % list.size].value  +
                list[(indexOf + 3000) % list.size].value
    println(i)
}
//}