import java.io.File
import kotlin.collections.fold

fun part1(): Long {
    val problems = mutableListOf<MutableList<Int>> ()
    var sum: Long = 0
    File("input.txt").forEachLine { line ->
        val split = line.split(" ").filter { it.isNotBlank() }
        if (problems.isEmpty()) {
            split.forEach { _ -> problems.add(ArrayList()) }
        }
        if (split.first().first().isDigit()) {
            val numbers = split.map { it.toInt() }
            numbers.mapIndexed { index, number -> problems[index].add(number) }
        } else {
            split.zip(problems).forEach { (op,nums) ->
                when (op) {
                    "+" -> sum += nums.sum()
                    "*" -> sum += nums.fold(1) { acc: Long, num -> acc * num }
                    else -> throw IllegalArgumentException("Invalid op: $op")
                }
            }
        }
    }
    return sum
}

fun part2(): Long {
    val lines = File("input.txt").readLines()
    val numberLines = lines.subList(0, lines.size-1)
    val lineLength = numberLines.maxOf { it.length }

    val sumOp = { s: List<Int> -> s.sum().toLong() }
    val productOp = { ns: List<Int> -> ns.fold(1) { acc: Long, num -> acc * num } }
    val operators = lines.last().split(" ").filter { it.isNotBlank() }
        .map { if (it == "+") sumOp else productOp }

    var sum: Long = 0
    var problemIndex = 0
    val problemNumbers = mutableListOf<Int>()

    for (columnIndex in 0 until lineLength) {
        val numberStr = buildString { numberLines.forEach {
            if (columnIndex < it.length) append(it[columnIndex])
        } }
        if (numberStr.isBlank() && problemNumbers.isNotEmpty()) {
            sum += operators[problemIndex](problemNumbers)
            problemNumbers.clear()
            problemIndex++
        } else {
            problemNumbers.add(numberStr.trim().toInt())
        }
    }
    if (problemNumbers.isNotEmpty()) {
        sum += operators[problemIndex](problemNumbers)
    }
    return sum
}

fun main() {
    val name = "Kotlin"
    println("Hello, $name!")
    println("Part1: The grant total of adding together all of the answers is ${part1()}.")
    println("Part2: The grant total of adding together all of the answers is ${part2()}.")
}