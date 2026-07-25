package com.example.bookshelfapp.model

import androidx.annotation.DrawableRes
import com.example.bookshelfapp.R


enum class Genre(val displayName: String, @DrawableRes val iconId: Int) {
    Romance(displayName = "Romance", iconId = R.drawable.romance ),
    Fantasy(displayName = "Fantasy", iconId = R.drawable.fantasy ),
    ThrillerAndSuspense(displayName = "Thriller and suspense", iconId = R.drawable.thrillerandsuspense),
    Mystery(displayName = "Mystery", iconId = R.drawable.mystery),
    ScienceFiction(displayName = "Science fiction", iconId = R.drawable.sciencefiction),
    HistoricalFiction(displayName = "Historical fiction", iconId = R.drawable.historicalfiction),
    Horror(displayName = "Horror", iconId = R.drawable.horror),
    History(displayName = "History", iconId = R.drawable.history),
    Dystopian(displayName = "Dystopian", iconId = R.drawable.dystopian),
    ActionAndAdventure(displayName = "Action and adventure", iconId = R.drawable.actionandadventure),
    ContemporaryFiction(displayName = "Contemporary fiction", iconId = R.drawable.contemporaryfiction),
    ShortStory(displayName = "Short story", iconId = R.drawable.shortstory)
}

object GenreProvider {
   private val listOfGenres = listOf(
        Genre.Dystopian,
        Genre.ThrillerAndSuspense,
        Genre.Romance,
        Genre.Fantasy,
        Genre.Mystery,
        Genre.ActionAndAdventure,
        Genre.Horror,
        Genre.ShortStory,
        Genre.HistoricalFiction,
        Genre.ScienceFiction,
        Genre.ContemporaryFiction,
        Genre.History,
    )

    fun getGenres() = listOfGenres
}
