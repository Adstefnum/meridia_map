package com.example.meridia_map


@Serializable
data class Feature (
    val id: Long,
    val points: List<Point>
)

@Serializable
data class Point (
    val accuracy: Double,
    val latitude: Double,
    val longitude: Double
)