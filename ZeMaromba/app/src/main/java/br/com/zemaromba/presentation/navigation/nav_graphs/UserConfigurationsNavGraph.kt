package br.com.zemaromba.presentation.navigation.nav_graphs

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import br.com.zemaromba.common.extensions.composableWithTransitionAnimation
import br.com.zemaromba.presentation.navigation.destinations.HomeDestinations
import br.com.zemaromba.presentation.navigation.destinations.UserConfigurationsDestinations
import br.com.zemaromba.presentation.user_configurations.screen.ConfigurationListScreen
import br.com.zemaromba.presentation.user_configurations.screen.UserManagementScreen
import br.com.zemaromba.presentation.user_configurations.viewmodel.UserManagementViewModel

fun NavGraphBuilder.userConfigurationsGraph(
    navController: NavController
) {
    navigation(
        startDestination = UserConfigurationsDestinations.MenuConfigOptionsListScreen.route,
        route = UserConfigurationsDestinations.UserConfigurationsGraph.route
    ) {
        composableWithTransitionAnimation(
            route = UserConfigurationsDestinations.MenuConfigOptionsListScreen.route
        ) {
            ConfigurationListScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToUserAccountConfigs = {
                    navController.navigate(HomeDestinations.UserManagementScreen.route)
                },
                onNavigateToThemeConfigs = {

                },
                onNavigateToContacts = {

                }
            )
        }

        composableWithTransitionAnimation(
            route = HomeDestinations.UserManagementScreen.route,
        ) {
            val viewModel: UserManagementViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value

            UserManagementScreen(
                state = state,
                onChangeName = {
                    viewModel.onEnterNewName(name = it)
                },
                onSaveName = {
                    viewModel.onSaveName()
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }

    }
}