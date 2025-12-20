class Circuits {
    private val circuits = HashMap<JunctionBox, ArrayList<JunctionBox>>()

    fun connect(p1: JunctionBox, p2: JunctionBox): Boolean {
        val circuit1 = circuits.computeIfAbsent(p1) { ArrayList<JunctionBox>().apply { add(p1) } }
        val circuit2 = circuits.computeIfAbsent(p2) { ArrayList<JunctionBox>().apply { add(p2) } }
        if (circuit1 === circuit2) {
            return false
        }
        circuit1.addAll(circuit2)
        for (p in circuit2) {
            circuits[p] = circuit1
        }
        return true
    }

    fun getCircuits(): List<List<JunctionBox>> {
        return circuits.values.sortedByDescending { it.size }.distinct()
    }
}
