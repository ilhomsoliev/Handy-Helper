package com.ikcollab.handyhelper.app.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ikcollab.core.Constants
import com.ikcollab.goals.GoalsListScreen
import com.ikcollab.goals.goalsScreen.GoalsScreen
import com.ikcollab.goals.components.BottomSheetInsertGoal
import com.ikcollab.notes.presentation.addNoteScreen.AddNoteScreen
import com.ikcollab.notes.presentation.foldersNotesScreen.FoldersNoteScreen
import com.ikcollab.notes.presentation.notesScreen.NotesScreen
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
    )
    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    val stateFolderName = viewModel.stateFolderName

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
                            modifier = Modifier.padding(bottom = 150.dp)
                        )
                    }
                    Screens.GoalsScreen.route -> {
                        BottomSheetInsertGoal(
                            goalValue = viewModel.newGoalName.value,
                            onGoalValueChange = viewModel::changeNewGoalName,
                            start = viewModel.newGoalStartDate.value,
                            deadline = viewModel.newGoalEndDate.value,
                            onAddClick = {
                                viewModel.addGoalToDatabase(onDone = {
                                    coroutineScope.launch { modalSheetState.hide() }
                                })
                            }
                        )
                    }
                }
            }
        },
        sheetElevation = 12.dp,
    ) {
        Scaffold(scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text =
                            when (currentScreen) {
                                Screens.GoalsScreen.route -> "Goals"
                                Screens.GoalsListScreen.route -> "Goals"
                                Screens.ChoresScreen.route -> "To-Do list"
                                Screens.TrackerScreen.route -> "Habit Tracker"
                                Screens.NotesScreen.route -> "Notes"
                                Screens.BudgetScreen.route -> "Budget"
                                Screens.FoldersNoteScreen.route -> "Folder"
                                Screens.AddNoteScreen.route -> "Add note"
                                else -> ""
                            }
                        )
                    }
                }, navigationIcon = {
                    if (currentScreen != Screens.GoalsListScreen.route && currentScreen != Screens.AddNoteScreen.route && currentScreen != Screens.FoldersNoteScreen.route) {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }) {
                            Icon(Icons.Filled.Menu, null)
                        }
                    } else {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            if (currentScreen == Screens.FoldersNoteScreen.route)
                                Icon(Icons.Filled.Close, null)
                            else
                                Icon(Icons.Filled.ArrowBack, null)
                        }
                    }
                }, actions = {
                    when (currentScreen) {
                        Screens.GoalsScreen.route -> {
                            IconButton(onClick = {
                                navController.navigate(Screens.GoalsListScreen.route)
                            }) {
                                Icon(Icons.Filled.ListAlt, null)
                            }
                        }
                        Screens.GoalsListScreen.route -> {}
                        Screens.FoldersNoteScreen.route -> {}
                        Screens.AddNoteScreen.route -> {}
                        else -> {
                            IconButton(onClick = {

                            }) {
                                Icon(Icons.Filled.Search, null)
                            }
                        }
                    }
                })
            },
            drawerGesturesEnabled = currentScreen != Screens.GoalsListScreen.route &&
                    currentScreen != Screens.FoldersNoteScreen.route &&
                    currentScreen != Screens.AddNoteScreen.route,
            drawerContent = {
                DrawerContent()
            },
            bottomBar = {
                if (currentScreen != Screens.GoalsListScreen.route && currentScreen != Screens.FoldersNoteScreen.route && currentScreen != Screens.AddNoteScreen.route) {
                    com.ikcollab.handyhelper.app.navigation.bottomBar.BottomNavigation(navController = navController)
                }
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
                    /*Screens.FoldersNoteScreen.route -> CustomFloatingActionButton(onInsert = {
                        navController.navigate(Screens.AddNoteScreen.route)
                    })*/
                }
            }
        ) {

            NavHost(
                modifier = Modifier
                    .padding(it),
//                    .background(Color(0xFF34568B)),
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
                composable(route = Screens.GoalsListScreen.route) {
                    GoalsListScreen()
                }
                composable(route = Screens.NotesScreen.route) {
                    NotesScreen(openFolderDetails = {
                        navController.navigate(
                            Screens.FoldersNoteScreen.route.replace(
                                "{${Constants.FOLDER_ID_ARG}}",
                                it.toString()
                            )
                        )
                    })
                }
                composable(route = Screens.FoldersNoteScreen.route,
                    arguments = listOf(navArgument(Constants.FOLDER_ID_ARG) {
                        type = NavType.IntType
                    })
                ) {
                    FoldersNoteScreen(
                        folderId = it.arguments?.getInt(Constants.FOLDER_ID_ARG) ?: -1,
                        openAddNoteScreen = {
                            navController.navigate(
                                Screens.AddNoteScreen.route.replace(
                                    "{${Constants.FOLDER_ID_ARG}}",
                                    it.toString()
                                )
                            )
                        })
                }
                composable(route = Screens.AddNoteScreen.route,
                    arguments = listOf(navArgument(Constants.FOLDER_ID_ARG) {
                        type = NavType.IntType
                    })
                ) {
                    AddNoteScreen(
                        folderId = it.arguments?.getInt(Constants.FOLDER_ID_ARG) ?: -1,
                        onGoBack = {
                            navController.popBackStack()
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