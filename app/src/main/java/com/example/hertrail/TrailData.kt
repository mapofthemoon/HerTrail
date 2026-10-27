
package com.example.hertrail

object TrailData {

    val trails = listOf(
        Trail(
            id = 1,
            name = "Butakovka Waterfall",
            location = "Butakovka Gorge",
            difficulty = "Easy",
            distanceKm = 3.5,
            durationMinutes = 120,
            returnMinutes = 60
        ),
        Trail(
            id = 2,
            name = "Kok-Zhailau",
            location = "Ile-Alatau",
            difficulty = "Moderate",
            distanceKm = 14.0,
            durationMinutes = 360,
            returnMinutes = 180
        ),
        Trail(
            id = 3,
            name = "Furmanov Peak",
            location = "Medeu Area",
            difficulty = "Hard",
            distanceKm = 12.0,
            durationMinutes = 420,
            returnMinutes = 210
        )
    )
}
