package com.example.bookshelfapp.network

import com.example.bookshelfapp.model.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookshelfApiService {
    @GET("volumes")
    suspend fun getBooksByGenre(
        @Query("q") query: String,
        @Query("key") apiKey: String,
        @Query("startIndex") startIndex: Int = 0,
        @Query("maxResults") maxResults: Int = 20
    ): BooksResponse
}
