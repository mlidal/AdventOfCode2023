import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

fun main() {

    fun power(number: Int) : Int {
        val floatNumber : Double = number.toDouble()
        return 2.0.pow(floatNumber).toInt()
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        for (index in input.indices) {
            val line = input[index]
            val card = line.split(":")[1]
            val elements = card.split("|").map { it.trim() }
            val winningNumbers = elements[0].split("\\s+".toRegex()).map { it.toInt() }.toSet()
            val myNumbers = elements[1].split("\\s+".toRegex()).map { it.toInt() }
            val myWinning = myNumbers.filter { winningNumbers.contains(it) }
            val lineSum = if (myWinning.isNotEmpty()) power(myWinning.size - 1) else 0
            sum += lineSum
        }
        return sum
    }


    fun part2(input: List<String>): Int {
        val cardNumbers = mutableMapOf<Int, Int>()
        for (index in input.indices) {
            val line = input[index]
            val split = line.split(":")
            val split1 = split[0].split("\\s+".toRegex())
            val cardNumber = split1[1].toInt()
            cardNumbers[cardNumber] = 0
        }
        for (index in input.indices) {
            val line = input[index]
            val cardNumber = line.split(":")[0].split("\\s+".toRegex())[1].toInt()
            val numbers = line.split(":")[1]
            val elements = numbers.split("|").map { it.trim() }
            val winningNumbers = elements[0].split("\\s+".toRegex()).map { it.toInt() }.toSet()
            val myNumbers = elements[1].split("\\s+".toRegex()).map { it.toInt() }
            val myWinning = myNumbers.filter { winningNumbers.contains(it) }
            cardNumbers[cardNumber] = cardNumbers[cardNumber]!! + 1
            for (i in 1..myWinning.size) {
                if (cardNumbers.containsKey(cardNumber + i)) {
                    val cardSum = cardNumbers[cardNumber]!!
                    cardNumbers[cardNumber + i] = cardNumbers[cardNumber + i]!! + cardSum
                }
            }
        }
        val sum = cardNumbers.values.reduce { acc, i -> acc + i }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)

    val input = readInput("Day04")
    part1(input).println()

    check(part2(testInput) == 30)
    part2(input).println()
}
