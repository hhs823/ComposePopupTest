package com.example.testapplication1

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlin.random.Random

class TestViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TestUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            while(true) {
                delay(1000)
                _uiState.update { it.copy(color = createRandomColor(), time = LocalDateTime.now()) }
            }
        }
    }

    private fun createRandomColor(): Color = android.graphics.Color.argb(
        255,
        Random.nextInt(256),
        Random.nextInt(256),
        Random.nextInt(256),
    ).run { Color(this) }
}

@Immutable
data class TestUiState(
    val text: TextFieldValue = TextFieldValue(),
    val color: Color = Color.Black,
    val time: LocalDateTime = LocalDateTime.now(),
)
