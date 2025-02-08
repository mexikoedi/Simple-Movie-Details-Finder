package io.github.mexikoedi.smdf.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import io.github.mexikoedi.smdf.data.api.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Entity(tableName = "movies")
@TypeConverters(GenreListConverter::class)
data class LocalMovie(
    @PrimaryKey val id: Long,
    val title: String,
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("vote_average")
    val voteAverage: Float,
    val genres: List<Genre> = emptyList(),
    @SerialName("release_date")
    val releaseDate: String?,
    val runtime: Int?
)

class GenreListConverter {
    @TypeConverter
    fun fromGenreList(genres: List<Genre>): String {
        return Json.encodeToString(genres)
    }

    @TypeConverter
    fun toGenreList(genresString: String): List<Genre> {
        return Json.decodeFromString(genresString)
    }
}