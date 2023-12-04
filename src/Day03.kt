import kotlin.math.max
import kotlin.math.min

data class Position(val x: Int, val y: Int)
data class Number(val value: Int, val positions: List<Position> )

fun symbolIndexes(line: String) : List<Int> {
    val indexes = mutableListOf<Int>()
    line.forEachIndexed { index, c -> if (!c.isDigit() && c != '.') indexes.add(index) }
    return indexes
}

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        for (i in input.indices) {
            val line = input[i]
            println(line)
            val symbols = mutableListOf<Int>()
            if (i > 0) symbols.addAll(symbolIndexes(input[i-1]))
            symbols.addAll(symbolIndexes(input[i]))
            if (i < input.size - 1) symbols.addAll(symbolIndexes(input[i+1]))

            val regex = "\\d+".toRegex()
            val matchList = regex.findAll(line)
            for (match in matchList) {
                val valString = match.value
                val value = valString.toInt()
                val startIndex = if (match.range.first > 0) match.range.first - 1 else match.range.first
                val endIndex = if (match.range.last < line.length - 1) match.range.last + 1 else match.range.last
                var matched = false
                if (i > 0) {
                    val searchRange = input[i - 1].substring(startIndex..endIndex)
                    if (searchRange.any { !it.isDigit() && it != '.' }) {
                        matched = true
                        println("Prev: $searchRange match")
                    } else {
                        println("Prev: $searchRange")
                    }
                }
                val searchRange = input[i].substring(startIndex..endIndex)
                if (!searchRange.first().isDigit() && searchRange.first() != '.' ||
                    !searchRange.last().isDigit() && searchRange.last() != '.') {
                    matched = true
                    println("Curr: $searchRange match")
                } else {
                    println("Curr: $searchRange")
                }
                if (i < input.size - 1) {
                    val prevLine = input[i + 1].substring(startIndex..endIndex)
                    if (prevLine.any { !it.isDigit() && it != '.' }) {
                        println("Next: $prevLine match")
                        matched = true
                    } else {
                        println("Next: $prevLine")
                    }
                }
                if (matched) {
                    sum += value
                }
                println()
            }
        }
        return sum
    }

    fun findNumber(text: String, startIndex: Int): Int {
        var index = startIndex

        while (index < text.length && text[index].isDigit()) {
            index += 1
        }
        val numberEnd = index
        index = startIndex - 1
        while (index >= 0 && text[index].isDigit()) {
            index -= 1
        }
        val numberStart = index
        val digitString = text.substring(numberStart + 1..<numberEnd)
        return digitString.toInt()
    }

    fun part2(input: List<String>): Int {
        var sumFactors = 0
        for (i in input.indices) {
            val line = input[i]
            println(line)
            val regex = "\\d+".toRegex()
            line.forEachIndexed { index, char ->
                    if (char == '*') {
                        val factors = mutableListOf<Int>()
                        if (index > 0 && line[index - 1].isDigit()) {
                            val first = findNumber(line, index - 1)
                            factors.add(first)
                        }
                        if (index < line.length - 1 && line[index + 1].isDigit()) {
                            val last = findNumber(line, index + 1)
                            factors.add(last)
                        }
                        if (i > 0) {
                            val prevLine = input[i-1]
                            val range = max(0, index - 1)..min(line.length, index + 1)
                            val substring = prevLine.substring(range)
                            if (substring[0].isDigit()) {
                                val number = findNumber(prevLine, index - 1)
                                factors.add(number)
                                if (!substring[1].isDigit() && substring[2].isDigit()) {
                                    val number = findNumber(prevLine, index + 1)
                                    factors.add(number)
                                }
                            } else if (substring[1].isDigit()) {
                                val number = findNumber(prevLine, index)
                                factors.add(number)
                            } else if (substring[2].isDigit()) {
                                val number = findNumber(prevLine, index + 1)
                                factors.add(number)
                            }
                        }
                        if (i < input.size - 1) {
                            val prevLine = input[i+1]
                            val range = max(0, index - 1)..min(line.length, index + 1)
                            val substring = prevLine.substring(range)
                            if (substring[0].isDigit()) {
                                val number = findNumber(prevLine, index - 1)
                                factors.add(number)
                                if (!substring[1].isDigit() && substring[2].isDigit()) {
                                    val number = findNumber(prevLine, index + 1)
                                    factors.add(number)
                                }
                            } else if (substring[1].isDigit()) {
                                val number = findNumber(prevLine, index)
                                factors.add(number)
                            } else if (substring[2].isDigit()) {
                                val number = findNumber(prevLine, index + 1)
                                factors.add(number)
                            }
                        }
                        if (factors.size > 1) {
                            sumFactors += factors[0] * factors[1]
                        }

                    }
                }
            }
        return sumFactors
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)

    val input = readInput("Day03")
    part1(input).println()

    check(part2(testInput) == 467835)
    part2(input).println()
}
