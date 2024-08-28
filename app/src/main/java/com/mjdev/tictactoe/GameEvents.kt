package com.mjdev.tictactoe

sealed class GameEvents {
    data class PlayerTurnChange(val rowAndColumn : Pair<Int,Int> ,val player : Char) : GameEvents()
}