package com.example.bookshelfapp.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.BooksResponse
import com.example.bookshelfapp.ui.screens.BookshelfAppContent
import com.example.bookshelfapp.ui.screens.ErrorScreen
import com.example.bookshelfapp.ui.screens.LoadingScreen

@Composable
fun BookShelfApp() {
    val viewModel:  BookShelfAppViewModel = viewModel(factory = BookShelfAppViewModel.Factory)
    val currentUiState by viewModel.uiState.collectAsStateWithLifecycle()

    val navController = rememberNavController()


    when(val state = currentUiState){
        is BookshelfUiState.Error -> {
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
            BookshelfAppContent(
                bookshelfResponse = state.booksResponse ?: BooksResponse(),
                navController = navController,
                onGenreClick = viewModel::setGenre,
                onBookClick = viewModel::showInfo,
                selectedIndex = state.selectedBookIndex,
                onBackClick = {
                    navController.popBackStack()
                },
                canNavigateBack = true,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}