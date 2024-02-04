package com.example.movieapplicationv2.ui.listscreen

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.movieapplicationv2.R
import com.example.movieapplicationv2.common.UiState
import com.example.movieapplicationv2.common.ViewModelFactory
import com.example.movieapplicationv2.data.MovieRepository
import com.example.movieapplicationv2.di.Injection
import com.example.movieapplicationv2.model.musiclist.TrackList
import com.example.movieapplicationv2.ui.listscreen.viewmodel.MovieListScreenViewModel
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Created by Alo-BambangHariantoSianturi on 20/11/23.
 */
private var mMediaPlayer: MediaPlayer? = null
private var isReady: Boolean = false
private var searchArtist: String = "Eminem"

@Composable
fun MovieListScreen(
    modifier: Modifier = Modifier,
    onNextButtonClicked: (TrackList) -> Unit,
    viewModel: MovieListScreenViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
        Log.d("data", it.toString())
        when (it) {
            is UiState.Loading -> viewModel.getMusicTrack(searchArtist)
            is UiState.Success -> {
                val data = it.data.data.orEmpty()
                SetListData(viewModel, modifier, data, onNextButtonClicked)
            }

            is UiState.Error -> it.errorMessage
            else -> {}
        }
    }

    viewModel.buttonStateFlow.collectAsState().value.let {

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SetListData(
    viewModel: MovieListScreenViewModel,
    modifier: Modifier,
    data: List<TrackList>,
    onNextButtonClicked: (TrackList) -> Unit
) {
    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }
        var changeImage by rememberSaveable { mutableStateOf(false) }
        val isChangeButton by viewModel.buttonStateFlow.collectAsState()
        val testChange by rememberSaveable {
            mutableStateOf(isChangeButton)
        }

        LazyColumn {
            items(data) { item ->
                HeroListItem(
                    viewModel,
                    item,
                    selectedHero = {
                        init(viewModel, item.preview, item.id ?: 0.0)
                        changeImage = true
                    })
                Divider()
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.purple_200))
                .padding(all = 8.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(onClick = {
                changeImage = !changeImage
                if (mMediaPlayer != null && mMediaPlayer?.isPlaying as Boolean) {
                    mMediaPlayer?.pause()
                } else {
                    mMediaPlayer?.start()
                }
            }, content = {
                if (changeImage) {
                    Image(
                        painter = painterResource(id = android.R.drawable.ic_media_pause),
                        contentDescription = ""
                    )
                } else if (!testChange) {
                    Image(
                        painter = painterResource(id = android.R.drawable.ic_media_play),
                        contentDescription = ""
                    )
                } else {
                    Image(
                        painter = painterResource(id = android.R.drawable.ic_media_play),
                        contentDescription = ""
                    )
                }
            })
        }

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(
                    Alignment.BottomCenter
                )
        ) {
            ScrollToTopButton(onClick = { scope.launch { listState.scrollToItem(index = 0) } })
        }
    }
}

@Composable
fun HeroListItem(
    viewModel: MovieListScreenViewModel,
    item: TrackList,
    modifier: Modifier = Modifier,
    selectedHero: (String) -> Unit,
) {
    val tempId by viewModel.musicStatePosition.collectAsState()

    val showEqualizerImage: Boolean by remember {
        derivedStateOf { tempId == item.id }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = modifier.clickable { selectedHero(item.title.orEmpty()) }
    ) {
        SubcomposeAsyncImage(
            model = item.artist.picture,
            alignment = Alignment.CenterStart,
            contentDescription = "Movie Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(8.dp)
                .size(80.dp)
        )

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.Start,
            modifier = modifier.width(200.dp)
        ) {
            Text(
                textAlign = TextAlign.Start,
                maxLines = 1,
                fontSize = 16.sp,
                text = item.title.orEmpty(),
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = 16.dp)
            )

            Text(
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                maxLines = 1,
                text = item.artist.name.orEmpty(),
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = 16.dp)
            )

            Text(
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                maxLines = 1,
                text = item.album.title.orEmpty(),
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        }

        val imageLoader = ImageLoader.Builder(LocalContext.current)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()

        if (showEqualizerImage) {
            Image(
                painter = rememberAsyncImagePainter(R.drawable.eq_lottie, imageLoader),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(50.dp)
            )
        }

    }
}

@Preview
@Composable
fun HeroListItemPreview() {
    HeroListItem(
        viewModel = MovieListScreenViewModel(repository = MovieRepository()),
        item = TrackList(
            id = null,
            title = null,
            preview = null,
            album = TrackList.Album(id = null, title = null),
            artist = TrackList.Artist(id = null, name = null, picture = null)
        ),
        selectedHero = {})
}

fun showToast(context: Context, name: String) {
    Toast.makeText(context, name, Toast.LENGTH_SHORT).show()
}

private fun init(viewModel: MovieListScreenViewModel, preview: String?, id: Double) {
    viewModel.setMusicPosition(id)
    mMediaPlayer?.stop()
    isReady = false

    mMediaPlayer = MediaPlayer()
    val attribute = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_MEDIA)
        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        .build()
    mMediaPlayer?.setAudioAttributes(attribute)

    try {
        mMediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mMediaPlayer?.setDataSource(preview)
    } catch (e: IOException) {
        e.printStackTrace()
    }

    mMediaPlayer?.setOnPreparedListener {
        isReady = true
        mMediaPlayer?.start()
    }

    mMediaPlayer?.setOnCompletionListener {
        viewModel.setButtonState(false)
//        viewModel.resetButton()
    }

    mMediaPlayer?.setOnErrorListener { _, _, _ -> false }

    if (!isReady) {
        mMediaPlayer?.prepareAsync()
    } else {
        if (mMediaPlayer?.isPlaying as Boolean) {
            mMediaPlayer?.pause()
        } else {
            mMediaPlayer?.start()
        }
    }
}


@Composable
fun ScrollToTopButton(
    onClick: () -> Unit, modifier: Modifier = Modifier
) {
    FilledIconButton(
        onClick = onClick, modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = stringResource(R.string.scroll_to_top),
        )
    }
}


