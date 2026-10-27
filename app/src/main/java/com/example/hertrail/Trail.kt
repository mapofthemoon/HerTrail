package com.example.hertrail

data class Trail(
    val id: Int,
    val name: String,
    val location: String,
    val difficulty: String,
    val distanceKm: Double,
    val durationMinutes: Int,
    val returnMinutes: Int
)
