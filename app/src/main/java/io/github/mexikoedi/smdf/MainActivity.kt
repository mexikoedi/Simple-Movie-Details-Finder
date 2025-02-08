package io.github.mexikoedi.smdf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import io.github.mexikoedi.smdf.ui.NavGraph
import io.github.mexikoedi.smdf.data.api.TmdbMovieApi
import io.github.mexikoedi.smdf.ui.theme.SMDFTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var tmdbMovieApi: TmdbMovieApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SMDFTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    NavGraph(baseUrl = tmdbMovieApi.baseUrl)
                }
            }
        }
    }
}