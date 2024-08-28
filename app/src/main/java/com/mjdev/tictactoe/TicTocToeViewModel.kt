package com.mjdev.tictactoe

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mjdev.tictactoe.util.isBoardFull
import com.mjdev.tictactoe.util.wonState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random


class TicTocToeViewModel : ViewModel() {

    private val _gameState = MutableStateFlow(GameState())
    val gameState = _gameState.asStateFlow()

    var gameTitle by mutableStateOf("Player X turn")
        private set

    var backgroundColor by mutableLongStateOf(Random.nextLong(0xFF000000, 0xFFFFFFFF))
        private set

    fun events(event: GameEvents) {
        when (event) {
            is GameEvents.PlayerTurnChange -> {
                viewModelScope.launch(Dispatchers.Main) {
                    val (row, column) = event.rowAndColumn
                    if (gameState.value.board[row][column] != null) {
                        gameTitle = "Can't make move here"
                        return@launch
                    } else {

                        val newBoard = gameState.value.board.map { it.copyOf() }.toTypedArray()
                        newBoard[row][column] = gameState.value.currentPlayerTurn

                        if (gameState.value.currentPlayerTurn == 'X') {
                            gameTitle = "Player O turn"
                        } else {
                            gameTitle = "Player X turn"
                        }
                        _gameState.value = gameState.value.copy(
                            board = newBoard,
                            currentPlayerTurn = if (gameState.value.currentPlayerTurn == 'X') 'O' else 'X'
                        )
                        if (newBoard.wonState() != null) {
                            backgroundColor = Random.nextLong(0xFF000000, 0xFFFFFFFF)
                            if (gameState.value.currentPlayerTurn == 'X') {
                                gameTitle = "Player O Won ☺"
                            } else {
                                gameTitle = "Player X Won ☺"
                            }
                            delay(1500)
                            _gameState.value = GameState()
                            gameTitle = "Player X turn"
                            return@launch
                        }
                        if (newBoard.isBoardFull()) {
                            backgroundColor = Random.nextLong(0xFF000000, 0xFFFFFFFF)
                            gameTitle = "There is no Winners ☺"
                            delay(1500)
                            _gameState.value = GameState()
                            gameTitle = "Player X turn"
                            return@launch
                        }

                    }
                }

            }

        }
    }
}