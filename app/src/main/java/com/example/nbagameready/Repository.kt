package com.example.nbagameready

import androidx.lifecycle.LiveData
import com.example.nbagameready.network.Game
import com.example.nbagameready.network.Today

interface Repository {

    fun insert(game: Game)

    fun getAll(): LiveData<List<Game>>

    fun delete(game: Game)

    fun update(game: Game)
}