package com.ikcollab.handyhelper.app.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ikcollab.handyhelper.app.navigation.graph.*
import com.ikcollab.notes.presentation.components.CustomSearchNotesTextField
import kotlinx.coroutines.launch
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.ikcollab.components.CustomDialog
import com.ikcollab.components.CustomIconButton
import com.ikcollab.handyhelper.app.navigation.bottomSheet.BottomSheets
import com.ikcollab.handyhelper.app.presentation.languages.LanguagesEvent
import com.ikcollab.handyhelper.app.presentation.languages.LanguagesScreen
import com.ikcollab.handyhelper.app.presentation.languages.LanguagesViewModel
import com.ikcollab.handyhelper.app.presentation.settings.SettingsScreen
import com.ikcollab.components.theme.AntiFlashWhite
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalMaterialNavigationApi::class,
)
@Composable
fun Navigation(
    state: NavigationState,
    onEvent: (NavigationEvent) -> Unit,
) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    val coroutineScope = rememberCoroutineScope()
    var isDropDownMenuActive by remember { mutableStateOf(false) }


    CustomDialog(
        text = "Attention",
        description = "Are you sure you want to delete all records of Expense type?",
        okBtnClick = {
            onEvent(NavigationEvent.StartOverExpenses)
            onEvent(NavigationEvent.OnDeleteExpenseDialogChange(false))
        },
        cancelBtnClick = {
            onEvent(NavigationEvent.OnDeleteExpenseDialogChange(false))
        },
        isDialogOpen = state.isDeleteBudgetExpenseTypeDialogActive,
        okBtnText = "Delete",
        cancelBtnText = "Cancel"
    ) {
        onEvent(NavigationEvent.OnDeleteExpenseDialogChange(false))
    }
    CustomDialog(
        text = "Attention",
        description = "Are you sure you want to delete all records of Income type?",
        okBtnClick = {
            onEvent(NavigationEvent.StartOverIncome)
            onEvent(NavigationEvent.OnDeleteIncomeDialogChange(false))
        },
        cancelBtnClick = {
            onEvent(NavigationEvent.OnDeleteIncomeDialogChange(false))
        },
        isDialogOpen = state.isDeleteBudgetIncomeTypeDialogActive,
        okBtnText = "Delete",
        cancelBtnText = "Cancel"
    ) {
        onEvent(NavigationEvent.OnDeleteIncomeDialogChange(false))
    }

    ModalBottomSheetLayout(bottomSheetNavigator) {
        Scaffold(
            contentColor = AntiFlashWhite, scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    elevation = 0.dp, backgroundColor = MaterialTheme.colors.background,
                    title = {
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
                                        text = getTopBarTitle(currentScreen),
                                        color = MaterialTheme.colors.onBackground
                                    )
                                }
                            }
                        }
                    },
                    navigationIcon = {
                        if (doesScreenHaveMenu(currentScreen)) {
                            CustomIconButton(icon = Icons.Filled.Menu) {
                                coroutineScope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            }
                        } else {
                            CustomIconButton(icon = Icons.Filled.ArrowBackIos) {
                                navController.popBackStack()
                            }
                        }
                    },
                    actions = {
                        when (currentScreen) {
                            Screens.GoalsScreen.route, BottomSheets.AddGoalSheet.route -> {
                                CustomIconButton(icon = Icons.Filled.ListAlt) {
                                    coroutineScope.launch {
                                        navController.navigate(Screens.GoalsListScreen.route)
                                    }
                                }
                            }
                            Screens.GoalsListScreen.route -> {}
                            Screens.AddNoteScreen.route -> {}
                            Screens.ShowDetailsOfNoteScreen.route -> {}
                            Screens.GoalStepsScreen.route, BottomSheets.AddStepGoalSheet.route -> {}
                            Screens.TodoListScreen.route -> {
                                CustomIconButton(icon = Icons.Filled.ListAlt) {
                                    coroutineScope.launch {
                                        navController.navigate(Screens.TodoCategoryScreen.route)
                                    }
                                }
                            }
                            Screens.SearchNotesScreen.route -> {}
                            Screens.TodoCategoryScreen.route -> {}
                            Screens.LanguagesScreen.route -> {}
                            Screens.SettingsScreen.route -> {}
                            Screens.BudgetCategoryScreen.route -> {}
                            BottomSheets.AddBudgetCategorySheet.route -> {}
                            Screens.BudgetScreen.route, BottomSheets.AddBudgetStorySheet.route -> {
                                CustomIconButton(icon = Icons.Filled.MoreVert) {
                                    isDropDownMenuActive = true
                                }
                                // TODO Transform this function into BudgetSreen
                                DropdownMenu(
                                    modifier = Modifier.background(MaterialTheme.colors.primary),
                                    offset = DpOffset(0.dp, 0.dp),
                                    expanded = isDropDownMenuActive,
                                    onDismissRequest = { isDropDownMenuActive = false }
                                ) {
                                    DropdownMenuItem(
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = {
                                            navController.navigate(Screens.BudgetCategoryScreen.route)
                                            isDropDownMenuActive = false
                                        }) {
                                        Text(
                                            text = "Category Management",
                                            color = MaterialTheme.colors.onPrimary
                                        )
                                    }
                                    DropdownMenuItem(
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = {
                                            onEvent(
                                                NavigationEvent.OnDeleteExpenseDialogChange(
                                                    true
                                                )
                                            )
                                            isDropDownMenuActive = false
                                        }) {
                                        Text(
                                            text = "Start Over (expenses)",
                                            color = MaterialTheme.colors.onPrimary
                                        )
                                    }
                                    DropdownMenuItem(
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = {
                                            onEvent(NavigationEvent.OnDeleteIncomeDialogChange(true))
                                            isDropDownMenuActive = false
                                        }) {
                                        Text(
                                            text = "Start over (income)",
                                            color = MaterialTheme.colors.onPrimary
                                        )
                                    }
                                    DropdownMenuItem(
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = {
                                            // Change currency Dialog
                                            isDropDownMenuActive = false
                                        }) {
                                        Text(
                                            text = "Change currency",
                                            color = MaterialTheme.colors.onPrimary
                                        )
                                    }
                                }
                            }
                            else -> {
                                CustomIconButton(icon = Icons.Filled.Search) {
                                    coroutineScope.launch {
                                        navController.navigate(Screens.SearchNotesScreen.route)
                                    }
                                }
                            }
                        }
                    },
                )
            },
            drawerGesturesEnabled = isDrawerGesturesEnabled(currentScreen),
            drawerContent = {
                DrawerContent(
                    openLanguagesScreen = {
                        navController.navigate(Screens.LanguagesScreen.route)
                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
                    openSettingsScreen = {
                        navController.navigate(Screens.SettingsScreen.route)
                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
                    openDonationsLink = {
                        uriHandler.openUri("https://www.buymeacoffee.com/ilhomsoliev")
                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
                    openGithubPage = {
                        uriHandler.openUri("https://github.com/ilhomsoliev/Handy-Helper")
                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
                    shareApp = {

                    },
                    sendEmail = {

                    },
                )
            },
            bottomBar = {
                if (doesScreenHaveBottomBar(currentScreen)) {
                    com.ikcollab.handyhelper.app.navigation.bottomBar.BottomNavigation(
                        navController = navController
                    )
                }
            },
        ) { it ->
            NavHost(
                modifier = Modifier
                    .padding(it),
                navController = navController,
                route = Graph.RootGraph.route,
                startDestination = Graph.BudgetGraph.route
            ) {
                GoalsNavGraph(navController)

                BudgetNavGraph(navController)

                ChoresNavGraph(navController)

                NotesNavGraph(navController, state)

                TodoListNavGraph(navController)

                composable(route = Screens.PickThemeScreen.route) {

                }
                composable(route = Screens.SettingsScreen.route) {

                }
                composable(route = Screens.TrackerScreen.route) {

                }
                composable(route = Screens.LanguagesScreen.route) {
                    val viewModel = hiltViewModel<LanguagesViewModel>()
                    LanguagesScreen(
                        state = viewModel.state.collectAsState().value,
                        onEvent = { event ->
                            when (event) {
                                is LanguagesEvent.OnBackClick -> {
                                    navController.popBackStack()
                                }
                                is LanguagesEvent.OnLanguageItemClick -> {
                                    val locale = Locale(event.shortcut)
                                    Locale.setDefault(locale)
                                    val resources = context.resources
                                    val configuration = resources.configuration
                                    configuration.locale = locale
                                    resources.updateConfiguration(
                                        configuration,
                                        resources.displayMetrics
                                    )
                                    viewModel.onEvent(event)
                                }
                                else -> {
                                    viewModel.onEvent(event)
                                }
                            }
                        })
                }
                composable(route = Screens.SettingsScreen.route) {
                    SettingsScreen()
                }

            }
        }
    }
}
