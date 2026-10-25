package com.migwi.diary.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Paper = Color(0xFFF4EFE6)
val Ink = Color(0xFF1C1915)
val Muted = Color(0xFF6E675E)
val Card = Color(0xFFFFFBF5)
val Accent = Color(0xFF8C3A2F)
val Line = Color(0xFFE4D9C8)

enum class Mood(val label: String) {
    Grateful("Grateful"),
    Calm("Calm"),
    Happy("Happy"),
    Reflective("Reflective"),
    Heavy("Heavy"),
    ;

    companion object {
        fun from(raw: String): Mood = entries.firstOrNull { it.name == raw } ?: Calm
    }
}

private val Colors = lightColorScheme(
    primary = Accent,
    onPrimary = Color.White,
    background = Paper,
    onBackground = Ink,
    surface = Card,
    onSurface = Ink,
    surfaceVariant = Paper,
    onSurfaceVariant = Muted,
    outline = Line,
)

private val Type = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 40.sp,
        lineHeight = 44.sp,
        color = Ink,
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        color = Ink,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        color = Ink,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        color = Ink,
    ),
    bodyMedium = TextStyle(
        fontSize = 15.sp,
        lineHeight = 22.sp,
        color = Ink,
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    ),
)

@Composable
fun DiaryTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = Colors,
        typography = Type,
        content = content,
    )
}
