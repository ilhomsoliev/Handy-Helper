package com.ikcollab.handyhelper.app.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
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
import com.ikcollab.core.Constants.ADD_NOTE
import com.ikcollab.core.Constants.EDIT_NOTE
import com.ikcollab.core.Constants.FOLDER_ID_ARG
import com.ikcollab.core.Constants.FOLDER_ID_IS_NULL
import com.ikcollab.core.Constants.FOLDER_NAME
import com.ikcollab.core.Constants.GOAL_ID_ARG
import com.ikcollab.core.Constants.NOTE_ID_ARG
import com.ikcollab.core.Constants.WHICH_NOTE
import com.ikcollab.goals.goalsListScreen.GoalsListScreen
import com.ikcollab.goals.goalsScreen.GoalsScreen
import com.ikcollab.goals.components.BottomSheetInsertGoal
import com.ikcollab.goals.components.BottomSheetInsertStepGoal
import com.ikcollab.goals.goalStepsScreen.GoalStepsScreen
import com.ikcollab.notes.presentation.addNoteScreen.AddNoteScreen
import com.ikcollab.notes.presentation.addNoteScreen.AddNoteScreenEvent
import com.ikcollab.notes.presentation.addNoteScreen.AddNoteScreenViewModel
import com.ikcollab.notes.presentation.foldersNotesScreen.FoldersNoteScreen
import com.ikcollab.notes.presentation.notesScreen.NotesScreen
import com.ikcollab.notes.presentation.components.CustomFloatingActionButton
import com.ikcollab.notes.presentation.components.CustomInsertFolderItem
import com.ikcollab.notes.presentation.components.CustomSearchNotesTextField
import com.ikcollab.notes.presentation.foldersNotesScreen.FoldersNoteScreenViewModel
import com.ikcollab.notes.presentation.foldersNotesScreen.FoldersNotesScreenEvent
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
                    when (currentScreen) {
                        Screens.SearchNotesScreen.route -> {
                            CustomSearchNotesTextField(
                                value = state.searchState,
                                onValueChange = {
                                    if (it.length <= 20)
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
                                        Screens.AddNoteScreen.route -> if(WHICH_NOTE.value == EDIT_NOTE) "Edit note" else "Add note"
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
                        currentScreen != Screens.TodoCategoryScreen.route
                    ) {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }) {
                            Icon(
                                Icons.Filled.Menu,
                                null,
                                tint = MaterialTheme.colors.onBackground
                            )
                        }
                    } else {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            if (currentScreen == Screens.FoldersNoteScreen.route ||
                                currentScreen == Screens.ShowDetailsOfNoteScreen.route
                            )
                                Icon(
                                    Icons.Filled.Close,
                                    null,
                                    tint = MaterialTheme.colors.onBackground
                                )
                            else
                                Icon(
                                    Icons.Filled.ArrowBackIos,
                                    null,
                                    tint = MaterialTheme.colors.onBackground
                                )
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
                                Icon(
                                    Icons.Filled.ListAlt,
                                    null,
                                    tint = MaterialTheme.colors.onBackground
                                )
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
                                Icon(
                                    Icons.Filled.ListAlt,
                                    null,
                                    tint = MaterialTheme.colors.onBackground
                                )
                            }
                        }
                        Screens.SearchNotesScreen.route -> {}
                        Screens.TodoCategoryScreen.route -> {}
                        else -> {
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    navController.navigate(Screens.SearchNotesScreen.route)
                                }
                            }) {
                                Icon(
                                    Icons.Filled.Search,
                                    null,
                                    tint = MaterialTheme.colors.onBackground
                                )
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
                                        "{${FOLDER_ID_ARG}}/{${NOTE_ID_ARG}}",
                                        "-1/-1"
                                    )
                                )
                            }
                            WHICH_NOTE.value = ADD_NOTE
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
                                    "{${GOAL_ID_ARG}}",
                                    it.toString(),
                                )
                            )
                        }
                    })
                }
                composable(
                    route = Screens.GoalStepsScreen.route,
                    arguments = listOf(navArgument(GOAL_ID_ARG) {
                        type = NavType.IntType
                    })
                ) {
                    onEvent(
                        NavigationEvent.OnNewStepGoalIdChange(
                            it.arguments?.getInt(GOAL_ID_ARG) ?: -1
                        )
                    )
                    GoalStepsScreen(goalId = it.arguments?.getInt(GOAL_ID_ARG) ?: -1)
                }
                composable(route = Screens.GoalsListScreen.route) {
                    GoalsListScreen()
                }
                composable(route = Screens.NotesScreen.route) {
                    NotesScreen(
                        folderId = it.arguments?.getInt(FOLDER_ID_ARG) ?: -1,
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
                        },
                        editNote = { folderId:Int,noteId: Int ->
                            navController.navigate(
                                Screens.AddNoteScreen.route.replace(
                                    "{${FOLDER_ID_ARG}}/{${NOTE_ID_ARG}}",
                                    "$folderId/$noteId"
                                )
                            )
                        })
                }
                composable(
                    route = Screens.FoldersNoteScreen.route,
                    arguments = listOf(navArgument(FOLDER_ID_ARG) {
                        type = NavType.IntType
                    })
                ) {
                    val folderId = it.arguments?.getInt(FOLDER_ID_ARG) ?: -1
                    val viewModel = hiltViewModel<FoldersNoteScreenViewModel>()
                    val state = viewModel.state.collectAsState()
                    LaunchedEffect(key1 = true) {
                        viewModel.onEvent(FoldersNotesScreenEvent.OnFolderIdChange(folderId))
                        viewModel.onEvent(FoldersNotesScreenEvent.GetNotesByFolderId)
                    }
                    FoldersNoteScreen(
                        state = viewModel.state.collectAsState(),
                    ) { event ->
                        when (event) {
                            is FoldersNotesScreenEvent.NavigateToAddNote -> {
                                coroutineScope.launch {
                                    navController.navigate(
                                        Screens.AddNoteScreen.route.replace(
                                            "{${FOLDER_ID_ARG}}/{${NOTE_ID_ARG}}",
                                            "${state.value.folderId}/-1"
                                        )
                                    )
                                }
                                WHICH_NOTE.value = ADD_NOTE
                            }
                            is FoldersNotesScreenEvent.NavigateToEditNote -> {
                                coroutineScope.launch {
                                    navController.navigate(
                                        Screens.AddNoteScreen.route.replace(
                                            "{${FOLDER_ID_ARG}}/{${NOTE_ID_ARG}}",
                                            "${state.value.folderId}/${state.value.noteId}"
                                        )
                                    )
                                }
                            }
                            is FoldersNotesScreenEvent.NavigateToShowDetails -> {
                                coroutineScope.launch {
                                    navController.navigate(
                                        Screens.ShowDetailsOfNoteScreen.route.replace(
                                            "{${FOLDER_ID_ARG}}/{${NOTE_ID_ARG}}",
                                            "${state.value.folderId}/${state.value.noteId}"
                                        )
                                    )
                                }
                            }
                            else -> {
                                viewModel.onEvent(event)
                            }
                        }
                    }
                }
                composable(
                    route = Screens.AddNoteScreen.route,
                    arguments = listOf(
                        navArgument(FOLDER_ID_ARG) {
                            type = NavType.IntType
                        },
                        navArgument(NOTE_ID_ARG) {
                            type = NavType.IntType
                        }
                    )
                ) {
                    val folderId = it.arguments?.getInt(FOLDER_ID_ARG) ?: -1
                    val noteId = it.arguments?.getInt(NOTE_ID_ARG) ?: -1
                    val viewModel = hiltViewModel<AddNoteScreenViewModel>()
                    LaunchedEffect(key1 = true) {
                        viewModel.onEvent(AddNoteScreenEvent.OnLoadNote(noteId, folderId))
                        viewModel.onEvent(AddNoteScreenEvent.OnFolderChange(folderId))
                    }
                    AddNoteScreen(
                        state = viewModel.state.collectAsState().value,
                        onEvent = { event ->
                            when (event) {
                                is AddNoteScreenEvent.OnGoBack -> {
                                    navController.popBackStack()
                                }
                                else -> {
                                    viewModel.onEvent(event)
                                }
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
                        stateSearch = state.searchState,
                        editNote = { folderId: Int, noteId: Int ->
                            coroutineScope.launch {
                                navController.navigate(
                                    Screens.AddNoteScreen.route.replace(
                                        "{${FOLDER_ID_ARG}}/{${NOTE_ID_ARG}}",
                                        "$folderId/$noteId"
                                    )
                                )
                            }
                        }
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