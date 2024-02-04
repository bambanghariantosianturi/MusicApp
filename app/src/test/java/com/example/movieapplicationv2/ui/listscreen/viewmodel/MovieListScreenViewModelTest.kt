package com.example.movieapplicationv2.ui.listscreen.viewmodel

import com.example.movieapplicationv2.common.UiState
import com.example.movieapplicationv2.data.MovieRepository
import com.example.movieapplicationv2.model.musiclist.MusicEntity
import com.example.movieapplicationv2.utils.DataDummy
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieListScreenViewModelTest {

    @Mock
    private lateinit var movieRepository: MovieRepository
    private lateinit var movieListScreenViewModel: MovieListScreenViewModel
    private val dummyNews = DataDummy.generateDummyMusicEntity()

    @Before
    fun setUp() {
        movieListScreenViewModel = MovieListScreenViewModel(movieRepository)
    }

    @Test
    suspend fun `when Get MusicList Should Not Null and Return Success`() {
        val expectedData = MutableStateFlow<UiState<MusicEntity>>(value = UiState.Success(
            MusicEntity(data = listOf(), total = null, next = "")))
        expectedData.value = UiState.Success(dummyNews)
        `when`(movieRepository.getMusicTrack("eminem")).thenReturn(dummyNews)
        val actualNews = movieListScreenViewModel.getMusicTrack("eminem")
        Assert.assertNotNull(actualNews)
    }
}