package br.com.zemaromba.core_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.zemaromba.common.extensions.openVideoInYoutubeOrBrowser
import br.com.zemaromba.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.feature.exercise.presentation.router.ExerciseRouter
import br.com.zemaromba.feature.exercise.presentation.router.exerciseGraph
import br.com.zemaromba.feature.home.presentation.model.MenuHome
import br.com.zemaromba.feature.home.presentation.router.HomeRouter
import br.com.zemaromba.feature.home.presentation.router.homeGraph
import br.com.zemaromba.feature.onboarding.presentation.router.OnBoardingRouter
import br.com.zemaromba.feature.onboarding.presentation.router.onBoardingGraph
import br.com.zemaromba.feature.training_plan.presentation.router.TrainingPlanRouter
import br.com.zemaromba.feature.training_plan.presentation.router.trainingPlanGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZeMarombaTheme {
                BoxWithConstraints(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = OnBoardingRouter.OnBoardingGraph.route
                    ) {
                        onBoardingGraph(
                            navController = navController,
                            width = constraints.maxWidth,
                            onFinishOnBoarding = {
                                navController.navigate(route = HomeRouter.HomeGraph.route) {
                                    popUpTo(OnBoardingRouter.OnBoardingGraph.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                        homeGraph(
                            width = constraints.maxWidth,
                            navigateTo = {
                                when (it) {
                                    MenuHome.TRAINING_PLAN_SCREEN -> {
                                        navController.navigate(
                                            route = TrainingPlanRouter.TrainingPlanGraph.route
                                        )
                                    }

                                    MenuHome.EXERCISES_SCREEN -> {
                                        navController.navigate(ExerciseRouter.ExerciseGraph.route)
                                    }
                                }
                            }
                        )
                        exerciseGraph(
                            width = constraints.maxWidth,
                            navController = navController,
                            openYoutube = { videoId: String ->
                                openVideoInYoutubeOrBrowser(videoId = videoId)
                            }
                        )
                        trainingPlanGraph(
                            width = constraints.maxWidth,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}