package com.example.nbagameready.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nbagameready.network.nbaapi.Team

@Database(entities = [Team::class], version = 2, exportSchema = false)
@TypeConverters(LeaguesConverters::class)
abstract class FavoriteTeamsDatabase : RoomDatabase() {

    abstract fun favoriteTeamDao(): FavoriteTeamDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: FavoriteTeamsDatabase? = null

        fun getDatabase(context: Context): FavoriteTeamsDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteTeamsDatabase::class.java,
                    "favorite_database"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}