package com.ikcollab.handyhelper.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route?:""

    Scaffold(scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = {
                Text(modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,text =
                    when(currentScreen) {
                        Screens.GoalsScreen.route -> "Goals"
                        Screens.ChoresScreen.route -> "To-Do list"
                        Screens.TrackerScreen.route -> "Habit Tracker"
                        Screens.NotesScreen.route -> "Notes"
                        Screens.BudgetScreen.route -> "Budget"
                        else -> ""
                    }
                )
            }, navigationIcon = {
                IconButton(onClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) {
                    Icon(Icons.Filled.Menu, null)
                }
            },actions = {
                IconButton(onClick = {

                }) {
                    Icon(Icons.Filled.Search, null)
                }
                /*when (currentScreen) {

                    Screens.HomeScreen.route -> {
                        IconButton(onClick = {

                        }) {
                            Icon(Icons.Filled.Search, null)
                        }
                    }
                    Screens.TrashScreen.route -> {
                        IconButton(onClick = {

                        }) {
                            Icon(Icons.Filled.Search, null)
                        }
                    }
                    Screens.LabelScreen.route -> {
                        IconButton(onClick = {
                            isCreateLabelDialogActive = true
                        }) {
                            Icon(Icons.Filled.Add, null)
                        }
                    }
                    else -> {}
                }*/
            })
        }, drawerGesturesEnabled = true, drawerContent = {
            DrawerContent()
        },
        bottomBar = {
            com.ikcollab.handyhelper.navigation.bottomBar.BottomNavigation(navController = navController)
        }
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = Screens.ChoresScreen.route
        ) {
            composable(route = Screens.BudgetScreen.route) {

            }
            composable(route = Screens.ChoresScreen.route) {

            }
            composable(route = Screens.GoalsScreen.route) {

            }
            composable(route = Screens.NotesScreen.route) {

            }
            composable(route = Screens.PickThemeScreen.route) {

            }
            composable(route = Screens.SettingsScreen.route) {

            }
            composable(route = Screens.TrackerScreen.route) {

            }
        }
    }
}