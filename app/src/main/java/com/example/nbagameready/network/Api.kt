package com.example.nbagameready.network

/***
 * Data class that represents the data in our app. These are the objects that should
 * be displayed on screen or manipulated by the app
 */


data class Api(
    val filters: List<String>,
    val games: List<Game>,
    val message: String,
    val results: Int,
    val status: Int
)