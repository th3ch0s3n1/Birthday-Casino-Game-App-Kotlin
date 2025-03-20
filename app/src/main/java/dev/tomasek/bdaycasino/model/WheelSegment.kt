package dev.tomasek.bdaycasino.model

import androidx.annotation.RawRes
import androidx.compose.ui.graphics.Color

data class WheelSegment(
    val label: String,
    val color: Color,
    @RawRes val soundResId: Int,
    val prize: Int
)