package com.example.recetopolis.presentation.home.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeHeader() {

    Text(
        text = "Recetópolis",
        style = MaterialTheme.typography.headlineLarge
    )
}