//package org.example
//
//import java.io.File
//import java.nio.file.Files
//import kotlin.math.max
//import kotlin.math.min
//
//fun main() {
//    println("Score: " + Day18().test("day18t"))
//}
//
//class Day18 {
//    private lateinit var points: HashSet<String>
//    private lateinit var visited: HashSet<String>
//
//    fun test(pathname: String): Int {
////        System.out.println("Hello world!");
////        BufferedReader br = new BufferedReader(
////                new InputStreamReader(new FileInputStream("day3")));
////
//        val file = String(Files.readAllBytes(File(pathname).toPath()))
//        val fileString = file.split("\n")
//        points = HashSet(fileString)
//        visited = HashSet()
//        var surface = 0
//        for (next in points) {
//            if (!visited.contains(next)) {
//                val ints = mutableListOf<List<Int>>()
//                val findSurface = findSurface(keyToInts(next), ints)
//
//                val initial = mutableListOf(
//                    Int.MAX_VALUE,
//                    Int.MAX_VALUE,
//                    Int.MAX_VALUE
//                ) to mutableListOf(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)
//                val box = ints.fold(initial) { result, a: List<Int> ->
//                    val minv = result.first
//                    val maxv = result.second
//                    minv[0] = min(a[0], minv[0])
//                    minv[1] = min(a[1], minv[1])
//                    minv[2] = min(a[2], minv[2])
//                    maxv[0] = max(a[0], maxv[0])
//                    maxv[1] = max(a[1], maxv[1])
//                    maxv[2] = max(a[2], maxv[2])
//                    result
//                }
//                println(findSurface)
////                val holeSurface = findHoles(ints, box)
//                println(box)
//                ints.filter { coord ->
//                    val (min, max) = box
//                    for (i in min[0]..coord[0]) {
//                        poin
//                    }
//                    true
//
//                }
//            }
//        }
//        return surface
//    }
//
////    private fun findHoles(
////        ints: MutableList<List<Int>>,
////        box: Pair<MutableList<Int>, MutableList<Int>>
////    ): Any {
////        for (x in box.first[0]..box.second[0]) {
////            for (y in box.first[1]..box.second[1]) {
////                for (z in box.first[2]..box.second[2]) {
////
////                }
////            }
////        }
////    }
//
//    private fun findSurface(ints: List<Int>, arrayList: MutableList<List<Int>>): Int {
//
//        val key = ints.joinToString(",")
//        if (!points.contains(key)) {
//            // either 1 because its a surface
//            return 1
//        } else if (visited.contains(key)) {
//            // 0 because there is cube that we already counted
//            return 0
//        } else {
//            visited.add(key)
//            arrayList.add(ints)
//            // much more if there is much more to count.
//            return (findSurface(listOf(ints[0] + 1, ints[1], ints[2]), arrayList)
//                    + findSurface(listOf(ints[0] - 1, ints[1], ints[2]), arrayList)
//                    + findSurface(listOf(ints[0], ints[1] + 1, ints[2]), arrayList)
//                    + findSurface(listOf(ints[0], ints[1] - 1, ints[2]), arrayList)
//                    + findSurface(listOf(ints[0], ints[1], ints[2] + 1), arrayList)
//                    + findSurface(listOf(ints[0], ints[1], ints[2] - 1), arrayList))
//        }
//    }
//
//    companion object {
//
//
//        private fun keyToInts(next: String): List<Int> {
//            return next.split(",").map { it.toInt() }.toList()
//        }
//    }
//}