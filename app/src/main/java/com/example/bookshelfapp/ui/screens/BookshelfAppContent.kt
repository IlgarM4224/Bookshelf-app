package com.example.bookshelfapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.BookItem
import com.example.bookshelfapp.model.BooksResponse
import com.example.bookshelfapp.model.Genre
import com.example.bookshelfapp.ui.CurrentScreen
import com.example.bookshelfapp.ui.components.TopAppBar


@Composable
fun BookshelfAppContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onGenreClick: (Genre) -> Unit,
    onBookClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    selectedIndex: Int,
    canNavigateBack: Boolean = true,
    bookshelfResponse: BooksResponse = BooksResponse()
) {
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
                onBackClick = onBackClick,
                canNavigateBack = canNavigateBack
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
                        onGenreClick(it)
                        navController.navigate(CurrentScreen.Books.name)
                    }
                )
            }

            composable(route = CurrentScreen.Books.name) {
                BookCoversGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = dimensionResource(R.dimen.padding_small)),
                    books = bookshelfResponse.items,
                    selectBookIndex = {
                        onBookClick(it)
                        navController.navigate(CurrentScreen.Info.name)
                    }
                )
            }

            composable(route = CurrentScreen.Info.name) {
                BookInfo(
                    modifier = Modifier.statusBarsPadding(),
                    volumeInfo = bookshelfResponse.items?.getOrNull(selectedIndex)?.volumeInfo,
                )
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