package com.example.movieapplicationv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapplicationv2.common.Screen
import com.example.movieapplicationv2.ui.DetailScreen2
import com.example.movieapplicationv2.ui.detailscreen.DetailScreen
import com.example.movieapplicationv2.ui.listscreen.MovieListScreen
import com.example.movieapplicationv2.ui.theme.MovieApplicationv2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MovieApplicationv2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MovieApp("Music App")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieApp(
    name: String,
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
) {
    var mTitle = ""
    val backStackEntry by navHostController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route

    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            title = {
//                var tempTitle = ""
                val tempTitle = when (currentScreen) {
                    Screen.Detail2.route -> {
                        "Bambang Harianto"
                    }
                    Screen.Detail.route -> {
                        mTitle
                    }
                    else -> {
                        name
                    }
                }

                Text(
                    text = tempTitle,
//                    mTitle.ifEmpty { name },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
            },
            navigationIcon = {
                if (currentScreen != Screen.Home.route) {
                    IconButton(onClick = { navHostController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "about_page",
                            tint = Color.White
                        )
                    }
                }
            },
        )
    }) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = Screen.Home.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = Screen.Home.route) {
                MovieListScreen(modifier, onNextButtonClicked = { movie ->
//                    navHostController.navigate(Screen.Detail.createRoute(movie.id, movie.title))
                })
            }
            composable(route = Screen.Detail.route,
                arguments = listOf(
                    navArgument("movieId") { type = NavType.StringType },
                    navArgument("movieTitle") {type = NavType.StringType}
                    )) {
                val movieId = it.arguments?.getString("movieId") ?: ""
                mTitle = it.arguments?.getString("movieTitle") ?: ""
                DetailScreen(movieId = movieId, onNextButtonClicked = { movieTitle ->
                    navHostController.navigate(Screen.Detail2.createRoute(movieTitle))
                })
            }
            composable(route = Screen.Detail2.route,
                arguments = listOf(
                    navArgument("movieTitle") {type = NavType.StringType}
                )
            ) {
                val movieTitle = it.arguments?.getString("movieTitle") ?: ""
                DetailScreen2(movieTitle)
            }
        }
    }
}