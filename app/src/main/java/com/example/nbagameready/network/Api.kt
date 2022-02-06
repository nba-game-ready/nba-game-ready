package com.example.nbagameready.network

data class Api(
    val filters: List<String>,
    val games: List<Game>,
    val message: String,
    val results: Int,
    val status: Int
)