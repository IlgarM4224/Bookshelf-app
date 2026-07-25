package com.example.bookshelfapp.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.BookItem
import com.example.bookshelfapp.model.Genre
import com.example.bookshelfapp.ui.BookshelfUiState
import com.example.bookshelfapp.ui.ContentType
import com.example.bookshelfapp.ui.CurrentScreen
import com.example.bookshelfapp.ui.components.TopAppBar

@Composable
fun BookshelfAppContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    retryAction: () -> Unit,
    selectGenre: (Genre) -> Unit,
    selectBookIndex: (Int) -> Unit,
    onBackPressed: (CurrentScreen) -> Unit,
    contentType: ContentType = ContentType.OnlyInfo,
    currentUiState: BookshelfUiState
) {
    val currentScreenForTopAppBar = when (currentUiState) {
        is BookshelfUiState.Error -> CurrentScreen.Error
        is BookshelfUiState.Loading -> CurrentScreen.Loading
        is BookshelfUiState.Success -> currentUiState.currentScreen
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .padding(
                        vertical  = dimensionResource(R.dimen.padding_small),
                        horizontal = dimensionResource(R.dimen.padding_small)
                    ),
                onBackClick = onBackPressed,
                currentScreen = currentScreenForTopAppBar,
                contentType = contentType,
                selectedGenre = if (currentUiState is BookshelfUiState.Success ) currentUiState.selectedGenre else null
            )
        },
        modifier = modifier
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = CurrentScreen.Genre.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = CurrentScreen.Genre.name) {
                GenreScreen(
                    onClick = {
                        selectGenre(it)
                        navController.navigate(CurrentScreen.Books.name)
                    }
                )
            }

            composable(route = CurrentScreen.Books.name) {
                when (currentUiState) {
                    is BookshelfUiState.Error -> {

                        BackHandler {
                            onBackPressed(CurrentScreen.Genre)
                        }

                        ErrorScreen(
                            retryAction = retryAction,
                            errorText = stringResource(R.string.error)
                        )
                    }

                    is BookshelfUiState.Success -> {
                        BackHandler {
                            onBackPressed(CurrentScreen.Books)
                        }

                        BookCoversGrid(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = dimensionResource(R.dimen.padding_small)),
                            books = currentUiState.booksResponse?.items,
                            selectBookIndex = {
                                selectBookIndex(it)
                                navController.navigate(CurrentScreen.Info.name)
                            }
                        )
                    }

                    else -> {
                        BackHandler {
                            onBackPressed(CurrentScreen.Loading)
                        }

                        LoadingScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(dimensionResource(R.dimen.padding_small))
                        )
                    }
                }
            }

            composable(route = CurrentScreen.Info.name) {
                if (currentUiState is BookshelfUiState.Success) {
                    BackHandler {
                        onBackPressed(CurrentScreen.Info)
                    }

                    if (contentType == ContentType.OnlyInfo) {
                        BookInfo(
                            volumeInfo = currentUiState.booksResponse?.items?.getOrNull(currentUiState.selectedBookIndex ?: 0)?.volumeInfo ,
                        )
                    } else {
                        Row {
                            BookCoversGrid(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = dimensionResource(R.dimen.padding_small))
                                    .weight(3f),
                                books = currentUiState.booksResponse?.items,
                                selectBookIndex = {
                                    selectBookIndex(it)
                                }
                            )
                            if (currentUiState.selectedBookIndex != null) {
                                BookInfo(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(2f),
                                    volumeInfo = currentUiState.booksResponse?.items?.getOrNull(currentUiState.selectedBookIndex)?.volumeInfo ,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookCoversGrid(
    modifier: Modifier = Modifier,
    selectBookIndex: (Int) -> Unit = {},
    books: List<BookItem>?
){
    val spacing = dimensionResource(R.dimen.grid_padding)

    LazyVerticalGrid(
        columns = GridCells.Adaptive(dimensionResource(R.dimen.vertical_grid_min_size)),
        contentPadding = PaddingValues(spacing),
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalArrangement = Arrangement.spacedBy(spacing),
        modifier = modifier
    ) {
        if (books?.size != null){
            items(books.size){
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(books[it].volumeInfo?.imageLinks?.secureThumbnail)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    fallback = painterResource(R.drawable.not_found),
                    placeholder = painterResource(R.drawable.loading),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f / 3f)
                        .clickable(onClick = { selectBookIndex(it) })

                )
            }
        }
    }
}