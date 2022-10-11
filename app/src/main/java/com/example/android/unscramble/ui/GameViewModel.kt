package com.example.android.unscramble.ui

import androidx.lifecycle.ViewModel
import com.example.android.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel: ViewModel() {

    //UI State
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var usedWords: MutableSet<String> = mutableSetOf()

    init {
        resetGame()
    }

    private fun pickRandomWordAndShuffle(): String {
        val randomWord = allWords.random()

        if(usedWords.contains(randomWord)) {
            pickRandomWordAndShuffle()
        } else {
            usedWords.add(randomWord)
            return shuffleWord(randomWord)
        }
    }

    private fun shuffleWord(word: String): String {
        val temp = word.toCharArray()
        temp.shuffle()

        if(temp.toString() == word) {
            shuffleWord(word)
        } else {
            return temp.toString()
        }
    }

    private fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

}