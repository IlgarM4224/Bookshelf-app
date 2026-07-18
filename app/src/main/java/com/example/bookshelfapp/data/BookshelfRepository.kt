package com.example.bookshelfapp.data

import com.example.bookshelfapp.BuildConfig
import com.example.bookshelfapp.model.BooksResponse
import com.example.bookshelfapp.model.Genre
import com.example.bookshelfapp.network.BookshelfApiService

const val apiKey = BuildConfig.BOOKS_API_KEY
interface BookshelfRepository {
    suspend fun getBooksByGenre(genre: Genre): BooksResponse
}

class NetworkBookshelfRepository (private val retrofitService: BookshelfApiService): BookshelfRepository {
    override suspend fun getBooksByGenre(genre: Genre): BooksResponse = retrofitService.getBooksByGenre(
        query = genre.name,
        apiKey = apiKey,
        maxResults = 10
    )
}