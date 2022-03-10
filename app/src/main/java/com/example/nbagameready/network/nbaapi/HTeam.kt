package com.example.nbagameready.network.nbaapi


data class HTeam(
    val fullName: String,
    val logo: String,
    val nickName: String,
    val score: Score,
    val shortName: String,
    val teamId: String
)