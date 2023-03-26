package com.ikcollab.handyhelper.app.navigation.graph

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.ikcollab.core.Constants
import com.ikcollab.goals.goalStepsScreen.GoalStepsEvent
import com.ikcollab.goals.goalStepsScreen.GoalStepsScreen
import com.ikcollab.goals.goalStepsScreen.GoalStepsViewModel
import com.ikcollab.goals.goalsListScreen.GoalsListScreen
import com.ikcollab.goals.goalsScreen.GoalsEvent
import com.ikcollab.goals.goalsScreen.GoalsScreen
import com.ikcollab.goals.goalsScreen.GoalsViewModel
import com.ikcollab.handyhelper.app.navigation.Screens


fun NavGraphBuilder.GoalsNavGraph(navController: NavHostController) {

    navigation(
        route = Graph.GoalsGraph.route,
        startDestination = Screens.GoalsScreen.route
    ) {
        composable(route = Screens.GoalsScreen.route) {
            val viewModel = hiltViewModel<GoalsViewModel>()
            val state = viewModel.state.collectAsState().value
            GoalsScreen(state = state, onEvent = { event ->
                when (event) {
                    is GoalsEvent.OpenGoalStepsScreen -> {
                        navController.navigate(
                            Screens.GoalStepsScreen.route.replace(
                                "{${Constants.GOAL_ID_ARG}}",
                                event.id.toString(),
                            )
                        )
                    }
                    else -> viewModel.onEvent(event)
                }
            })
        }
        composable(
            route = Screens.GoalStepsScreen.route,
            arguments = listOf(navArgument(Constants.GOAL_ID_ARG) {
                type = NavType.IntType
            })
        ) {
            val viewModel = hiltViewModel<GoalStepsViewModel>()
            val state = viewModel.state.collectAsState().value
            LaunchedEffect(key1 = false, block = {
                val goalId = it.arguments?.getInt(Constants.GOAL_ID_ARG) ?: -1
                viewModel.onEvent(GoalStepsEvent.OnInit(goalId))
            })
            GoalStepsScreen(state = state, onEvent = { event ->
                when (event) {
                    else -> {
                        viewModel.onEvent(event)
                    }
                }
            })
        }
        composable(route = Screens.GoalsListScreen.route) {
            GoalsListScreen()
        }
    }

}