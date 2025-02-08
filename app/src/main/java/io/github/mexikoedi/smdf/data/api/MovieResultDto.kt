package io.github.mexikoedi.smdf.data.api

import kotlinx.serialization.Serializable

@Serializable
data class MovieResultDto(
    val results: List<Movie>
)