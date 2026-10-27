package com.example.hertrail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun CommunityScreen(viewModel: HerTrailViewModel) {

    val joined by viewModel.joinedGroups.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            Column {
                Text(
                    "Hike together",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Forest
                )
                Text(
                    "Find your hiking girls",
                    color = Forest
                )
                Text(
                    "Community events are demo data.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        items(viewModel.groups) { group ->

            val isJoined = group.id in joined
            val currentMembers =
                group.members + if (isJoined) 1 else 0

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = androidx.compose.ui.graphics.Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        group.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Forest
                    )

                    Text("Hosted by ${group.host}")
                    Text(group.date)
                    Text("Pace: ${group.pace}")

                    Text(
                        "$currentMembers / ${group.capacity} hikers"
                    )

                    Button(
                        onClick = { viewModel.toggleGroup(group) },
                        enabled = isJoined ||
                                currentMembers < group.capacity,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Forest
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (isJoined) "Leave hike" else "Join hike")
                    }
                }
            }
        }
    }
}
