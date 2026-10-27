
package com.example.hertrail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExploreScreen() {

    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    var difficulty by rememberSaveable {
        mutableStateOf("All")
    }

    var selectedTrailId by rememberSaveable {
        mutableIntStateOf(-1)
    }

    val filteredTrails = TrailData.trails.filter { trail ->
        val matchesSearch = trail.name.contains(
            searchText,
            ignoreCase = true
        )

        val matchesDifficulty =
            difficulty == "All" ||
                    trail.difficulty == difficulty

        matchesSearch && matchesDifficulty
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(16.dp)
    ) {

        Text(
            text = "Explore trails",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Forest
        )

        Text(
            text = "Find your next adventure",
            color = Forest
        )

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search trails") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOf("All", "Easy", "Moderate", "Hard").forEach { level ->

                FilterChip(
                    selected = difficulty == level,
                    onClick = { difficulty = level },
                    label = { Text(level) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Sage
                    )
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredTrails, key = { it.id }) { trail ->
                TrailCard(
                    trail = trail,
                    onClick = { selectedTrailId = trail.id }
                )
            }
        }
    }

    val selectedTrail = TrailData.trails.firstOrNull {
        it.id == selectedTrailId
    }

    if (selectedTrail != null) {
        AlertDialog(
            onDismissRequest = { selectedTrailId = -1 },
            title = { Text(selectedTrail.name) },
            text = {
                Text(
                    "Location: ${selectedTrail.location}\n" +
                            "Difficulty: ${selectedTrail.difficulty}\n" +
                            "Distance: ${selectedTrail.distanceKm} km\n" +
                            "Estimated duration: ${selectedTrail.durationMinutes / 60.0} hours"
                )
            },
            confirmButton = {
                TextButton(onClick = { selectedTrailId = -1 }) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
fun TrailCard(
    trail: Trail,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = trail.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Forest
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = trail.location,
                color = Color.Gray
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "${trail.distanceKm} km  •  " +
                        "${trail.durationMinutes / 60.0} hours  •  " +
                        trail.difficulty,
                color = Forest
            )
        }
    }
}
