package com.example.nbagameready

import android.app.Application
import com.example.nbagameready.database.FavoriteTeamsDatabase

class Application : Application() {

    val database: FavoriteTeamsDatabase by lazy { FavoriteTeamsDatabase.getDatabase(this) }

}
