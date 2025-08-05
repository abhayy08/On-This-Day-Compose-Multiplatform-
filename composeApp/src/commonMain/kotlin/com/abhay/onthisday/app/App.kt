package com.abhay.onthisday.app

import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
@Preview
fun App() {

    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Routes.MainGraph
        ) {
            navigation<Routes.MainGraph>(
                startDestination = Routes.HomeScreen
            ) {
                composable<Routes.HomeScreen> {
                    val viewModel = koinViewModel<HomeViewModel>()
                    HomeScreen(
                        viewModel = viewModel,
                        onEventClicked = { identifierTitle ->
                            navController.navigate(Routes.DetailsScreen(identifierTitle = identifierTitle))
                        }
                    )
                }

                composable<Routes.DetailsScreen> {
                    val args = it.toRoute<Routes.DetailsScreen>()

                    val viewmodel = koinViewModel<DetailsViewModel>()
                    viewmodel.getDetailOf(args.identifierTitle)

                    DetailsScreen(identifierTitle = args.identifierTitle)
                }
            }
        }
    }
}