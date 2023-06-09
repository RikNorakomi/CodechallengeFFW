package com.vanvelzen.codechallengeffw.android.ui.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vanvelzen.codechallengeffw.android.ui.shared.CustomDivider
import com.vanvelzen.codechallengeffw.android.ui.shared.PlaceholderEmptyState
import com.vanvelzen.codechallengeffw.android.ui.shared.PlaceholderErrorState
import com.vanvelzen.codechallengeffw.android.ui.shared.PlaceholderLoadingState
import com.vanvelzen.codechallengeffw.android.ui.shared.shimmerBrush
import com.vanvelzen.codechallengeffw.data.DummyDataSwapi
import com.vanvelzen.codechallengeffw.models.StarWarsCharacter
import com.vanvelzen.codechallengeffw.ui.OverviewScreenViewModel
import com.vanvelzen.codechallengeffw.ui.UiStateOverview.Error
import com.vanvelzen.codechallengeffw.ui.UiStateOverview.Loading
import com.vanvelzen.codechallengeffw.ui.UiStateOverview.Success
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OverViewScreen(
    onItemClick: (StarWarsCharacter) -> Unit
) {
    val viewModel: OverviewScreenViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsState()

    val refreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(refreshing, { viewModel.onPullToRefresh() })
    var endOfListReached by rememberSaveable { mutableStateOf(false) }

    when (state) {
        is Error ->
            PlaceholderWithPullToRefresh(
                placeholder = { PlaceholderErrorState(error = (state as Error).errorMessage) },
                refreshing = refreshing,
                pullRefreshState = pullRefreshState
            )
        is Loading -> PlaceholderLoadingState()
        is Success -> {
            endOfListReached = false
            val characters = (state as Success).people
            val canLoadMore = (state as Success).canLoadMore
            if (characters.isEmpty()) PlaceholderWithPullToRefresh(
                placeholder = { PlaceholderEmptyState() },
                refreshing = refreshing,
                pullRefreshState = pullRefreshState
            )
            else CharacterList(
                people = characters,
                onItemClick = { people -> onItemClick(people) },
                refreshing = refreshing,
                pullRefreshState = pullRefreshState,
                canLoadMore = canLoadMore,
                onEndOfListReached = {
                    if (!endOfListReached) {
                        endOfListReached = true
                        viewModel.onEndOfListReached()
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlaceholderWithPullToRefresh(
    placeholder: @Composable () -> Unit,
    refreshing: Boolean,
    pullRefreshState: PullRefreshState
) {
    Box(Modifier.pullRefresh(pullRefreshState)) {
        placeholder()
        PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CharacterList(
    people: List<StarWarsCharacter>,
    onItemClick: (StarWarsCharacter) -> Unit,
    refreshing: Boolean,
    pullRefreshState: PullRefreshState,
    onEndOfListReached: () -> Unit,
    canLoadMore: Boolean,
) {
    val listState = rememberLazyListState()
    if (!listState.canScrollForward && canLoadMore) onEndOfListReached()

    Box(Modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(all = 8.dp)
                .padding(top = 20.dp)
        ) {
            items(people) { character ->
                StarWarsCharacterRow(character) {
                    onItemClick(it)
                }
                CustomDivider()
            }
            if (canLoadMore) item {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(50.dp),
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
        }
        PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun StarWarsCharacterRow(character: StarWarsCharacter, onClick: (StarWarsCharacter) -> Unit) {
    val showShimmer = remember { mutableStateOf(true) }
    Row(
        Modifier
            .clickable { onClick(character) }
            .padding(10.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(character.imageUrl)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .clip(CircleShape)
                .background(color = Color.DarkGray)
                .background(
                    shimmerBrush(
                        targetValue = 1300f,
                        showShimmer = showShimmer.value
                    )
                )
                .width(50.dp)
                .height(50.dp),
            contentDescription = character.name,
            contentScale = ContentScale.Crop,
            onSuccess = { showShimmer.value = false },
            onError = {showShimmer.value = false },
        )
        Text(
            text = character.name,
            color = MaterialTheme.colors.onBackground,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            fontSize = 18.sp,
            modifier = Modifier
                .weight(1F)
                .padding(horizontal = 20.dp)
                .align(CenterVertically)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun MainScreenContentPreview_Success() {
    val refreshing = false
    val pullRefreshState = rememberPullRefreshState(refreshing, { })
    CharacterList(
        people = DummyDataSwapi.items,
        onItemClick = {},
        refreshing = refreshing,
        pullRefreshState = pullRefreshState,
        onEndOfListReached = {},
        canLoadMore = true,
    )
}
