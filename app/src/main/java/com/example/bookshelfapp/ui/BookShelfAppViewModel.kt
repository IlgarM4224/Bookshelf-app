package com.example.bookshelfapp.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelfapp.BookShelfApplication
import com.example.bookshelfapp.data.BookshelfRepository
import com.example.bookshelfapp.model.BooksResponse
import com.example.bookshelfapp.model.Genre
import com.example.bookshelfapp.model.GenreProvider
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed interface BookshelfUiState {
    data class Success(val booksResponse: BooksResponse, val genres: List<String> ) : BookshelfUiState
    object Error : BookshelfUiState
    object Loading : BookshelfUiState
}

class BookShelfAppViewModel (private val bookshelfRepository: BookshelfRepository): ViewModel(){
    var bookshelf by mutableStateOf<BookshelfUiState>(BookshelfUiState.Loading)
        private set

    init {
        getBookshelf()
    }

    private fun getBookshelf(){
        viewModelScope.launch {
            bookshelf = BookshelfUiState.Loading

            bookshelf = try {
                BookshelfUiState.Success(
                    bookshelfRepository.getBooksByGenre(genre = Genre.HistoricalFiction),
                    GenreProvider.getGenres()
                )
            } catch (e : IOException){
                Log.e("BookshelfVM", "IOException", e)
                BookshelfUiState.Error
            } catch (e : HttpException) {
                Log.e("BookshelfVM", "HttpException: ${e.code()}", e)
                BookshelfUiState.Error
            } catch (e: Exception){
                Log.e("BookshelfVM", "Other exception", e)
                BookshelfUiState.Error
            }
        }
    }

    fun retry(){
        getBookshelf()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookShelfApplication)
                val bookshelfRepository = application.container.bookshelfRepository
                BookShelfAppViewModel(bookshelfRepository)
            }
        }
    }
}
