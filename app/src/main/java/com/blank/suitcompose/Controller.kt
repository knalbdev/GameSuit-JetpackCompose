package com.blank.suitcompose

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.MutableLiveData

class Controller {
    private val suitJepang = mutableListOf("gunting", "batu", "kertas")
    val result = MutableLiveData<String>()

    fun setResult(resut: String) {
        result.value = resut
    }

    fun checkPemenang(player1: String, player2: String) {
        if (player1 == suitJepang[1] && player2 == suitJepang[0] ||
            player1 == suitJepang[0] && player2 == suitJepang[2] ||
            player1 == suitJepang[2] && player2 == suitJepang[1]
        ) {
            result.value = "Pemain 1\nMENANG!"
        } else if (
            player1 == suitJepang[1] && player2 == suitJepang[2] ||
            player1 == suitJepang[0] && player2 == suitJepang[1] ||
            player1 == suitJepang[2] && player2 == suitJepang[0]
        ) {
            result.value = "Pemain 2\nMENANG!"
        } else {
            result.value = "Draw"
        }
    }
}

@Composable
fun setStyleResult(resutl: String): TextStyle =
    when (resutl) {
        "VS" -> MaterialTheme.typography.h1
        "Pemain 1\nMENANG!" -> MaterialTheme.typography.h5
        "Pemain 2\nMENANG!" -> MaterialTheme.typography.h5
        else -> MaterialTheme.typography.h3
    }

@Composable
fun setColorTextResult(resutl: String): Color =
    when (resutl) {
        "VS" -> Color.Red
        else -> Color.White
    }

@Composable
fun setBgResult(resutl: String): Color =
    when (resutl) {
        "VS" -> Color.Transparent
        "Pemain 1\nMENANG!" -> Color.Green
        "Pemain 2\nMENANG!" -> Color.Green
        else -> Color.Blue
    }