package com.example.bookshelfapp.data

import com.example.bookshelfapp.network.BookshelfApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

private val json = Json { ignoreUnknownKeys = true }

interface AppContainer {
    val bookshelfRepository: BookshelfRepository
}

class DefaultAppContainer: AppContainer {
    private val baseUrl = "https://www.googleapis.com/books/v1/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    val retrofitService : BookshelfApiService by lazy {
        retrofit.create(BookshelfApiService::class.java)
    }

    override val bookshelfRepository: BookshelfRepository by lazy {
        NetworkBookshelfRepository(retrofitService)
    }
}