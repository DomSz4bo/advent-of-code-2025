import java.io.File

fun getPairsToConnect(boxes: List<JunctionBox>): List<Pair<JunctionBox, JunctionBox>> {
    val pairs = ArrayList<Pair<JunctionBox, JunctionBox>>()
    for (i in boxes.indices) {
        for (j in (i + 1) until boxes.size) {
            pairs.add(boxes[i] to boxes[j])
        }
    }
    pairs.sortBy { it.first.distanceSquared(it.second) }
    return pairs
}

fun day8(): Pair<Int, Long> {
    val boxes = File("input.txt").readLines()
        .map { line -> JunctionBox.from(line) }.toList()
    val pairs = getPairsToConnect(boxes)
    var mergeCount = 0
    val circuits = Circuits()

    // part 1
    for (i in 0 until 1000) {
        val (fst, snd) = pairs[i]
        if (circuits.connect(fst, snd)) {
            mergeCount++
        }
    }
    val solution1 = circuits.getCircuits().take(3).map { it.size }
        .fold(1, Int::times)

    // part 2
    var solution2: Long = -1
    for (i in 1000 until pairs.size) {
        val (fst, snd) = pairs[i]
        if (circuits.connect(fst, snd)) {
            mergeCount++
            if (mergeCount == boxes.size - 1) {
                solution2 = fst.x.toLong() * snd.x
            }
        }
    }

    return Pair(solution1, solution2)
}

fun main() {
    val (solution1, solution2) = day8()
    println("The answer for part 1 is $solution1.")
    println("The answer for part 2 is $solution2.")
}
