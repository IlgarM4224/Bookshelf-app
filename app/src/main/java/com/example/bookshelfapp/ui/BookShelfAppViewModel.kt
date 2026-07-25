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
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface BookshelfUiState {
    data class Success(
        val booksResponse: BooksResponse? = null,
        val genres: List<Genre> = emptyList(),
        val selectedGenre: Genre? = null,
        val currentScreen: CurrentScreen = CurrentScreen.Genre,
        val selectedBookIndex: Int? = null,
        val canNavigateBack: Boolean = false,
        val isLoadingMore: Boolean = false,
        val endReached: Boolean = false
        ) : BookshelfUiState
    object Error : BookshelfUiState
    object Loading : BookshelfUiState
}

class BookShelfAppViewModel (private val bookshelfRepository: BookshelfRepository): ViewModel(){
    private val _uiState = MutableStateFlow<BookshelfUiState>(BookshelfUiState.Success())
    val uiState: StateFlow<BookshelfUiState> = _uiState.asStateFlow()

    private var currentGenre: Genre? = null

    private fun getBookshelf(selectedGenre: Genre ){
        viewModelScope.launch {
            _uiState.value = BookshelfUiState.Loading

            bookshelfRepository.getBooksByGenreFlow(selectedGenre, startIndex = 0)
                .catch { e ->
                    Log.e("BookshelfVM", "Critical error while loading data", e)
                    _uiState.value = BookshelfUiState.Error
                }
                .collect { response ->
                    _uiState.value = BookshelfUiState.Success(
                        booksResponse = response,
                        genres = GenreProvider.getGenres(),
                        selectedGenre = selectedGenre,
                        currentScreen = CurrentScreen.Books,
                        canNavigateBack = true,
                        endReached = response.items.isNullOrEmpty()
                    )
                }
        }
    }

    fun loadMore() {
        val current = _uiState.value

        if (current !is BookshelfUiState.Success) return
        if (current.isLoadingMore || current.endReached) return

        val genre = current.selectedGenre ?: return
        val loadedCount = current.booksResponse?.items?.size ?: 0

        viewModelScope.launch {
            _uiState.update { (it as? BookshelfUiState.Success)?.copy(isLoadingMore = true) ?: it }

            bookshelfRepository.getBooksByGenreFlow(genre, startIndex = loadedCount)
                .catch { e ->
                    Log.e("BookshelfVM", "Error loading more books", e)
                    _uiState.update { (it as? BookshelfUiState.Success)?.copy(isLoadingMore = false) ?: it }
                }
                .collect { response ->
                    _uiState.update { state ->
                        if (state is BookshelfUiState.Success) {
                            val combined = (state.booksResponse?.items ?: emptyList()) + (response.items ?: emptyList())
                            state.copy(
                                booksResponse = (state.booksResponse ?: response).copy(items = combined),
                                isLoadingMore = false,
                                endReached = response.items.isNullOrEmpty()
                            )
                        } else state
                    }
                }
        }
    }

    fun retry(){ currentGenre?.let { getBookshelf(it) } }

    fun setGenre (selected: Genre) {
        currentGenre = selected
        getBookshelf(selected)
    }

    fun showInfo(index: Int) {
        _uiState.update { currentState ->
            if (currentState is BookshelfUiState.Success) {
                currentState.copy(
                    currentScreen = CurrentScreen.Info,
                    selectedBookIndex = index
                )
            } else{
                currentState
            }
        }
    }

    fun updateScreen(currentScreen: CurrentScreen){
        if (currentScreen == CurrentScreen.Loading) {
            viewModelScope.coroutineContext.cancelChildren()
        }

        val moveTo = when (currentScreen) {
            CurrentScreen.Info -> CurrentScreen.Books
            CurrentScreen.Books -> CurrentScreen.Genre
            else -> CurrentScreen.Genre
        }

        _uiState.update { currentState ->
            when (currentState) {
                is BookshelfUiState.Success -> currentState.copy(currentScreen = moveTo)
                else -> BookshelfUiState.Success(
                    genres = GenreProvider.getGenres(),
                    currentScreen = moveTo
                )
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

enum class CurrentScreen {
    Info, Books, Genre, Error, Loading
}