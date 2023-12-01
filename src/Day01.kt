import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val numbers = line.filter { it.isDigit() }
            val numberText = "${numbers.first()}${numbers.last()}"
            sum += numberText.toInt()
        }
        return sum
    }

    val numberMap = mapOf("one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5,
        "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9)

    fun convertNumbers(input: String) : String {
        var converted = ""
        var index = 0
        var didConvert = false
        while (index < input.length) {
            didConvert = false
            for (number in numberMap) {
                if (input.substring(index).startsWith(number.key)) {
                    converted += number.value.toString()
                    didConvert = true
                    break
                }
            }
            if (!didConvert) {
                converted += input[index]
            }
            index += 1
        }
        return converted
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val converted = convertNumbers(line)
            val numbers = converted.filter { it.isDigit() }
            val numberText = "${numbers.first()}${numbers.last()}"
            sum += numberText.toInt()
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()

    val testInputPart2 = readInput("Day01_2_test")
    val part2Result = part2(testInputPart2)
    check(part2Result == 281)

    part2(input).println()
}
