

import com.example.nbagameready.network.Games
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call
import retrofit2.http.Query

private const val BASE_URL = "https://api-nba-v1.p.rapidapi.com/"


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

/***
 * A retrofit service to fetch nba game data
 */
interface NbaApiService {
    @GET("games/live/")
    fun getLiveGames(@Query("rapidapi-key") secretValue:String): Call<Games>

    @GET("games/date/{currentDateForApi}/")
     fun getGames(@Path("currentDateForApi") currentDateForApi: String,
                  @Query("rapidapi-key") secretValue:String): Call<Games>

}


object NbaApi {
    val retrofitService: NbaApiService by lazy { retrofit.create(NbaApiService::class.java) }
}