package com.example.bookshelfapp.model

import kotlinx.serialization.Serializable

@Serializable
data class BooksResponse(
    val totalItems: Int? = null,
    val items: List<BookItem>? = null
)

@Serializable
data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo? = null
)

@Serializable
data class VolumeInfo(
    val title: String? = null,
    val authors: List<String>? = null,
    val publisher: String? = null,
    val publishedDate: String? = null,
    val description: String? = null,
    val pageCount: Int? = null,
    val categories: List<String>? = null,
    val imageLinks: ImageLinks? = null,
    val language: String? = null
)

@Serializable
data class ImageLinks(
    val thumbnail: String? = null,
) {
    val secureThumbnail: String?
        get() = thumbnail?.replace("http://", "https://")
}