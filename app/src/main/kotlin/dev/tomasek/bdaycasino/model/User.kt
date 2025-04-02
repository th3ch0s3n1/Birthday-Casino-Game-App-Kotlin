package dev.tomasek.bdaycasino.model

data class User(
    var name: String    = "",
    var credits: Int    = 0,
    var prize: Int      = 0,
    var inventory: MutableList<Prize>
)