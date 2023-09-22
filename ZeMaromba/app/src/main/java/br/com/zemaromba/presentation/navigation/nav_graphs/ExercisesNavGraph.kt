package br.com.zemaromba.presentation.navigation.nav_graphs

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import br.com.zemaromba.common.extensions.composableWithTransitionAnimation
import br.com.zemaromba.common.extensions.orZero
import br.com.zemaromba.presentation.exercises.screen.ExerciseManagementScreen
import br.com.zemaromba.presentation.exercises.screen.ExercisesListScreen
import br.com.zemaromba.presentation.exercises.screen.event.ExerciseManagementEvents
import br.com.zemaromba.presentation.exercises.viewmodel.ExerciseManagementViewModel
import br.com.zemaromba.presentation.exercises.viewmodel.ExercisesListEvents
import br.com.zemaromba.presentation.exercises.viewmodel.ExercisesListViewModel
import br.com.zemaromba.presentation.navigation.destinations.ExerciseDestinations
import br.com.zemaromba.presentation.navigation.destinations.ExerciseDestinations.Params

fun NavGraphBuilder.exerciseGraph(
    navController: NavController,
    openYoutube: (videoId: String) -> Unit,
) {
    navigation(
        startDestination = ExerciseDestinations.ExercisesListScreen.route,
        route = ExerciseDestinations.ExerciseGraph.route
    ) {
        composableWithTransitionAnimation(
            route = ExerciseDestinations.ExercisesListScreen.route
        ) {
            val viewModel: ExercisesListViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            ExercisesListScreen(
                state = state,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToNewExercise = {
                    navController.navigate(
                        route = ExerciseDestinations.ExerciseManagementScreen.getRouteWithExerciseId(
                            exerciseId = 0
                        )
                    )
                },
                onOpenExercise = { exerciseId ->
                    navController.navigate(
                        route = ExerciseDestinations.ExerciseManagementScreen.getRouteWithExerciseId(
                            exerciseId = exerciseId
                        )
                    )
                },
                onFavoriteExercise = { exerciseId, favoriteIcon ->
                    viewModel.onEvent(
                        event = ExercisesListEvents.OnFavoriteExercise(
                            exerciseId = exerciseId,
                            favoriteIcon = favoriteIcon
                        )
                    )
                },
                onSearch = { exerciseName ->
                    viewModel.onEvent(
                        event = ExercisesListEvents.OnSearchExercise(
                            exerciseName = exerciseName
                        )
                    )
                },
                onFilterChange = { exerciseFilter ->
                    viewModel.onEvent(
                        event = ExercisesListEvents.OnFilterChange(exerciseFilter = exerciseFilter)
                    )
                },
                onApplySelectedMuscleGroups = {
                    viewModel.onEvent(event = ExercisesListEvents.OnFilterBySelectedMuscleGroups)
                },
                onMuscleGroupSelection = { index, isSelected ->
                    viewModel.onEvent(
                        event = ExercisesListEvents.OnMuscleGroupSelection(
                            id = index,
                            isSelected = isSelected
                        )
                    )
                },
                onOpenYoutubeApp = { videoId ->
                    openYoutube(videoId)
                }
            )
        }
        composableWithTransitionAnimation(
            route = ExerciseDestinations.ExerciseManagementScreen.route,
            arguments = listOf(
                navArgument(name = Params.exerciseId) {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) {
            val exerciseId = remember {
                it
                    .arguments
                    ?.getLong(Params.exerciseId)
                    .orZero()
            }
            val viewModel: ExerciseManagementViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            LaunchedEffect(key1 = Unit) {
                viewModel.retrieveExercise(exerciseId)
            }
            ExerciseManagementScreen(
                state = state,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onChangeName = { newName ->
                    viewModel
                        .onEvent(event = ExerciseManagementEvents.OnEnterName(exerciseName = newName))
                },
                onChangeUrlLink = {
                    viewModel.updateUrlLinkValue(url = it)
                },
                onSaveExercise = {
                    viewModel.onEvent(event = ExerciseManagementEvents.OnSaveExercise)
                },
                onDeleteExercise = {
                    viewModel.onEvent(ExerciseManagementEvents.OnDeleteExercise)
                },
                onMuscleGroupSelection = { id, isSelected ->
                    viewModel.onEvent(
                        event = ExerciseManagementEvents.OnMuscleGroupSelection(
                            id = id,
                            isSelected = isSelected
                        )
                    )
                },
                onShowAlertAboutRemoving = { showDialog ->
                    viewModel.onEvent(
                        event = ExerciseManagementEvents
                            .OnShowWarningAboutRemoving(showDialog = showDialog)
                    )
                }
            )
        }
    }
}