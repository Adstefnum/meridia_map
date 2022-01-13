data class Feature (
    val id: Long,
    val points: List<Point>
)

data class Point (
    val accuracy: Double,
    val latitude: Double,
    val longitude: Double
)