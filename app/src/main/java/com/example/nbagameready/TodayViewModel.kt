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


}



