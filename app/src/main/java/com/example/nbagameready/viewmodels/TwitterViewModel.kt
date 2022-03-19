package com.example.nbagameready.viewmodels

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.*
import com.example.nbagameready.network.twitterapi.Tweets
import com.example.nbagameready.network.twitterapi.TwitterApi
import kotlinx.coroutines.launch


class TwitterViewModel(application: Application) : AndroidViewModel(application) {

    val ai: ApplicationInfo = application.packageManager
        ?.getApplicationInfo(application.packageName, PackageManager.GET_META_DATA)!!
    val value = ai.metaData["bearerToken"]
    val token = value.toString()

    private val _apiResponse = MutableLiveData<Tweets>()
    val apiResponse: LiveData<Tweets> = _apiResponse

    init {
        getNbaTweets()
    }

    fun getNbaTweets() {
        viewModelScope.launch {
            _apiResponse.value = TwitterApi.retrofitService.getRecentTweets("Bearer "+ token)
        }
    }
}