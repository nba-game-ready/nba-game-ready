package com.example.nbagameready

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import com.example.nbagameready.network.Game
import com.example.nbagameready.network.Games
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.SecretKey
import kotlin.coroutines.coroutineContext

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val _apiResponse = MutableLiveData<Call<Games>>()
    val apiResponse: LiveData<Call<Games>> = _apiResponse
    val ai: ApplicationInfo = application.packageManager
        ?.getApplicationInfo(application.packageName, PackageManager.GET_META_DATA)!!
    val value = ai.metaData["keyValue"]
    val key = value.toString()



    init {
        getGames()
    }

    fun getGames() {
        val date = SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis())
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val c = Calendar.getInstance()
        //Setting the date to the given date
        c.time = sdf.parse(date)
        c.add(Calendar.DAY_OF_MONTH, 1)
        val newDate = sdf.format(c.time)
        viewModelScope.launch {
            _apiResponse.value = NbaApi.retrofitService.getGames(newDate, key)
        }
    }
}






