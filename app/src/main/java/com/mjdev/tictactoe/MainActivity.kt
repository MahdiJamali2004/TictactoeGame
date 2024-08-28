package com.mjdev.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mjdev.tictactoe.components.TicTocToeScreen
import com.mjdev.tictactoe.ui.theme.TictactoeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TictactoeTheme {
                val viewModel = viewModel<TicTocToeViewModel>()
                val gameState by viewModel.gameState.collectAsState()
                TicTocToeScreen(
                    backgroundColor = viewModel.backgroundColor,
                    gameState = gameState,
                    gameTitle = viewModel.gameTitle,
                    playerTurnChange = { rowColumn , player ->
                        viewModel.events(GameEvents.PlayerTurnChange(rowColumn,player))
                    }
                )
            }
        }
    }
}

