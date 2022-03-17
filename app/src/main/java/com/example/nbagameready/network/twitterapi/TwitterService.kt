package com.example.nbagameready.network.twitterapi

import android.database.Observable
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


private const val BASE_URL = "https://api.twitter.com/2/tweets/search/"

/***
* Okhttp for network logging
*/

private val networkLoggingInterceptor =
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


/***
 * Building the moshi object that Retrofit will be using. Adding in the full
 * kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


/***
 * Main entry point for network access
 */
private val retrofit = Retrofit.Builder()
    .client(OkHttpClient.Builder().addInterceptor(networkLoggingInterceptor).build())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface TwitterService {
    @GET("recent?query=nba")
    suspend fun getRecentTweets(@Header("Authorization") bearerToken: String) : Tweets

}

object TwitterApi {
    val retrofitService: TwitterService by lazy { retrofit.create(TwitterService::class.java) }
}