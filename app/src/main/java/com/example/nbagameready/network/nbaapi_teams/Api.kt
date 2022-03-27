package com.example.nbagameready.network.nbaapi_teams

data class Api(
    val filters: List<String>,
    val message: String,
    val results: Int,
    val status: Int,
    val teams: List<Team>
)