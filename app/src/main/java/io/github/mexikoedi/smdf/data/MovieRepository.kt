package io.github.mexikoedi.smdf.data

import io.github.mexikoedi.smdf.data.api.Movie
import io.github.mexikoedi.smdf.data.api.MovieResultDto
import io.github.mexikoedi.smdf.data.api.TmdbMovieApi
import io.github.mexikoedi.smdf.data.database.LocalMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: TmdbMovieApi,
    private val localMovieRepository: LocalMovieRepository
) {
    fun searchMovies(query: String, page: Int): Flow<List<Movie>> = flow {
        val result = api.searchMovies(query, page)
        val movies: List<Movie> = result.getOrElse { MovieResultDto(emptyList()) }.results
        localMovieRepository.addMovies(movies).collect { }
        emit(movies)
    }.flowOn(Dispatchers.IO)

    fun getMovieDetails(movieId: Int): Flow<Movie?> = flow {
        val localMovie = localMovieRepository.getMovieDetails(movieId).firstOrNull()

        if (localMovie != null) {
            if (localMovie.genres.isEmpty() || localMovie.runtime == null) {
                val remoteMovie = api.getMovieDetails(movieId).getOrNull()

                if (remoteMovie != null) {
                    val updatedLocalMovie =
                        localMovie.copy(genres = remoteMovie.genres, runtime = remoteMovie.runtime)
                    localMovieRepository.addMovies(listOf(updatedLocalMovie)).collect { }
                    emit(remoteMovie)
                } else {
                    emit(localMovie)
                }
            } else {
                emit(localMovie)
            }
        } else {
            val remoteMovie = api.getMovieDetails(movieId).getOrNull()

            if (remoteMovie != null) {
                localMovieRepository.addMovies(listOf(remoteMovie)).collect { }
                emit(remoteMovie)
            } else {
                emit(null)
            }
        }
    }.flowOn(Dispatchers.IO)
}