package com.example.nbagameready

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nbagameready.network.Game
import com.example.nbagameready.network.Today

class TodayViewModel : ViewModel() {
    val games: MutableLiveData<Game> by lazy {
        MutableLiveData<Game>()

    }



}