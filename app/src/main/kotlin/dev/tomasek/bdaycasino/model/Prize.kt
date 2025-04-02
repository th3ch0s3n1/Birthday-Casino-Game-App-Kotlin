package dev.tomasek.bdaycasino.model

data class Prize(
    val name: String,
    var description: String,
    val minimumCreditsToUnlock: Int,
    val contributor: String
)
