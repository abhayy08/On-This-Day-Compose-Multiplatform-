package com.abhay.onthisday.app

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.abhay.onthisday.presentation.details.DetailsScreen
import com.abhay.onthisday.presentation.details.DetailsViewModel
import com.abhay.onthisday.presentation.home_screen.HomeScreen
import com.abhay.onthisday.presentation.home_screen.HomeViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun App() {
    MaterialTheme {
        OnThisDayNavGraph()
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview
fun OnThisDayNavGraph() {

    val navController = rememberNavController()

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = Routes.MainGraph,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(400)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(400)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(400)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(400)
                )
            }
        ) {
            navigation<Routes.MainGraph>(
                startDestination = Routes.HomeScreen
            ) {
                composable<Routes.HomeScreen> {
                    val viewModel = koinViewModel<HomeViewModel>()

                    HomeScreen(
                        animatedVisibilityScope = this@composable,
                        viewModel = viewModel,
                        onEventClicked = { identifierTitle, image, title ->
                            navController.navigate(
                                Routes.DetailsScreen(
                                    identifierTitle = identifierTitle,
                                    imageLink = image,
                                    title = title
                                )
                            )
                        }
                    )
                }

                composable<Routes.DetailsScreen> {
                    val args = it.toRoute<Routes.DetailsScreen>()
                    val viewModel = koinViewModel<DetailsViewModel>()

                    DetailsScreen(
                        animatedVisibilityScope = this@composable,
                        identifierTitle = args.identifierTitle,
                        viewModel = viewModel,
                        onBackPressed = {
                            navController.popBackStack()
                        },
                        imageLink = args.imageLink.ifBlank { "" },
                        title = args.title
                    )
                }
            }
        }
    }

}