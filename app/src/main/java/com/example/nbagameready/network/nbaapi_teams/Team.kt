package com.example.nbagameready.network.nbaapi_teams


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_table")
data class Team(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "allstar")
    val allStar: String?,
    @ColumnInfo(name = "city")
    val city: String?,
    @ColumnInfo(name = "fullName")
    val fullName: String?,
    @ColumnInfo(name = "leagues")
    val leagues: Leagues?,
    @ColumnInfo(name = "logo")
    val logo: String?,
    @ColumnInfo(name = "nbaFranchise")
    val nbaFranchise: String?,
    @ColumnInfo(name = "nickName")
    val nickname: String?,
    @ColumnInfo(name = "shortName")
    val shortName: String?,
    @ColumnInfo(name = "teamId")
    val teamId: String?,
    @ColumnInfo(name="checked")
    var checked:Boolean = true
)