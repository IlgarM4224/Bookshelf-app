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
