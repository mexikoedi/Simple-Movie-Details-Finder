package io.github.mexikoedi.smdf.data.api

import androidx.room.Entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("vote_average")
    val voteAverage: Float,
    val genres: List<Genre> = emptyList(),
    @SerialName("release_date")
    val releaseDate: String? = null,
    val runtime: Int? = null
)

@Serializable
data class Genre(
    val id: Int,
    val name: String
)