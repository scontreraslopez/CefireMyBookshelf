package com.davant.cefiremybookshelf.data.openlibrary.search

import com.davant.cefiremybookshelf.data.openlibrary.covers.OpenLibraryCover
import com.davant.cefiremybookshelf.data.openlibrary.search.bookinfo.BookSearch
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://openlibrary.org"

val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

private val olSearch = Retrofit.Builder()
    .addConverterFactory(json
        .asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL).build()

interface OLSearchService {
    @GET("search.json")
    suspend fun getBookInfoByIsbn(@Query("isbn") isbn:String): BookSearch
}

object OLSearchApi {
    val searchService : OLSearchService by lazy {
        olSearch.create(OLSearchService::class.java)
    }
}
