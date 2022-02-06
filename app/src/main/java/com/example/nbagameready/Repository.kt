package com.example.nbagameready

import androidx.lifecycle.LiveData
import com.example.nbagameready.network.Today

interface Repository {

    fun insert(today: Today)

    fun getAll(): LiveData<List<Today>>

    fun delete(today: Today)

    fun update(today: Today)
}