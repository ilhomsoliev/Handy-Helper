package com.ikcollab.handyhelper.app.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ikcollab.handyhelper.app.navigation.Screens

fun NavGraphBuilder.ChoresNavGraph(navController: NavController) {
    navigation(route = Graph.ChoresGraph.route, startDestination = Screens.ChoresScreen.route) {
        composable(route = Screens.ChoresScreen.route) {

        }
    }
}