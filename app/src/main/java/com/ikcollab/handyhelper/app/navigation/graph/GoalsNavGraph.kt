package com.ikcollab.handyhelper.app.navigation.graph

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.ikcollab.core.Constants
import com.ikcollab.goals.bottomSheetInsertGoal.BottomSheetInsertGoal
import com.ikcollab.goals.bottomSheetInsertGoal.BottomSheetInsertGoalEvent
import com.ikcollab.goals.bottomSheetInsertGoal.BottomSheetInsertGoalOneTimeEvent
import com.ikcollab.goals.bottomSheetInsertGoal.BottomSheetInsertGoalViewModel
import com.ikcollab.goals.bottomSheetInsertStepGoal.BottomSheetInsertStepGoal
import com.ikcollab.goals.bottomSheetInsertStepGoal.BottomSheetInsertStepGoalEvent
import com.ikcollab.goals.bottomSheetInsertStepGoal.BottomSheetInsertStepGoalOneTimeEvent
import com.ikcollab.goals.bottomSheetInsertStepGoal.BottomSheetInsertStepGoalViewModel
import com.ikcollab.goals.goalSteps.GoalStepsEvent
import com.ikcollab.goals.goalSteps.GoalStepsScreen
import com.ikcollab.goals.goalSteps.GoalStepsViewModel
import com.ikcollab.goals.goalsList.GoalsListScreen
import com.ikcollab.goals.goals.GoalsEvent
import com.ikcollab.goals.goals.GoalsScreen
import com.ikcollab.goals.goals.GoalsViewModel
import com.ikcollab.handyhelper.app.ads.showInterstitial
import com.ikcollab.handyhelper.app.navigation.Screens
import com.ikcollab.handyhelper.app.navigation.bottomSheet.BottomSheets


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.GoalsNavGraph(navController: NavHostController, context: Context) {

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
                    is GoalsEvent.OnEditGoalClick -> {
                        navController.navigate(
                            BottomSheets.AddGoalSheet.route.replace(
                                "{${Constants.GOAL_ID_ARG}}",
                                event.id.toString(),
                            )
                        )
                    }
                    is GoalsEvent.OpenBottomSheet -> {
                        navController.navigate(
                            BottomSheets.AddGoalSheet.route.replace(
                                "{${Constants.GOAL_ID_ARG}}",
                                "-1",
                            )
                        )
                    }
                    else -> viewModel.onEvent(event)
                }
            })
        }
        bottomSheet(
            BottomSheets.AddGoalSheet.route,
            arguments = listOf(navArgument(Constants.GOAL_ID_ARG) {
                type = NavType.IntType
            })
        ) { backstackEntry ->
            val viewModel = hiltViewModel<BottomSheetInsertGoalViewModel>()
            val state = viewModel.state.collectAsState().value
            LaunchedEffect(key1 = false, block = {
                viewModel.flow.collect() { event ->
                    when (event) {
                        is BottomSheetInsertGoalOneTimeEvent.CloseBottomSheet -> {
                            navController.popBackStack()
                        }
                    }
                }
            })
            LaunchedEffect(key1 = false, block = {
                val goalId = backstackEntry.arguments?.getInt(Constants.GOAL_ID_ARG) ?: -1
                viewModel.onEvent(BottomSheetInsertGoalEvent.OnLoad(goalId))
            })
            BottomSheetInsertGoal(
                state = state,
                onEvent = { event ->
                    when (event) {
                        else -> viewModel.onEvent(event)
                    }
                }

            )
        }
        composable(
            route = Screens.GoalStepsScreen.route,
            arguments = listOf(navArgument(Constants.GOAL_ID_ARG) {
                type = NavType.IntType
            })
        ) {
            showInterstitial(context) {}

            val viewModel = hiltViewModel<GoalStepsViewModel>()
            val state = viewModel.state.collectAsState().value
            LaunchedEffect(key1 = false, block = {
                val goalId = it.arguments?.getInt(Constants.GOAL_ID_ARG) ?: -1
                viewModel.onEvent(GoalStepsEvent.OnInit(goalId))
            })
            GoalStepsScreen(state = state, onEvent = { event ->
                when (event) {
                    is GoalStepsEvent.OpenBottomSheet -> {
                        navController.navigate(
                            BottomSheets.AddStepGoalSheet.route.replace(
                                "{${Constants.GOAL_ID_ARG}}",
                                state.goal!!.id.toString(),
                            )
                        )
                    }
                    else -> {
                        viewModel.onEvent(event)
                    }
                }
            })
        }
        bottomSheet(
            route = BottomSheets.AddStepGoalSheet.route,
            arguments = listOf(navArgument(Constants.GOAL_ID_ARG) {
                type = NavType.IntType
            })
        ) {

            val viewModel = hiltViewModel<BottomSheetInsertStepGoalViewModel>()
            val state = viewModel.state.collectAsState().value
            LaunchedEffect(key1 = false, block = {
                val goalId = it.arguments?.getInt(Constants.GOAL_ID_ARG) ?: -1
                viewModel.onEvent(BottomSheetInsertStepGoalEvent.OnInit(goalId))

                viewModel.flow.collect() { event ->
                    when (event) {
                        is BottomSheetInsertStepGoalOneTimeEvent.CloseBottomSheet -> {
                            navController.popBackStack()
                        }
                    }
                }
            })
            BottomSheetInsertStepGoal(
                state = state,
                onEvent = { event ->
                    when (event) {
                        else -> viewModel.onEvent(event)
                    }
                }

            )
        }
        composable(route = Screens.GoalsListScreen.route) {
            GoalsListScreen()
        }
    }

}