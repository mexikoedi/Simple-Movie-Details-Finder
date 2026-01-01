# Simple Movie Details Finder

## What is this?
This is a simple Android application that helps you find details of movies. <br>
You can search for a movie by its name and get details like the poster, score, genres, release date, runtime, and overview.

## Is this useful to me?
If you are a movie enthusiast or someone who frequently looks up movie details, this app can be very useful. <br>
It provides a quick and easy way to get comprehensive information about movies directly on your Android device.

## Limitations?
1) Only Android devices are supported from version 11 (API level 30) and above.
2) No APK file is provided in this repository. You need to build the app yourself to use it. (See [How to use](#How-To-Use) section below.)

## How to use?
An IDE like [Android Studio](https://developer.android.com/studio) or similar is required to open this project. <br>
You can download the project by selecting the green button named "Code" then "Download ZIP". <br>
After downloading, you can extract the files and open them with your IDE. <br>
Or you can clone the project and open it directly in your IDE. <br>
Afterwards, you need to have an API key from [The Movie Database (TMDB)](https://developer.themoviedb.org/docs/getting-started) to use the app. <br>
Be aware that you need to have an account there to get an API key. <br>
After you get your API key, you need to put it in the `local.properties` file in the project. <br>
The `local.properties` file needs to be located in the root directory of the project. <br>
Add the API key like this to the file: `TMDB_API_KEY=your_api_key_here` <br>
After that, you can run the app on an emulator or a real device.

## How does it work?
The app was built with the "Recommended Architecture for Android Apps" in mind. <br>
It uses many different modern technologies. <br>
Here are some of the key technologies used in the app:
- Kotlin: The primary programming language used for Android development.
- Gradle: A build system used to automate building, testing, and deploying Android apps.
- Jetpack Compose: A modern toolkit for building native Android UI.
- Hilt: A dependency injection library for managing dependencies.
- Room: A database library for storing and managing data locally.
- Ktor: A networking library for making HTTP requests.
- Coil: An image loading library for loading images from URLs.

The app uses the TMDB API to fetch movie details. <br>
When you search for a movie, the app makes a request to the API with the movie name. <br>
The API returns a list of twenty movies that match the search query. <br>
You can load more movies by pressing the "Load More" button at the bottom of the list if there are more than twenty movies available. <br>
If you want to view the movie details, just click on a movie to learn more about it. <br>
The app also stores the data locally using the Room database. <br>
This prevents the app from making unnecessary network requests when the same movie is searched again.

## Examples
Dark Mode: <br>
![Movie Search](https://github.com/user-attachments/assets/9c8b2d3e-cfb4-4cfc-b1d0-3dd6eabdc28e "Movie Search") <br>
![Movie Details](https://github.com/user-attachments/assets/372c3851-6833-4981-baf2-c8ddda5971b5 "Movie Details")

Light Mode: <br>
![Movie Search](https://github.com/user-attachments/assets/307cdcb1-d9f3-4570-935b-bfddf7678a74 "Movie Search") <br>
![Movie Details](https://github.com/user-attachments/assets/a76f8e72-3a28-4d00-800e-5cee1f9ed24c "Movie Details")

Thanks to [Colin Bankart](https://github.com/Ironwally) for the help with some code parts.

© 2025-2026 mexikoedi

All rights reserved.
