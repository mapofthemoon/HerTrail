package com.example.hertrail

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ReadyScreen(viewModel: HerTrailViewModel) {

    val trailId by viewModel.selectedTrailId.collectAsStateWithLifecycle()
    val weather by viewModel.weather.collectAsStateWithLifecycle()
    val packed by viewModel.packedItems.collectAsStateWithLifecycle()

    val trail = TrailData.trails.firstOrNull {
        it.id == trailId
    } ?: return

    val items = viewModel.packingList(trail, weather)
    val packedCount = items.count { it in packed }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Get ready",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Forest
        )

        Text("Choose your trail", color = Forest)

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TrailData.trails.forEach { option ->
                FilterChip(
                    selected = option.id == trailId,
                    onClick = { viewModel.selectTrail(option.id) },
                    label = { Text(option.name) }
                )
            }
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Sage
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    trail.name,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = Forest
                )
                Text("${trail.distanceKm} km")
                Text(trail.difficulty)
                Text(
                    "Estimated hike: ${trail.durationMinutes / 60.0} hours"
                )
            }
        }

        Text(
            "Weather scenario (demo)",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Forest
        )

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Clear", "Rain", "Cold", "Windy").forEach { condition ->
                FilterChip(
                    selected = weather == condition,
                    onClick = { viewModel.setWeather(condition) },
                    label = { Text(condition) }
                )
            }
        }

        Text(
            "Live forecasts will replace these example conditions.",
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            "Your packing list",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Forest
        )

        Text("$packedCount of ${items.size} items packed")

        LinearProgressIndicator(
            progress = { packedCount.toFloat() / items.size },
            modifier = Modifier.fillMaxWidth(),
            color = Forest
        )

        items.forEach { item ->
            PackingRow(
                item = item,
                checked = item in packed,
                onCheckedChange = { viewModel.togglePacked(item) }
            )
        }

        HorizontalDivider()

        Text(
            "Daylight safety",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Forest
        )

        Text(
            "Estimated return time: " +
                    "${trail.returnMinutes} minutes"
        )

        Text(
            "Live sunset and recommended turnaround time " +
                    "will appear here after weather integration."
        )
    }
}

@Composable
fun PackingRow(
    item: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )

        Text(
            text = item,
            color = Forest
        )
    }
}
