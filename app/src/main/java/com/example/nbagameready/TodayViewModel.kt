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
import javax.crypto.SecretKey
import kotlin.coroutines.coroutineContext

class TodayViewModel(application: Application) : AndroidViewModel(application) {
    private val _apiResponse = MutableLiveData<Games>()
    val apiResponse: LiveData<Games> = _apiResponse

    val ai: ApplicationInfo = application.packageManager
        ?.getApplicationInfo(requireContext().packageName, PackageManager.GET_META_DATA)!!
    val value = ai.metaData["keyValue"]
    val key = value.toString()

//    init {
//        getSunData()
//    }

    fun getGames(currentDateForApi: String, secretKey: SecretKey) {
        viewModelScope.launch {
            _apiResponse.value = NbaApi.retrofitService.getGames(currentDateForApi, key)
        }
    }
}



