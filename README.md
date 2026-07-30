# Bookshelf App

![Android](https://img.shields.io/badge/Platform-Android-green.svg?style=flat&logo=android)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple.svg?style=flat&logo=kotlin)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4.svg?style=flat&logo=jetpackcompose)
![Architecture](https://img.shields.io/badge/Architecture-MVVM-orange.svg?style=flat)

**Bookshelf** is an adaptive Android application built to browse and search books using the **Google Books API**. It enables users to explore different literary genres, load more results on demand, and view comprehensive details for any selected title across multiple device screen sizes.

## Features

* **Genre Catalog**: Explore and select from popular book genres.
* **Book Collection**: Browse covers, titles, and authors for any selected genre.
* **Pagination (Load More)**: Fetch and append more books seamlessly using the *More* button.
* **Detailed View**: Access full descriptions, publication dates and author details.
* **Adaptive UI**: Responsive layouts tailored for compact devices (phones) and expanded screens (tablets).
* **State Management**:
    * **Loading**: Clear visual feedback while fetching remote data.
    * **Error**: Friendly offline/failure screens featuring a **Retry** button to re-trigger network calls.

## Visuals

| Full functionality                                                 |                              Error page                              |                                                      More button |
|:-------------------------------------------------------------------|:--------------------------------------------------------------------:|-----------------------------------------------------------------:|
| <img src="media/Compact.gif" width="280" alt="Compact screen" />   | <img src="media/ErrorAndRetry.gif" width="280" alt="Error screen" /> | <img src="media/MoreButton.gif" width="280" alt="More button" /> |

### On the tablet
<img src="media/Large.gif" height="555" alt="Grid screen" />

## Setup & API Key Configuration

To request data from the Google Books API without strict rate limits, configure your API key locally:

1. Obtain a **Google Books API Key** from the [Google Cloud Console](https://console.cloud.google.com/).
2. Open your project's root directory and locate or create the `local.properties` file.
3. Add your API key entry:
   ```properties
   BOOKS_API_KEY=your_key_here
   ```

> ⚠️ **Note**: `local.properties` is ignored by Git, ensuring your API key remains private.

## Tech Stack & Architecture

- **Language:** [Kotlin](https://kotlinlang.org/)
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Declarative UI)
- **Architecture:** MVVM (Model-View-ViewModel) + Repository Pattern
- **Concurrency**: Kotlin Coroutines & `StateFlow` / `Flow`
- **Network**: [Retrofit 2](https://square.github.io/retrofit/) + kotlinx.serialization
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/) (Image loading library for Compose)
- **DI**: Dependency Injection (AppContainer / Manual DI)