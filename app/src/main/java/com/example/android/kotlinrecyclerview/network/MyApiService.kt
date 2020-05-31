package com.example.android.marsrealestate.network

import com.example.android.kotlinrecyclerview.entity.Response
import com.example.android.kotlinrecyclerview.entity.SectionDetails
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

private const val BASE_URL = "https://content.viaplay.se/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
/*private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()*/

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */

//using the GsconConverterFactory. We can use moshi as well.
private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()



/**
 * A public interface that exposes the [getSections] method
 */
interface MyApiService {


    /**
     * Returns a Coroutine [Deferred] [List] of [Response] which can be fetched with await() if
     * in a Coroutine scope.
     * The @GET annotation indicates that the "androiddash-se" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("androiddash-se")
    fun getSections():
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<Response>

    @GET
    fun getSectionDetails(@Url url: String):
            Deferred<SectionDetails>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object MyApi {
    val retrofitService : MyApiService by lazy {
        retrofit.create(MyApiService::class.java) }
}
