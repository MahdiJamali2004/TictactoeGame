package com.mjdev.tictactoe.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.mjdev.tictactoe.GameState

@Composable
fun TicTocToeScreen(
    modifier: Modifier = Modifier,
    gameState: GameState,
    gameTitle: String,
    backgroundColor : Long,
    playerTurnChange: (Pair<Int, Int>,player : Char) -> Unit, ) {
    val lineColors = MaterialTheme.colorScheme.onSurface
    val currentOrientation = LocalConfiguration.current.orientation
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(backgroundColor)),
    ) {
        Text(
            modifier = Modifier
                .widthIn(max = 100.dp)
                .align(  if(currentOrientation == Configuration.ORIENTATION_LANDSCAPE) Alignment.TopStart else Alignment.TopCenter)
                .padding(top = 32.dp , start = if(currentOrientation == Configuration.ORIENTATION_LANDSCAPE) 32.dp else 0.dp ),
            text = gameTitle,
            fontSize = MaterialTheme.typography.titleLarge.fontSize
        )

            Canvas(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(360.dp)
                    .pointerInput(Unit) {
                        detectTapGestures { offset ->
                            var x = ((offset.x) / 300 ).toInt()
                            var y = (offset.y / 300 ).toInt()
                            if (x >=3) x--
                            if (y >=3) y--
                            Log.v("tesg", "${gameState.currentPlayerTurn}")
                            playerTurnChange((Pair(y ,x)),gameState.currentPlayerTurn)
                        }
                    }
            ) {
                val width = this.size.width
                val height = this.size.height
                this.size.width
                drawBoard(
                    width = width,
                    height = height,
                    lineColors = lineColors,
                    strokeWidth = 8f
                )
                gameState.board.forEachIndexed { i, rows ->
                    rows.forEachIndexed { j, c ->
                        val xCenter = ((width * (j + 1) / 3) - (width * 1 / 6))
                        val yCenter = ((height * (i + 1) / 3) - (height * 1 / 6))
                        if (c == 'X') {
                            drawLine(
                                Color.Blue,
                                strokeWidth = 16f,
                                start = Offset(
                                    xCenter - (width * 1 / 9),
                                    yCenter - (height * 1 / 9)
                                ),
                                end = Offset(xCenter + (width * 1 / 9), yCenter + (height * 1 / 9)),
                            )
                            drawLine(
                                Color.Blue,
                                strokeWidth = 16f,
                                start = Offset(
                                    xCenter + (width * 1 / 9),
                                    yCenter - (height * 1 / 9)
                                ),
                                end = Offset(xCenter - (width * 1 / 9), yCenter + (height * 1 / 9)),
                            )
                        }
                        if (c == 'O') {
                            drawCircle(
                                style = Stroke(width = 16f),
                                radius = (width * 1 / 9f),
                                color = Color.Red,
                                center = Offset(xCenter, yCenter)
                            )
                        }
                    }
                }
            }


        }

    }



@Preview
@Composable
private fun TicTocToePreview() {
    TicTocToeScreen(
        gameTitle = "Test",
        backgroundColor = 0,
        gameState = GameState(
            board = arrayOf(
                arrayOf('X', 'X', null),
                arrayOf('O', 'O', 'O'),
                arrayOf('X', 'X', null),
            )
        ), playerTurnChange = {Pair , char ->}
    )
}

fun DrawScope.drawBoard(
    width: Float,
    height: Float,
    lineColors: Color,
    strokeWidth: Float
) {
    drawLine(
        color = lineColors,
        strokeWidth = strokeWidth,
        start = Offset(0f, height * 1 / 3f),
        end = Offset(width, height * 1 / 3f)
    )
    drawLine(
        color = lineColors,
        strokeWidth = strokeWidth,
        start = Offset(0f, height * 2 / 3f),
        end = Offset(width, height * 2 / 3f)
    )
    drawLine(
        color = lineColors,
        strokeWidth = strokeWidth,
        start = Offset(width * 1 / 3f, 0f),
        end = Offset(width * 1 / 3f, height)
    )
    drawLine(
        color = lineColors,
        strokeWidth = strokeWidth,
        start = Offset(width * 2 / 3f, 0f),
        end = Offset(width * 2 / 3f, height)
    )
}