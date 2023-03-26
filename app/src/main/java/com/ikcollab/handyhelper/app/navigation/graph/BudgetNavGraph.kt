package com.ikcollab.handyhelper.app.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ikcollab.handyhelper.app.navigation.Screens

fun NavGraphBuilder.BudgetNavGraph(navController: NavController) {
    navigation(route = Graph.BudgetGraph.route, startDestination = Screens.BudgetScreen.route) {
        composable(route = Screens.BudgetScreen.route) {

        }
    }
}