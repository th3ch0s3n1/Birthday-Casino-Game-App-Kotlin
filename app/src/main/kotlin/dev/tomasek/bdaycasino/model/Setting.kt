package dev.tomasek.bdaycasino.model

data class Setting(
    var maxPrize: Int           = 0,
    val prizes: MutableList<Prize>,
    val startingCredits: Int
)