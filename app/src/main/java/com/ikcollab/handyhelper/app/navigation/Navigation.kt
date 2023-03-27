package com.ikcollab.handyhelper.app.navigation

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ikcollab.components.CustomFloatingActionButton
import com.ikcollab.core.Constants.ADD_NOTE
import com.ikcollab.core.Constants.EDIT_NOTE
import com.ikcollab.core.Constants.FOLDER_ID_ARG
import com.ikcollab.core.Constants.FOLDER_ID_IS_NULL
import com.ikcollab.core.Constants.FOLDER_NAME
import com.ikcollab.core.Constants.NOTE_ID_ARG
import com.ikcollab.core.Constants.WHICH_NOTE
import com.ikcollab.handyhelper.app.navigation.graph.*
import com.ikcollab.notes.presentation.components.CustomInsertFolderItem
import com.ikcollab.notes.presentation.components.CustomSearchNotesTextField
import com.ikcollab.todolist.components.bottomSheet.BottomSheetInsertTodoTask
import kotlinx.coroutines.launch
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.ikcollab.handyhelper.app.navigation.bottomSheet.BottomSheets

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalMaterialApi::class, ExperimentalMaterialNavigationApi::class,
)
@Composable
fun Navigation(
    state: NavigationState,
    onEvent: (NavigationEvent) -> Unit,
) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    val scaffoldState = rememberScaffoldState()
    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    val stateOfKeyboard = remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
    )

    if (!modalSheetState.isVisible) {
        stateOfKeyboard.value = false
    }

    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
        stateOfKeyboard.value = false
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
                            modifier = Modifier.padding(bottom = 150.dp),
                            stateOfKeyboard = stateOfKeyboard
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
        ModalBottomSheetLayout(bottomSheetNavigator) {

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
                                            Screens.GoalsScreen.route, BottomSheets.AddGoalSheet.route,Screens.GoalsListScreen.route -> "Goals"
                                            Screens.ChoresScreen.route -> "To-Do list"
                                            Screens.TrackerScreen.route -> "Habit Tracker"
                                            Screens.NotesScreen.route -> "Notes"
                                            Screens.BudgetScreen.route -> "Budget"
                                            Screens.FoldersNoteScreen.route -> FOLDER_NAME.value
                                            Screens.AddNoteScreen.route -> if (WHICH_NOTE.value == EDIT_NOTE) "Edit note" else "Add note"
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
                            currentScreen != BottomSheets.AddStepGoalSheet.route &&
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
                            Screens.GoalsScreen.route, BottomSheets.AddGoalSheet.route -> {
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
                            Screens.GoalStepsScreen.route, BottomSheets.AddStepGoalSheet.route -> {}
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
                        currentScreen != BottomSheets.AddStepGoalSheet.route &&
                        currentScreen != Screens.TodoCategoryScreen.route
                    ) {
                        com.ikcollab.handyhelper.app.navigation.bottomBar.BottomNavigation(
                            navController = navController
                        )
                    }
                },
                floatingActionButton = {
                    when (currentScreen) {
                        Screens.NotesScreen.route -> CustomFloatingActionButton(
                            onInsert = {
                                coroutineScope.launch {
                                    //modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                                stateOfKeyboard.value = true
                                onEvent(NavigationEvent.OnFolderIdChange(-1))
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
                    }
                }
            ) { it ->
                NavHost(
                    modifier = Modifier
                        .padding(it),
                    navController = navController,
                    route = Graph.RootGraph.route,
                    startDestination = Graph.GoalsGraph.route
                ) {
                    GoalsNavGraph(navController)
                    BudgetNavGraph(navController)
                    ChoresNavGraph(navController)
                    NotesNavGraph(navController, onEvent = { onEvent(it) })

                    TodoListNavGraph(navController)

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
}