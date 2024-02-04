package com.example.movieapplicationv2.ui.detailscreen

import android.R
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapplicationv2.common.UiState
import com.example.movieapplicationv2.di.Injection
import com.example.movieapplicationv2.model.movie.MovieDetail
import com.example.movieapplicationv2.ui.detailscreen.viewmodel.DetailScreenViewModel
import com.example.movieapplicationv2.common.ViewModelFactory
import com.example.movieapplicationv2.ui.listscreen.showToast

/**
 * Created by Alo-BambangHariantoSianturi on 27/11/23.
 */
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    onNextButtonClicked: (String) -> Unit,
    movieId: String,
    viewModel: DetailScreenViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    )
) {

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
        Log.d("data", it.toString())
        when (it) {
            is UiState.Loading -> viewModel.getMovieDetail(movieId)
            is UiState.Success -> {
                val data = it.data
                SetListData(modifier, data, onNextButtonClicked)
            }

            is UiState.Error -> it.errorMessage
        }
    }
}

@Composable
fun SetListData(modifier: Modifier, data: MovieDetail, onNextButtonClicked: (String) -> Unit) {
    showToast(LocalContext.current, data.title)
    val padding = 16.dp
    Column(Modifier.fillMaxWidth()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/original/${data.backdrop_path}")
                .build(),
            placeholder = painterResource(id = R.drawable.ic_menu_manage),
            contentDescription = stringResource(id = R.string.ok),
            contentScale = ContentScale.Fit,
            modifier = modifier
                .fillMaxWidth()
                .align(CenterHorizontally)
                .clickable { onNextButtonClicked(data.title) }
        )
        Spacer(modifier.size(8.dp))

        Text(
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            text = data.overview,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        )
    }
}

@Preview
@Composable
fun SetListDataPreview() {
//    com.example.movieapplicationv2.ui.listscreen.SetListData(modifier = Modifier,
//        data = Movies(0, listOf(), 0, totalResults = 0)
//    ) {}
}