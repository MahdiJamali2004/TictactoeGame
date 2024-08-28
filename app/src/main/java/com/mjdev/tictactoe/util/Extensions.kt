package com.mjdev.tictactoe.util


fun Array<Array<Char?>>.wonState(): Char? {
    return if (this[0][0] != null && this[0][0] == this[0][1] && this[0][1] == this[0][2])
        this[0][0]
    else if (this[0][0] != null && this[0][0] == this[1][0] && this[1][0] == this[2][0] )
        this[0][0]
    else if (this[0][0] != null && this[0][0] == this[1][1] && this[1][1] == this[2][2] )
        this[0][0]
    else if (this[2][0] != null && this[2][0] == this[1][1] && this[1][1] == this[0][2] )
        this[2][0]
    else if (this[2][0] != null && this[2][0] == this[2][1] && this[2][1] == this[2][2] )
        this[2][0]
    else if (this[0][2] != null && this[0][2] == this[1][2] && this[1][2] == this[2][2] )
        this[0][2]
    else if (this[0][1] != null && this[0][1] == this[1][1] && this[1][1] == this[2][1] )
        this[0][1]
    else if (this[1][0] != null && this[1][0] == this[1][1] && this[1][1] == this[1][2] )
        this[1][0]
    else
        null

}

fun Array<Array<Char?>>.isBoardFull(): Boolean {
    return this.all { charArrays -> charArrays.all { it != null } }
}