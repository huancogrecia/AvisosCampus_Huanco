package com.tecsup.avisoscampus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AzulCampus = Color(0xFF1976A5)
private val FondoCampus = Color(0xFFF5F9FD)

private val Colores = lightColorScheme(
    primary = AzulCampus,
    onPrimary = Color.White,
    background = FondoCampus,
    surface = Color.White
)

@Composable
fun AvisosCampusTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Colores, content = content)
}
