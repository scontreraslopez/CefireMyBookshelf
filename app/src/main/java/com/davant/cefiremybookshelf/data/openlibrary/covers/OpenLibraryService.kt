package com.davant.cefiremybookshelf.data.openlibrary.covers

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://covers.openlibrary.org/b/"

val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

private val olCover = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL).build()

interface CoverOLService {
    @GET("isbn/{isbn}.json")
    suspend fun getCoverByIsbn(@Path("isbn") isbn:String): OpenLibraryCover
}

object CoverOLApi {
    val coverOLService : CoverOLService by lazy {
        olCover.create(CoverOLService::class.java)
    }
}
