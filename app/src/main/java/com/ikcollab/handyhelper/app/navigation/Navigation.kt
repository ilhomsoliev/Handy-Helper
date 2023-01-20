package com.ikcollab.handyhelper.app.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ikcollab.goals.GoalsScreen
import com.ikcollab.goals.components.BottomSheetInsertGoal
import com.ikcollab.notes.presentation.NotesScreen
import com.ikcollab.notes.presentation.NotesScreenViewModel
import com.ikcollab.notes.presentation.components.CustomFloatingActionButton
import com.ikcollab.notes.presentation.components.CustomInsertFolderItem
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun Navigation(viewModel: NavigationViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        //skipHalfExpanded = true,
        /*bottomSheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Collapsed
        )*/
    )
    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    val stateFolderName = viewModel.stateFolderName
    val focus = remember { mutableStateOf(true) }

    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
        sheetState = modalSheetState,
        sheetContent = {
            Box(Modifier.defaultMinSize(minHeight = 1.dp)) {
                when (currentScreen) {
                    Screens.NotesScreen.route -> {
                        CustomInsertFolderItem(
                            value = stateFolderName.value,
                            onValueChange = {
                                viewModel.setFolderName(it)
                            },
                            onClick = {
                                if (stateFolderName.value != "") {
                                    viewModel.insertFolder(
                                        stateFolderName.value
                                    )
                                }
                            },
                            placeholder = "Name of Folder...",
                            focus = focus,
                            modifier = Modifier.padding(bottom = 150.dp)
                        )
                    }
                    Screens.GoalsScreen.route -> {
                        BottomSheetInsertGoal(
                            goalValue = viewModel.newGoalName.value,
                            onGoalValueChange = viewModel::changeNewGoalName,
                            start = viewModel.newGoalStartDate.value,
                            deadline = viewModel.newGoalEndDate.value,
                            onAddClick = viewModel::addGoalToDatabase
                        )
                    }
                }
            }
        },
        sheetElevation = 12.dp,
        //sheetPeekHeight = sheetPeekHeight.dp
    ) {
        Scaffold(scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, text =
                        when (currentScreen) {
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
                }, actions = {
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
                com.ikcollab.handyhelper.app.navigation.bottomBar.BottomNavigation(navController = navController)
            },
            floatingActionButton = {
                when (currentScreen) {
                    Screens.NotesScreen.route -> CustomFloatingActionButton(
                        onInsert = {
                            coroutineScope.launch {
                                modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        },
                        onEdit = {

                        },
                        isMultiple = true
                    )
                    Screens.GoalsScreen.route -> CustomFloatingActionButton(
                        onInsert = {
                            coroutineScope.launch {
                                modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        },
                    )
                }
            }
        ) {

            NavHost(
                modifier = Modifier
                    .padding(it)
                    .background(Color(0xFF34568B)),
                navController = navController,
                startDestination = Screens.GoalsScreen.route
            ) {
                composable(route = Screens.BudgetScreen.route) {

                }
                composable(route = Screens.ChoresScreen.route) {

                }
                composable(route = Screens.GoalsScreen.route) {
                    GoalsScreen()
                }
                composable(route = Screens.NotesScreen.route) {
                    NotesScreen(openFolderDetails = {
                        //navController.navigate()
                    })
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
}