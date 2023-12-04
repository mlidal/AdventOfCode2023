import java.lang.Math.max

fun main() {
    fun part1(input: List<String>): Int {
        var sumIds = 0
        val maxCubes = mapOf("blue" to 14, "red" to 12, "green" to 13)
        for (line in input) {
            var validGame = true
            val game = line.split(":")
            val gameId = game[0].split(" ")[1].toInt()
            val cubeSet = mutableMapOf("blue" to 0, "red" to 0, "green" to 0)
            val sets = game[1].split(";")
            for (set in sets) {
                val cubes = set.split(",")
                for (cube in cubes) {
                    val elements = cube.trim().split(" ")
                    if (elements[0].toInt() > maxCubes[elements[1]]!!) {
                        validGame = false
                    }
                }
            }
            if (validGame) {
                sumIds += gameId
            }
        }
        return sumIds
    }

    fun part2(input: List<String>): Int {
        var sumGames = 0
        val maxCubes = mapOf("blue" to 14, "red" to 12, "green" to 13)
        for (line in input) {
            var validGame = true
            val game = line.split(":")
            val gameId = game[0].split(" ")[1].toInt()
            val cubeSet = mutableMapOf("blue" to 0, "red" to 0, "green" to 0)
            val sets = game[1].split(";")
            for (set in sets) {
                val cubes = set.split(",")
                for (cube in cubes) {
                    val elements = cube.trim().split(" ")
                    val color = elements[1]
                    val prev = cubeSet[color]!!
                    val curr = elements[0].toInt()
                    cubeSet[color] = kotlin.math.max(prev, curr)
                }
            }
            sumGames += cubeSet["red"]!!.toInt() * cubeSet["blue"]!!.toInt() * cubeSet["green"]!!.toInt()
        }
        return sumGames
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)

    val input = readInput("Day02")
    part1(input).println()

    check(part2(testInput) == 2286)
    part2(input).println()
}
