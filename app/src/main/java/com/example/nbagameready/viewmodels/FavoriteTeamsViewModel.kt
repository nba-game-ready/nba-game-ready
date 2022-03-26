package com.example.nbagameready.viewmodels

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.*
import com.example.nbagameready.database.FavoriteTeamDao
import com.example.nbagameready.network.nbaapi_teams.Team
import com.example.nbagameready.network.nbaapi_teams.Teams
import kotlinx.coroutines.launch
import retrofit2.Call

class FavoriteTeamsViewModel(application: Application, private val favoriteDao: FavoriteTeamDao) :
    AndroidViewModel(application) {

    private val _apiResponse = MutableLiveData<Call<Teams>>()
    val apiResponse: LiveData<Call<Teams>> = _apiResponse
    private val ai: ApplicationInfo = application.packageManager
        ?.getApplicationInfo(application.packageName, PackageManager.GET_META_DATA)!!
    private val value = ai.metaData["keyValue"]
    private val key = value.toString()

    init {
        getTeams()
    }

    fun deleteTeam(id: Int) {
        viewModelScope.launch {
            Log.i("Yunis", "Team to be deleted: $id")

            favoriteDao.deleteTeam(id)
            Log.i("Yunis", "Team deleted: $id")

        }
    }

    fun addTeam(team: Team) {
        viewModelScope.launch {
            favoriteDao.insert(team)
            Log.i("Yunis", "Team inserted: " + team.nickname)
        }
    }

    fun teamExists(id: Int): Boolean {
        return favoriteDao.teamExists(id)
    }

    fun updateCheckBox(id: Int, checked: Boolean): Int {
        var result = 0
        viewModelScope.launch {
            result = favoriteDao.updateCheckBoxState(id, checked)
        }
        return result
    }

    private fun getTeams() {

        viewModelScope.launch {
            _apiResponse.value = NbaApi.retrofitService.getTeams(key)
        }
    }

    class MainViewModelFactory(
        private val favoriteDao: FavoriteTeamDao,
        private val application: Application
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavoriteTeamsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FavoriteTeamsViewModel(application, favoriteDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}