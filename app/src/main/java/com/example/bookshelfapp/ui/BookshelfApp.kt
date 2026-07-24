package com.example.bookshelfapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.bookshelfapp.ui.screens.BookshelfAppContent

@Composable
fun BookShelfApp() {
    val viewModel:  BookShelfAppViewModel = viewModel(factory = BookShelfAppViewModel.Factory)
    val currentUiState by viewModel.uiState.collectAsStateWithLifecycle()

    val navController = rememberNavController()

    BookshelfAppContent(
        currentUiState = currentUiState,
        navController = navController,
        retryAction = viewModel::retry,
        selectGenre = viewModel::setGenre,
        selectBookIndex = viewModel::showInfo,
        onBackPressed = {
            viewModel.updateScreen(it)
            navController.navigateUp()
        },
        modifier = Modifier.fillMaxSize()
    )
}