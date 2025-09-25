package io.github.mexikoedi.smdf.data.database

import io.github.mexikoedi.smdf.data.api.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalMovieRepository @Inject constructor(
    private val localMovieDao: LocalMovieDao
) {
    fun addMovies(movies: List<Movie>): Flow<Unit> = flow {
        localMovieDao.addMovies(movies.map { movie ->
            LocalMovie(
                id = movie.id,
                title = movie.title,
                overview = movie.overview,
                posterPath = movie.posterPath,
                voteAverage = movie.voteAverage,
                genres = movie.genres,
                releaseDate = movie.releaseDate,
                runtime = movie.runtime
            )
        })
        emit(Unit)
    }.flowOn(Dispatchers.IO)

    fun getMovieDetails(id: Int): Flow<Movie> {
        return localMovieDao.getMovieDetails(id).map { localMovie ->
            Movie(
                id = localMovie.id,
                title = localMovie.title,
                overview = localMovie.overview,
                posterPath = localMovie.posterPath,
                voteAverage = localMovie.voteAverage,
                genres = localMovie.genres,
                releaseDate = localMovie.releaseDate,
                runtime = localMovie.runtime
            )
        }
    }
}