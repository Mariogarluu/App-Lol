package com.example.app_lol

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ChampionViewModel : ViewModel() {
    private val _champions = MutableStateFlow(ChampionRepository.champions)
    val champions: StateFlow<List<Champion>> = _champions
}