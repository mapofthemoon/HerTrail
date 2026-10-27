
package com.example.hertrail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*

// HerTrail colors
val Cream = Color(0xFFF7F2EC)
val Blush = Color(0xFFE8C7D3)
val Sage = Color(0xFFA9BDA1)
val Forest = Color(0xFF4D6556)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Forest,
                    onPrimary = Color.White,
                    secondary = Sage,
                    tertiary = Blush,
                    background = Cream,
                    surface = Cream,
                    onSurface = Forest
                )
            ) {
                HerTrailApp()
            }
        }
    }
}

@Composable
fun HerTrailApp(
    appViewModel: HerTrailViewModel = viewModel()
) {
    val navController = rememberNavController()

    val tabs = listOf(
        Triple("explore", "Explore", "🏔️"),
        Triple("community", "Girls", "👭"),
        Triple("ready", "Get Ready", "🎒"),
        Triple("safety", "Safety", "🛡️")
    )

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        containerColor = Cream,
        bottomBar = {
            NavigationBar(
                containerColor = Cream
            ) {
                tabs.forEach { tab ->
                    NavigationBarItem(
                        selected = currentRoute == tab.first,
                        onClick = {
                            navController.navigate(tab.first) {
                                popUpTo("explore") {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Text(tab.third) },
                        label = { Text(tab.second) }
                    )
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = "explore",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("explore") {
                ExploreScreen()
            }

            composable("community") {
                CommunityScreen(appViewModel)
            }

            composable("ready") {
                ReadyScreen(appViewModel)
            }

            composable("safety") {
                SafetyScreen(appViewModel)
            }
        }
    }
}
