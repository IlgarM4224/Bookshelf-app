package com.example.bookshelfapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelfapp.R
import com.example.bookshelfapp.ui.screens.BookshelfAppContent
import com.example.bookshelfapp.ui.screens.ErrorScreen
import com.example.bookshelfapp.ui.screens.LoadingScreen

@Composable
fun BookShelfApp() {
    val viewModel:  BookShelfAppViewModel = viewModel(factory = BookShelfAppViewModel.Factory)
    val currentUiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(val state = currentUiState){
        is BookshelfUiState.Error ->
            ErrorScreen(
                retryAction = { viewModel.retry() },
                errorText = stringResource(R.string.error)
            )
        is BookshelfUiState.Loading -> LoadingScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_small))
        )
        is BookshelfUiState.Success -> {
            BookshelfAppContent(
                bookshelfResponse = state.booksResponse,
                genres = state.genres,
                onGenreClick = viewModel::setGenre,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}