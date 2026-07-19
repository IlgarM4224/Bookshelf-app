package com.example.bookshelfapp.data

import android.util.Log
import com.example.bookshelfapp.BuildConfig
import com.example.bookshelfapp.model.BooksResponse
import com.example.bookshelfapp.model.Genre
import com.example.bookshelfapp.network.BookshelfApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import retrofit2.HttpException
import java.io.IOException
import kotlin.time.Duration.Companion.milliseconds

const val apiKey = BuildConfig.BOOKS_API_KEY
interface BookshelfRepository {
    suspend fun getBooksByGenre(genre: Genre): BooksResponse
    fun getBooksByGenreFlow(genre: Genre, maxRetries: Int = 3): Flow<BooksResponse>
}

class NetworkBookshelfRepository (private val retrofitService: BookshelfApiService): BookshelfRepository {
    override suspend fun getBooksByGenre(genre: Genre): BooksResponse =
        retrofitService.getBooksByGenre(
            query = genre.name,
            apiKey = apiKey,
            maxResults = 20
        )

    override fun getBooksByGenreFlow(genre: Genre, maxRetries: Int): Flow<BooksResponse> {
        return flow {
            emit(getBooksByGenre(genre))
        }.retryWhen { cause, attempt ->
            val isNetworkError = cause is IOException
            val isServerError503 = cause is HttpException && cause.code() == 503

            val shouldRetry = attempt < maxRetries && (isNetworkError || isServerError503)

            if (shouldRetry) {
                val delayTime = (1 shl attempt.toInt()) * 1000L

                Log.w(
                    "NetworkRepository",
                    "Server error 503. Attempt ${attempt + 1}"
                )
                delay(delayTime.milliseconds)
            }
            shouldRetry
        }
    }
}