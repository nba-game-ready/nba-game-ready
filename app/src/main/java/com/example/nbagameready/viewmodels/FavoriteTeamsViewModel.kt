package com.example.nbagameready.viewmodels

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.*
import com.example.nbagameready.database.FavoriteTeamDao
import com.example.nbagameready.network.nbaapi_teams.Team
import com.example.nbagameready.network.nbaapi_teams.Teams
import com.example.nbagameready.receivers.AlarmReceiver
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

    // Notification Items
    private val REQUEST_CODE = 0
    private val TRIGGER_TIME = "TRIGGER_AT"

    private val minute: Long = 60_000L
    private val second: Long = 1_000L

    private val notifyPendingIntent: PendingIntent

    private val alarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private var prefs =
        application.getSharedPreferences("com.example.nbagameready", Context.MODE_PRIVATE)
    private val notifyIntent = Intent(application, AlarmReceiver::class.java)

    private val _timeSelection = MutableLiveData<Int>()
    val timeSelection: LiveData<Int>
        get() = _timeSelection

    private val _elapsedTime = MutableLiveData<Long>()
    val elapsedTime: LiveData<Long>
        get() = _elapsedTime

    private var _alarmOn = MutableLiveData<Boolean>()
    val isAlarmOn: LiveData<Boolean>
        get() = _alarmOn



    init {
        getTeams()
        _alarmOn.value = PendingIntent.getBroadcast(
            getApplication(),
            REQUEST_CODE,
            notifyIntent,
            PendingIntent.FLAG_NO_CREATE
        ) != null

        notifyPendingIntent = PendingIntent.getBroadcast(
            getApplication(),
            REQUEST_CODE,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
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