package io.github.mexikoedi.smdf.data.api

import io.github.mexikoedi.smdf.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

class TmdbMovieApi(private val client: HttpClient) {
    val baseUrl = "https://image.tmdb.org/t/p/w500"

    suspend fun searchMovies(query: String, page: Int): Result<MovieResultDto> {
        return runCatching {
            val response: HttpResponse =
                client.get("https://api.themoviedb.org/3/search/movie") {
                    parameter("query", query)
                    parameter("page", page)
                    parameter("include_adult", false)
                    parameter("language", "en-US")
                    header("accept", "application/json")
                    header("Authorization", "Bearer ${BuildConfig.TMDB_API_KEY}")
                }

            response.body()
        }
    }

    suspend fun getMovieDetails(movieId: Int): Result<Movie> {
        return runCatching {
            val response: HttpResponse =
                client.get("https://api.themoviedb.org/3/movie/$movieId") {
                    parameter("api_key", BuildConfig.TMDB_API_KEY)
                    header("accept", "application/json")
                    header("Authorization", "Bearer ${BuildConfig.TMDB_API_KEY}")
                }

            response.body()
        }
    }
}