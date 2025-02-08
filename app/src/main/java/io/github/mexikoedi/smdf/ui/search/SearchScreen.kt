package io.github.mexikoedi.smdf.ui.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import io.github.mexikoedi.smdf.data.api.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    baseUrl: String,
    onNavigateToDetail: (Long) -> Unit,
    viewModel: MovieViewModel = hiltViewModel()
) {
    var query by rememberSaveable { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Movie Search",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        )

                        Text(
                            "© 2025-${java.time.Year.now()} mexikoedi",
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                            ),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            SearchBar(
                query = query,
                onQueryChange = {
                    query = it
                    viewModel.searchMovies(query)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (query.isNotEmpty() && uiState.movies.isEmpty()) {
                Text(
                    text = "No results found",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else if (uiState.movies.isNotEmpty()) {
                SearchResultsListWithLoadMore(
                    searchResults = uiState.movies,
                    isLoading = uiState.isLoading,
                    canLoadMore = uiState.canLoadMore,
                    baseUrl = baseUrl,
                    onLoadMoreClick = {
                        viewModel.loadNextPage()
                    },
                    onItemClick = { movie ->
                        onNavigateToDetail(movie.id)
                    }
                )
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    val borderColor =
        if (MaterialTheme.colorScheme.surface == Color.White) Color.Gray else Color.LightGray

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
            .border(BorderStroke(1.dp, borderColor), shape = MaterialTheme.shapes.medium)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            textStyle = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.fillMaxWidth()
        )

        if (query.isEmpty()) {
            Text(
                text = "Search for movies...",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                style = TextStyle(fontSize = 16.sp)
            )
        }
    }
}

@Composable
fun MovieListItem(movie: Movie, baseUrl: String, onClick: () -> Unit) {
    val borderColor =
        if (MaterialTheme.colorScheme.surface == Color.White) Color.Gray else Color.LightGray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
            .border(BorderStroke(1.dp, borderColor), shape = MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = "$baseUrl${movie.posterPath}"),
            contentDescription = movie.title,
            modifier = Modifier
                .size(60.dp)
                .background(
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    MaterialTheme.shapes.small
                ),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = movie.title,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun SearchResultsListWithLoadMore(
    searchResults: List<Movie>,
    isLoading: Boolean,
    canLoadMore: Boolean,
    baseUrl: String,
    onLoadMoreClick: () -> Unit,
    onItemClick: (Movie) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(searchResults.size) { index ->
                MovieListItem(
                    movie = searchResults[index],
                    baseUrl = baseUrl,
                    onClick = {
                        onItemClick(searchResults[index])
                    }
                )
            }
        }

        if (canLoadMore && searchResults.size >= 20) {
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onLoadMoreClick,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Load More")
                }
            }
        }
    }
}