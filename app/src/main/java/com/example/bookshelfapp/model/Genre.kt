package com.example.bookshelfapp.model

enum class Genre(val displayName: String) {
    Romance("romance"),
    Fantasy("fantasy"),
    ThrillerAndSuspense("thriller and suspense"),
    Mystery("mystery"),
    ScienceFiction("science fiction"),
    HistoricalFiction("historical fiction"),
    Horror("horror"),
    History("history"),
    Dystopian("dystopian"),
    ActionAndAdventure("action and adventure"),
    ContemporaryFiction("contemporary fiction"),
    ShortStory("short story")
}

object GenreProvider {
   private val listOfGenres = listOf(
        Genre.Dystopian.displayName,
        Genre.ThrillerAndSuspense.displayName,
        Genre.Romance.displayName,
        Genre.Fantasy.displayName,
        Genre.Mystery.displayName,
        Genre.ActionAndAdventure.displayName,
        Genre.Horror.displayName,
        Genre.ShortStory.displayName,
        Genre.HistoricalFiction.displayName,
        Genre.ScienceFiction.displayName,
        Genre.ContemporaryFiction.displayName,
        Genre.History.displayName,
    )

    fun getGenres() = listOfGenres
}
