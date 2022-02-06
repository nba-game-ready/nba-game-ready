package com.example.nbagameready

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nbagameready.network.Game
import com.example.nbagameready.network.Today

class TodayViewModel(private val repository: Repository) : ViewModel() {
    val uiModel: MutableLiveData<Today> by lazy {
        MutableLiveData<Today>()

    }

    fun generateReport (){
        // TODO Add complicated report computation
    }

    fun deleteToday(today: Today?) {
        deleteTodayFromRepository(today)
        uiModel.value?.api?.games


    }

    fun deleteTodayFromRepository(today: Today?) {
        if(today != null) { repository.delete(today) }
    }
}