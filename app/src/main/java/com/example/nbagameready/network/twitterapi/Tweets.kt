package com.example.nbagameready.network.twitterapi

data class Tweets(
    val `data`: List<Data>,
    val includes: Includes,
    val meta: Meta
)