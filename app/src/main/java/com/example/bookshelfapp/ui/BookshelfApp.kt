package com.example.bookshelfapp.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.BooksResponse
import com.example.bookshelfapp.ui.screens.BookInfo
import com.example.bookshelfapp.ui.screens.BookshelfAppContent
import com.example.bookshelfapp.ui.screens.ErrorScreen
import com.example.bookshelfapp.ui.screens.GenreScreen
import com.example.bookshelfapp.ui.screens.LoadingScreen

@Composable
fun BookShelfApp() {
    val viewModel:  BookShelfAppViewModel = viewModel(factory = BookShelfAppViewModel.Factory)
    val currentUiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(val state = currentUiState){
        is BookshelfUiState.Error -> {
            BackHandler {
                viewModel.backToGenreScreen()
            }
            ErrorScreen(
                retryAction = { viewModel.retry() },
                errorText = stringResource(R.string.error)
            )
        }

        is BookshelfUiState.Loading -> LoadingScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_small))
        )

        is BookshelfUiState.Success -> {

            BackHandler(enabled = state.currentScreen != CurrentScreen.Genre) {
                viewModel.back(state.currentScreen)
            }

            when(state.currentScreen) {
                CurrentScreen.Genre ->
                    GenreScreen(
                        onClick = viewModel::setGenre
                    )
                CurrentScreen.Info ->
                    BookInfo(
                        modifier = Modifier.statusBarsPadding(),
                        volumeInfo = state.booksResponse?.items?.getOrNull(state.selectedBookIndex)?.volumeInfo,
                        onBackClick = { viewModel.backToBooksScreen() }
                    )
                else ->
                    BookshelfAppContent(
                        bookshelfResponse = state.booksResponse ?: BooksResponse(),
                        onBookClick = viewModel::showInfo,
                        onBackClick = viewModel::backToGenreScreen,
                        modifier = Modifier.fillMaxSize()
                    )
            }
        }
    }
}