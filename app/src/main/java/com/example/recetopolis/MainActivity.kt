package com.example.recetopolis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recetopolis.presentation.navigation.AppNavGraph
import com.example.recetopolis.presentation.splash.SplashScreen
import com.example.recetopolis.presentation.splash.SplashViewModel
import com.example.recetopolis.presentation.theme.RecetopolisTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RecetopolisTheme {
                val viewModel: SplashViewModel = hiltViewModel()
                val isLoggedIn by viewModel.isLoggedIn.collectAsState()
                var showSplash by remember { mutableStateOf(true) }

                if (showSplash) {
                    SplashScreen(
                        onSplashFinished = {
                            showSplash = false
                        }
                    )
                } else {
                    AppNavGraph(
                        startDestination = if (isLoggedIn) "home" else "login"
                    )
                }
            }
        }
    }
}