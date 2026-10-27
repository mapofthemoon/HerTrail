
package com.example.hertrail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SafetyScreen(viewModel: HerTrailViewModel) {

    val context = LocalContext.current
    val checked by viewModel.safetyItems.collectAsStateWithLifecycle()
    val trailId by viewModel.selectedTrailId.collectAsStateWithLifecycle()

    var returnTime by rememberSaveable {
        mutableStateOf("")
    }

    var errorMessage by rememberSaveable {
        mutableStateOf("")
    }

    val trail = TrailData.trails.firstOrNull {
        it.id == trailId
    }

    val safetyChecklist = listOf(
        "I checked the weather",
        "My phone is charged",
        "Someone knows my route",
        "I have enough water",
        "I know when to turn back"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Safety first",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Forest
        )

        Text(
            "Prepare before you head into the mountains.",
            color = Forest
        )

        Text(
            "Pre-hike checklist",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Forest
        )

        safetyChecklist.forEach { item ->
            PackingRow(
                item = item,
                checked = item in checked,
                onCheckedChange = { viewModel.toggleSafety(item) }
            )
        }

        HorizontalDivider()

        Text(
            "Share your hike plan",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Forest
        )

        Text("Selected trail: ${trail?.name ?: "Not selected"}")

        OutlinedTextField(
            value = returnTime,
            onValueChange = { returnTime = it },
            label = { Text("Planned return time") },
            placeholder = { Text("e.g. Sunday, 5:00 PM") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val message =
                    "My HerTrail hike plan\n" +
                            "Trail: ${trail?.name ?: "Unknown"}\n" +
                            "Expected return: $returnTime\n" +
                            "Please check in with me if I am late."

                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, message)
                }

                try {
                    context.startActivity(
                        Intent.createChooser(intent, "Share hike plan")
                    )
                    errorMessage = ""
                } catch (e: ActivityNotFoundException) {
                    errorMessage = "No sharing app is available."
                }
            },
            enabled = returnTime.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Share itinerary")
        }

        HorizontalDivider()

        Text(
            "Emergency assistance",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Forest
        )

        Text(
            "If you need emergency help in Kazakhstan, " +
                    "use 112. This button opens your phone dialer."
        )

        Button(
            onClick = {
                val intent = Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:112")
                )
                try {
                    context.startActivity(intent)
                    errorMessage = ""
                } catch (e: ActivityNotFoundException) {
                    errorMessage = "Phone dialer is unavailable."
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Forest
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Open 112 dialer")
        }

        if (errorMessage.isNotBlank()) {
            Text(
                errorMessage,
                color = MaterialTheme.colorScheme.error
            )
        }

        Text(
            "HerTrail does not automatically contact " +
                    "emergency services or send SOS alerts.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
