package com.example.meridia_map


val json    = Json(JsonConfiguration.Stable)
val mapmodel = json.parse(MapModel.serializer(), jsonString)

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class MapModel (
    val features: List<Feature>
)

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