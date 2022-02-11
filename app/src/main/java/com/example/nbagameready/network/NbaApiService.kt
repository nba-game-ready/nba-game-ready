

import com.example.nbagameready.network.Game
import com.example.nbagameready.network.Today
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

private val networkLoggingInterceptor =
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .client(OkHttpClient.Builder().addInterceptor(networkLoggingInterceptor).build())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface NbaApiService {


    @GET("games/live/")
    fun getLiveGames(@Query("rapidapi-key") secretValue:String): Call<Today>

    @GET("games/date/{currentDateForApi}/")
     fun getGames(@Path("currentDateForApi") currentDateForApi: String,
                  @Query("rapidapi-key") secretValue:String): Call<Today>

}

object NbaApi {
    val retrofitService: NbaApiService by lazy { retrofit.create(NbaApiService::class.java) }
}