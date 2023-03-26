package com.ikcollab.handyhelper.app.navigation.graph

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.ikcollab.core.Constants
import com.ikcollab.goals.goalStepsScreen.GoalStepsScreen
import com.ikcollab.goals.goalsListScreen.GoalsListScreen
import com.ikcollab.goals.goalsScreen.GoalsScreen
import com.ikcollab.handyhelper.app.navigation.Screens


fun NavGraphBuilder.GoalsNavGraph(navController: NavController) {
    navigation(
        route = Graph.GoalsGraph.route,
        startDestination = Screens.GoalsScreen.route
    ) {
        composable(route = Screens.GoalsScreen.route) {
            GoalsScreen(openGoalStepsScreen = {
                navController.navigate(
                    Screens.GoalStepsScreen.route.replace(
                        "{${Constants.GOAL_ID_ARG}}",
                        it.toString(),
                    )
                )
            })
        }
        composable(
            route = Screens.GoalStepsScreen.route,
            arguments = listOf(navArgument(Constants.GOAL_ID_ARG) {
                type = NavType.IntType
            })
        ) {
            /*onEvent(
                NavigationEvent.OnNewStepGoalIdChange(
                    it.arguments?.getInt(Constants.GOAL_ID_ARG) ?: -1
                )
            )*/
            GoalStepsScreen(goalId = it.arguments?.getInt(Constants.GOAL_ID_ARG) ?: -1)
        }
        composable(route = Screens.GoalsListScreen.route) {
            GoalsListScreen()
        }
    }

}