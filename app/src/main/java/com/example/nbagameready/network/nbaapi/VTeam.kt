package com.example.nbagameready.network.nbaapi

import com.example.nbagameready.network.nbaapi.ScoreX

data class VTeam(
    val fullName: String,
    val logo: String,
    val nickName: String,
    val score: ScoreX,
    val shortName: String,
    val teamId: String
)