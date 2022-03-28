package com.example.nbagameready.viewmodels

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nbagameready.network.nbaapi.Games
import kotlinx.coroutines.launch
import retrofit2.Call
import java.text.SimpleDateFormat
import java.util.*

class TomorrowViewModel(application: Application) : AndroidViewModel(application) {

    private val _apiResponse = MutableLiveData<Call<Games>>()
    val apiResponse: LiveData<Call<Games>> = _apiResponse
    val ai: ApplicationInfo = application.packageManager
        ?.getApplicationInfo(application.packageName, PackageManager.GET_META_DATA)!!
    val value = ai.metaData["keyValue"]
    val key = value.toString()

    init {
        getTodayGames()
    }


    fun getTodayGames() {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(System.currentTimeMillis())
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val c = Calendar.getInstance()
        //Setting the date to the given date
        c.time = sdf.parse(date)

        c.add(Calendar.DATE, 2)
        val newDate = sdf.format(c.time)
        viewModelScope.launch {
            _apiResponse.value = NbaApi.retrofitService.getGames(newDate, key)
        }
    }
}