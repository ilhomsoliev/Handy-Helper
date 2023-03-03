package com.ikcollab.handyhelper.app.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.Companion.toString
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ikcollab.core.Constants
import com.ikcollab.core.Constants.FOLDER_ID_ARG
import com.ikcollab.core.Constants.FOLDER_ID_IS_NULL
import com.ikcollab.core.Constants.FOLDER_NAME
import com.ikcollab.core.Constants.NOTE_ID_ARG
import com.ikcollab.goals.goalsListScreen.GoalsListScreen
import com.ikcollab.goals.goalsScreen.GoalsScreen
import com.ikcollab.goals.components.BottomSheetInsertGoal
import com.ikcollab.goals.components.BottomSheetInsertStepGoal
import com.ikcollab.goals.goalStepsScreen.GoalStepsScreen
import com.ikcollab.notes.presentation.addNoteScreen.AddNoteScreen
import com.ikcollab.notes.presentation.foldersNotesScreen.FoldersNoteScreen
import com.ikcollab.notes.presentation.notesScreen.NotesScreen
import com.ikcollab.notes.presentation.components.CustomFloatingActionButton
import com.ikcollab.notes.presentation.components.CustomInsertFolderItem
import com.ikcollab.notes.presentation.components.CustomSearchNotesTextField
import com.ikcollab.notes.presentation.searchNotesScreen.SearchNotesScreen
import com.ikcollab.notes.presentation.showDetailsOfNoteScreen.ShowDetailsOfNoteScreen
import com.ikcollab.todolist.components.bottomSheet.BottomSheetInsertTodoTask
import com.ikcollab.todolist.todoCategoryScreen.TodoCategoryEvent
import com.ikcollab.todolist.todoCategoryScreen.TodoCategoryScreen
import com.ikcollab.todolist.todoCategoryScreen.TodoCategoryScreenViewModel
import com.ikcollab.todolist.todoListScreen.TodoListEvent
import com.ikcollab.todolist.todoListScreen.TodoListScreen
import com.ikcollab.todolist.todoListScreen.TodoListScreenViewModel
import kotlinx.coroutines.launch
import kotlin.Unit.toString

@SuppressLint("CoroutineCreationDuringComposition", "NewApi")
@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun Navigation(
    state: NavigationState,
    onEvent: (NavigationEvent) -> Unit,
) {

    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
    )
    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""

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
                            value = state.folderName,
                            onValueChange = {
                                onEvent(NavigationEvent.OnFolderNameChange(it))
                            },
                            onClick = {
                                onEvent(NavigationEvent.InsertFolder)
                                coroutineScope.launch {
                                    modalSheetState.hide()
                                }
                            },
                            placeholder = "Name of Folder...",
                            modifier = Modifier.padding(bottom = 150.dp)
                        )
                    }
                    Screens.GoalsScreen.route -> {
                        BottomSheetInsertGoal(
                            goalValue = state.goalName,
                            onGoalValueChange = { onEvent(NavigationEvent.OnNewGoalNameChange(it)) },
                            start = state.goalStartDate,
                            deadline = state.goalEndDate,
                            onAddClick = {
                                onEvent(NavigationEvent.InsertGoalToDatabase)
                                coroutineScope.launch { modalSheetState.hide() }
                            }
                        )
                    }
                    Screens.GoalStepsScreen.route -> {
                        BottomSheetInsertStepGoal(
                            stepGoalValue = state.stepGoalName,
                            onStepGoalValueChange = {
                                onEvent(
                                    NavigationEvent.OnNewStepGoalNameChange(
                                        it
                                    )
                                )
                            },
                            deadline = state.stepGoalDeadline,
                            onDeadlineChange = {
                                onEvent(
                                    NavigationEvent.OnNewStepGoalDeadlineChange(
                                        it
                                    )
                                )
                            },
                            onAddClick = {
                                onEvent(NavigationEvent.InsertStepGoalToDatabase)
                                coroutineScope.launch { modalSheetState.hide() }
                            }
                        )
                    }
                    Screens.TodoCategoryScreen.route -> {
                        CustomInsertFolderItem(
                            value = state.todoCategoryName,
                            onValueChange = {
                                onEvent(NavigationEvent.OnTodoCategoryNameChange(it))
                            },
                            onClick = {
                                coroutineScope.launch {
                                    onEvent(NavigationEvent.InsertTodoCategory)
                                    coroutineScope.launch {
                                        modalSheetState.hide()
                                    }
                                }
                            },
                            placeholder = "Name of Category...",
                            modifier = Modifier.padding(bottom = 150.dp)
                        )
                    }
                    Screens.TodoListScreen.route -> {
                        BottomSheetInsertTodoTask(
                            taskValue = state.todoTaskName,
                            onTaskValueChange = { onEvent(NavigationEvent.OnTodoNameChange(it)) },
                            deadline = state.todoTaskDeadline,
                            onAddClick = {
                                onEvent(NavigationEvent.InsertTodoTaskToDatabase)
                                coroutineScope.launch { modalSheetState.hide() }
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
                    when(currentScreen) {
                        Screens.SearchNotesScreen.route -> {
                            CustomSearchNotesTextField(
                                value = state.searchState,
                                onValueChange = {
                                    if(it.length<=20)
                                    onEvent(NavigationEvent.OnSearchNotes(it))
                                },
                                placeholder = "Search notes"
                            )
                        }
                        else -> {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text =
                                    when (currentScreen) {
                                        Screens.GoalsScreen.route -> "Goals"
                                        Screens.GoalsListScreen.route -> "Goals"
                                        Screens.ChoresScreen.route -> "To-Do list"
                                        Screens.TrackerScreen.route -> "Habit Tracker"
                                        Screens.NotesScreen.route -> "Notes"
                                        Screens.BudgetScreen.route -> "Budget"
                                        Screens.FoldersNoteScreen.route -> FOLDER_NAME.value
                                        Screens.AddNoteScreen.route -> "Add note"
                                        else -> ""
                                    },
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                        }
                    }
                }, navigationIcon = {
                    if (currentScreen != Screens.GoalsListScreen.route &&
                        currentScreen != Screens.AddNoteScreen.route &&
                        currentScreen != Screens.FoldersNoteScreen.route &&
                        currentScreen != Screens.GoalStepsScreen.route &&
                        currentScreen != Screens.ShowDetailsOfNoteScreen.route &&
                        currentScreen != Screens.SearchNotesScreen.route &&
                        currentScreen !=Screens.TodoCategoryScreen.route
                    ) {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }) {
                            Icon(Icons.Filled.Menu, null, tint = MaterialTheme.colors.onBackground)
                        }
                    } else {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                navController.popBackStack()
                            }
                        }) {
                            if (currentScreen == Screens.FoldersNoteScreen.route ||
                                currentScreen == Screens.ShowDetailsOfNoteScreen.route
                            )
                                Icon(Icons.Filled.Close, null, tint = MaterialTheme.colors.onBackground)
                            else
                                Icon(Icons.Filled.ArrowBackIos, null, tint = MaterialTheme.colors.onBackground)
                        }
                    }
                }, actions = {
                    when (currentScreen) {
                        Screens.GoalsScreen.route -> {
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    navController.navigate(Screens.GoalsListScreen.route)
                                }
                            }) {
                                Icon(Icons.Filled.ListAlt, null, tint = MaterialTheme.colors.onBackground)
                            }
                        }
                        Screens.GoalsListScreen.route -> {}
                        Screens.AddNoteScreen.route -> {}
                        Screens.ShowDetailsOfNoteScreen.route -> {}
                        Screens.GoalStepsScreen.route -> {}
                        Screens.TodoListScreen.route -> {
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    navController.navigate(Screens.TodoCategoryScreen.route)
                                }
                            }) {
                                Icon(Icons.Filled.ListAlt, null, tint = MaterialTheme.colors.onBackground)
                            }
                        }
                        Screens.SearchNotesScreen.route->{}
                        Screens.TodoCategoryScreen.route->{}
                        else -> {
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    navController.navigate(Screens.SearchNotesScreen.route)
                                }
                            }) {
                                Icon(Icons.Filled.Search, null, tint = MaterialTheme.colors.onBackground)
                            }
                        }
                    }
                },
                backgroundColor = MaterialTheme.colors.background
                )
            },
            drawerGesturesEnabled = currentScreen != Screens.GoalsListScreen.route &&
                    currentScreen != Screens.FoldersNoteScreen.route &&
                    currentScreen != Screens.AddNoteScreen.route &&
                    currentScreen != Screens.GoalStepsScreen.route &&
                    currentScreen != Screens.ShowDetailsOfNoteScreen.route &&
                    currentScreen != Screens.TodoCategoryScreen.route &&
                    currentScreen != Screens.SearchNotesScreen.route,
            drawerContent = {
                DrawerContent()
            },
            bottomBar = {
                if (currentScreen != Screens.GoalsListScreen.route &&
                    currentScreen != Screens.FoldersNoteScreen.route &&
                    currentScreen != Screens.AddNoteScreen.route &&
                    currentScreen != Screens.GoalStepsScreen.route &&
                    currentScreen != Screens.ShowDetailsOfNoteScreen.route &&
                    currentScreen != Screens.SearchNotesScreen.route &&
                    currentScreen != Screens.TodoCategoryScreen.route
                ) {
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
                            coroutineScope.launch {
                                navController.navigate(
                                    Screens.AddNoteScreen.route.replace(
                                        "{${Constants.FOLDER_ID_ARG}}",
                                        (-1).toString()
                                    )
                                )
                            }
                            FOLDER_ID_IS_NULL.value = true
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
                    Screens.GoalStepsScreen.route -> CustomFloatingActionButton(onInsert = {
                        coroutineScope.launch {
                            modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                        }
                    })
                    /*Screens.FoldersNoteScreen.route -> CustomFloatingActionButton(onInsert = {
                        navController.navigate(Screens.AddNoteScreen.route)
                    })*/
                }
            }
        ) { it ->

            NavHost(
                modifier = Modifier
                    .padding(it),
                navController = navController,
                startDestination = Screens.GoalsScreen.route
            ) {
                composable(route = Screens.BudgetScreen.route) {

                }
                composable(route = Screens.ChoresScreen.route) {

                }
                composable(route = Screens.GoalsScreen.route) {
                    GoalsScreen(openGoalStepsScreen = {
                        coroutineScope.launch {
                            navController.navigate(
                                Screens.GoalStepsScreen.route.replace(
                                    "{${Constants.GOAL_ID_ARG}}",
                                    it.toString(),
                                )
                            )
                        }
                    })
                }
                composable(
                    route = Screens.GoalStepsScreen.route,
                    arguments = listOf(navArgument(Constants.GOAL_ID_ARG) {
                        type = NavType.IntType
                    })
                ) {
                    onEvent(
                        NavigationEvent.OnNewStepGoalIdChange(
                            it.arguments?.getInt(Constants.GOAL_ID_ARG) ?: -1
                        )
                    )
                    GoalStepsScreen(goalId = it.arguments?.getInt(Constants.GOAL_ID_ARG) ?: -1)
                }
                composable(route = Screens.GoalsListScreen.route) {
                    GoalsListScreen()
                }
                composable(route = Screens.NotesScreen.route) {
                    NotesScreen(
                        folderId = it.arguments?.getInt(Constants.FOLDER_ID_ARG) ?: -1,
                        openFolderDetails = {
                            coroutineScope.launch {
                                navController.navigate(
                                    Screens.FoldersNoteScreen.route.replace(
                                        "{${FOLDER_ID_ARG}}",
                                        it.toString(),
                                    )
                                )
                            }
                        },
                        showDetailsOnClick = { folderId: Int, noteId ->
                            coroutineScope.launch {
                                navController.navigate(
                                    Screens.ShowDetailsOfNoteScreen.route.replace(
                                        "{${FOLDER_ID_ARG}}/{${NOTE_ID_ARG}}",
                                        "-$folderId/$noteId"
                                    )
                                )
                            }
                        })
                }
                composable(
                    route = Screens.FoldersNoteScreen.route,
                    arguments = listOf(navArgument(Constants.FOLDER_ID_ARG) {
                        type = NavType.IntType
                    })
                ) {
                    FoldersNoteScreen(
                        folderId = it.arguments?.getInt(Constants.FOLDER_ID_ARG) ?: -1,
                        openAddNoteScreen = {
                            coroutineScope.launch {
                                navController.navigate(
                                    Screens.AddNoteScreen.route.replace(
                                        "{${Constants.FOLDER_ID_ARG}}",
                                        it.toString()
                                    )
                                )
                            }
                        },
                        showDetailsOnClick = { folderId: Int, noteId ->
                            coroutineScope.launch {
                                navController.navigate(
                                    Screens.ShowDetailsOfNoteScreen.route.replace(
                                        "{${FOLDER_ID_ARG}}/{${NOTE_ID_ARG}}",
                                        "$folderId/$noteId"
                                    )
                                )
                            }
                        }
                    )
                }
                composable(
                    route = Screens.AddNoteScreen.route,
                    arguments = listOf(navArgument(Constants.FOLDER_ID_ARG) {
                        type = NavType.IntType
                    })
                ) {
                    AddNoteScreen(
                        folderId = it.arguments?.getInt(Constants.FOLDER_ID_ARG) ?: -1,
                        onGoBack = {
                            coroutineScope.launch {
                                navController.popBackStack()
                            }
                        })
                }
                composable(
                    route = Screens.ShowDetailsOfNoteScreen.route,
                    arguments = listOf(
                        navArgument(FOLDER_ID_ARG) {
                            type = NavType.IntType
                        },
                        navArgument(NOTE_ID_ARG) {
                            type = NavType.IntType
                        }
                    )
                ) {
                    ShowDetailsOfNoteScreen()
                }
                composable(
                    route = Screens.SearchNotesScreen.route
                ) {
                    SearchNotesScreen(
                        showDetailsOnClick = { folderId: Int, noteId ->
                            coroutineScope.launch {
                                navController.navigate(
                                    Screens.ShowDetailsOfNoteScreen.route.replace(
                                        "{${FOLDER_ID_ARG}}/{${NOTE_ID_ARG}}",
                                        "$folderId/$noteId"
                                    )
                                )
                            }
                        },
                        stateSearch = state.searchState
                    )
                }
                composable(route = Screens.PickThemeScreen.route) {

                }
                composable(route = Screens.SettingsScreen.route) {

                }
                composable(route = Screens.TrackerScreen.route) {

                }
                composable(route = Screens.TodoListScreen.route) {
                    val viewModel = hiltViewModel<TodoListScreenViewModel>()
                    TodoListScreen(viewModel.state.collectAsState().value, onEvent = { event ->
                        when (event) {
                            is TodoListEvent.OpenBottomSheet -> {
                                coroutineScope.launch {
                                    modalSheetState.show()
                                }
                            }
                            is TodoListEvent.OnTaskCategoryIdChange -> {
                                onEvent(NavigationEvent.OnTodoCategoryIdChange(event.value))
                            }
                            else -> viewModel.onEvent(event)
                        }
                    })
                }
                composable(route = Screens.TodoCategoryScreen.route) {
                    val viewModel = hiltViewModel<TodoCategoryScreenViewModel>()
                    TodoCategoryScreen(
                        viewModel.state.collectAsState().value,
                        onEvent = { event ->
                            when (event) {
                                is TodoCategoryEvent.OpenBottomSheet -> {
                                    coroutineScope.launch {
                                        modalSheetState.show()
                                    }
                                }

                                else -> viewModel.onEvent(event)
                            }
                        }
                    )
                }
            }
        }
    }
}