package com.example.bookshelfapp.ui

import android.util.Log
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface BookshelfUiState {
    data class Success(
            val booksResponse: BooksResponse,
            val genres: List<Genre>,
            val selectedGenre: Genre,
            val infoScreen: Boolean,
            val selectedBookIndex: Int = 0
        ) : BookshelfUiState
    object Error : BookshelfUiState
    object Loading : BookshelfUiState
}

class BookShelfAppViewModel (private val bookshelfRepository: BookshelfRepository): ViewModel(){
    private val _uiState = MutableStateFlow<BookshelfUiState>(BookshelfUiState.Loading)

    val uiState: StateFlow<BookshelfUiState> = _uiState.asStateFlow()

    private var currentGenre: Genre = Genre.Dystopian

    init {
        getBookshelf(currentGenre)
    }

    private fun getBookshelf(selectedGenre: Genre ){
        viewModelScope.launch {
            _uiState.value = BookshelfUiState.Loading

            bookshelfRepository.getBooksByGenreFlow(selectedGenre)
                .catch { e ->
                    Log.e("BookshelfVM", "Critical error while loading data", e)
                    _uiState.value = BookshelfUiState.Error
                }
                .collect { response ->
                    _uiState.value = BookshelfUiState.Success(
                        booksResponse = response,
                        genres = GenreProvider.getGenres(),
                        selectedGenre = selectedGenre,
                        infoScreen = false
                    )
                }
        }
    }

    fun retry(){
        getBookshelf(currentGenre)
    }

    fun setGenre (selected: Genre) {
        currentGenre = selected
        getBookshelf(selected)
    }

    fun showInfo(index: Int) {
        _uiState.update { currentState ->
            if (currentState is BookshelfUiState.Success) {
                currentState.copy(
                    infoScreen = true,
                    selectedBookIndex = index
                )
            } else{
                currentState
            }
        }
    }

    fun onBackPase() {
        _uiState.update { currentState ->
            if (currentState is BookshelfUiState.Success) {
                currentState.copy(
                    infoScreen = false,
                    selectedBookIndex = 0
                )
            } else{
                currentState
            }
        }
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
