package com.example.nbagameready.database

import androidx.room.*
import com.example.nbagameready.network.nbaapi.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteTeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(team: Team)


    @Query("DELETE FROM team_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM team_table")
    fun getTeams(): Flow<List<Team>>

    @Delete
    suspend fun delete(team: Team)

    @Query("DELETE FROM team_table WHERE teamID = :id")
    suspend fun deleteTeam(id:Int)

    @Query("SELECT EXISTS(SELECT * FROM team_table WHERE teamId = :id)")
    fun teamExists(id:Int): Boolean

    @Query("UPDATE team_table SET checked = :checked WHERE teamId = :id")
    suspend fun updateCheckBoxState(id: Int, checked: Boolean): Int

}