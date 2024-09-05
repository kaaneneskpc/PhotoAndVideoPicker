package com.kaaneneskpc.photoandvideopicker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kaaneneskpc.photoandvideopicker.component.BottomBar
import com.kaaneneskpc.photoandvideopicker.screens.Home
import com.kaaneneskpc.photoandvideopicker.screens.MultiplePhotoPicker
import com.kaaneneskpc.photoandvideopicker.screens.SinglePhotoPicker
import com.kaaneneskpc.photoandvideopicker.screens.SingleVideoPicker
import com.kaaneneskpc.photoandvideopicker.ui.theme.PhotoAndVideoPickerTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhotoAndVideoPickerTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "single") {
                    composable("home") {
                        Scaffold(
                            bottomBar = {
                                BottomBar(
                                    navController = navController
                                )
                            },
                        ) {
                            Home()
                        }
                    }
                    composable("single") {
                        Scaffold(
                            bottomBar = {
                                BottomBar(
                                    navController = navController
                                )
                            },
                        ) {
                            SinglePhotoPicker()
                        }
                    }
                    composable("multi") {
                        Scaffold(
                            bottomBar = {
                                BottomBar(navController = navController)
                            },
                        ) {
                            MultiplePhotoPicker()
                        }
                    }

                    composable("video") {
                        Scaffold(
                            bottomBar = {
                                BottomBar(navController = navController)
                            },
                        ) {
                            SingleVideoPicker()
                        }
                    }
                }
            }
        }
    }
}