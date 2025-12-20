data class JunctionBox(val x:Int, val y:Int, val z:Int) {
    fun distanceSquared(other: JunctionBox): Long {
        val dx = (x - other.x).toLong()
        val dy = (y - other.y).toLong()
        val dz = (z - other.z).toLong()
        return (dx * dx + dy * dy + dz * dz)
    }
    companion object {
        fun from(str: String): JunctionBox {
            val coords = str.split(',').map(String::toInt).toList()
            return JunctionBox(coords[0], coords[1], coords[2])
        }
    }
}
