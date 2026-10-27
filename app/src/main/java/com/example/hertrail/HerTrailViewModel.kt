
package com.example.hertrail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HikeGroup(
    val id: Int,
    val trailId: Int,
    val title: String,
    val host: String,
    val date: String,
    val pace: String,
    val members: Int,
    val capacity: Int
)

class HerTrailViewModel : ViewModel() {

    // Example hiking events for our MVP
    val groups = listOf(
        HikeGroup(
            1, 1, "Waterfall girls",
            "Amina", "Saturday, 9:00 AM",
            "Beginner", 3, 6
        ),
        HikeGroup(
            2, 2, "Kok-Zhailau weekend",
            "Dana", "Sunday, 8:00 AM",
            "Moderate", 4, 7
        ),
        HikeGroup(
            3, 3, "Summit sisters",
            "Aruzhan", "Sunday, 7:00 AM",
            "Experienced", 2, 5
        )
    )

    private val _joinedGroups = MutableStateFlow(setOf<Int>())
    val joinedGroups = _joinedGroups.asStateFlow()

    private val _selectedTrailId = MutableStateFlow(1)
    val selectedTrailId = _selectedTrailId.asStateFlow()

    private val _weather = MutableStateFlow("Clear")
    val weather = _weather.asStateFlow()

    private val _packedItems = MutableStateFlow(setOf<String>())
    val packedItems = _packedItems.asStateFlow()

    private val _safetyItems = MutableStateFlow(setOf<String>())
    val safetyItems = _safetyItems.asStateFlow()

    fun toggleGroup(group: HikeGroup) {
        val current = _joinedGroups.value

        if (group.id in current) {
            _joinedGroups.value = current - group.id
        } else if (group.members < group.capacity) {
            _joinedGroups.value = current + group.id
            selectTrail(group.trailId)
        }
    }

    fun selectTrail(id: Int) {
        _selectedTrailId.value = id
        _packedItems.value = emptySet()
    }

    fun setWeather(value: String) {
        _weather.value = value
    }

    fun togglePacked(item: String) {
        val current = _packedItems.value
        _packedItems.value =
            if (item in current) current - item
            else current + item
    }

    fun toggleSafety(item: String) {
        val current = _safetyItems.value
        _safetyItems.value =
            if (item in current) current - item
            else current + item
    }

    fun packingList(trail: Trail, condition: String): List<String> {
        val items = mutableListOf(
            "Water",
            "Snacks",
            "Charged phone",
            "Power bank",
            "First-aid kit",
            "Hiking shoes"
        )

        if (trail.durationMinutes >= 240) {
            items.add("Extra water and food")
        }

        if (trail.difficulty != "Easy") {
            items.add("Warm extra layer")
        }

        when (condition) {
            "Rain" -> {
                items.add("Waterproof jacket")
                items.add("Dry bag")
            }
            "Cold" -> {
                items.add("Thermal clothing")
                items.add("Gloves")
            }
            "Windy" -> {
                items.add("Windproof jacket")
            }
            "Clear" -> {
                items.add("Sunglasses")
                items.add("Sun protection")
            }
        }

        return items
    }
}
