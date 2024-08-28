package com.mjdev.tictactoe

data class GameState(
    val board: Array<Array<Char?>> = arrayOf(
        arrayOf(null, null, null),
        arrayOf(null, null, null),
        arrayOf(null, null, null),
    ),
//    val isBoardFull : Boolean = false,
    val currentPlayerTurn : Char = 'X',
//    val isWinner : Char? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameState

        return board.contentDeepEquals(other.board)
    }

    override fun hashCode(): Int {
        return board.contentDeepHashCode()
    }
}
