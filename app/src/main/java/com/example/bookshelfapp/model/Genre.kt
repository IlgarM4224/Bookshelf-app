package com.example.bookshelfapp.model

import androidx.annotation.DrawableRes
import com.example.bookshelfapp.R


enum class Genre(val displayName: String, @DrawableRes val iconId: Int) {
    Romance(displayName = "romance", iconId = R.drawable.romance ),
    Fantasy(displayName = "fantasy", iconId = R.drawable.fantasy ),
    ThrillerAndSuspense(displayName = "thriller and suspense", iconId = R.drawable.thrillerandsuspense),
    Mystery(displayName = "mystery", iconId = R.drawable.mystery),
    ScienceFiction(displayName = "science fiction", iconId = R.drawable.sciencefiction),
    HistoricalFiction(displayName = "historical fiction", iconId = R.drawable.historicalfiction),
    Horror(displayName = "horror", iconId = R.drawable.horror),
    History(displayName = "history", iconId = R.drawable.history),
    Dystopian(displayName = "dystopian", iconId = R.drawable.dystopian),
    ActionAndAdventure(displayName = "action and adventure", iconId = R.drawable.actionandadventure),
    ContemporaryFiction(displayName = "contemporary fiction", iconId = R.drawable.contemporaryfiction),
    ShortStory(displayName = "short story", iconId = R.drawable.shortstory)
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
