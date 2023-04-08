package com.ikcollab.handyhelper.app.navigation.graph

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.ikcollab.core.Constants
import com.ikcollab.handyhelper.app.navigation.NavigationState
import com.ikcollab.handyhelper.app.navigation.Screens
import com.ikcollab.handyhelper.app.navigation.bottomSheet.BottomSheets
import com.ikcollab.notes.presentation.addNoteScreen.AddNoteScreen
import com.ikcollab.notes.presentation.addNoteScreen.AddNoteScreenEvent
import com.ikcollab.notes.presentation.addNoteScreen.AddNoteScreenViewModel
import com.ikcollab.notes.presentation.bottomSheetInsertFolder.BottomSheetInsertFolder
import com.ikcollab.notes.presentation.bottomSheetInsertFolder.BottomSheetInsertFolderEvent
import com.ikcollab.notes.presentation.bottomSheetInsertFolder.BottomSheetInsertFolderOneTimeEvent
import com.ikcollab.notes.presentation.bottomSheetInsertFolder.BottomSheetInsertFolderViewModel
import com.ikcollab.notes.presentation.folderNotesScreen.FolderNotesEvent
import com.ikcollab.notes.presentation.folderNotesScreen.FolderNotesViewModel
import com.ikcollab.notes.presentation.folderNotesScreen.FoldersNoteScreen
import com.ikcollab.notes.presentation.notesScreen.NotesEvent
import com.ikcollab.notes.presentation.notesScreen.NotesScreen
import com.ikcollab.notes.presentation.notesScreen.NotesScreenViewModel
import com.ikcollab.notes.presentation.searchNotesScreen.SearchNotesEvent
import com.ikcollab.notes.presentation.searchNotesScreen.SearchNotesScreen
import com.ikcollab.notes.presentation.searchNotesScreen.SearchNotesScreenViewModel
import com.ikcollab.notes.presentation.showDetailsOfNoteScreen.ShowDetailsOfNoteScreen
import kotlinx.coroutines.delay

val stateSearch = mutableStateOf("")

@SuppressLint("NewApi", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.NotesNavGraph(
    navController: NavController,
    state:NavigationState
) {
    stateSearch.value = state.searchState
    navigation(route = Graph.NotesGraph.route, startDestination = Screens.NotesScreen.route) {
        composable(route = Screens.NotesScreen.route) {
            val viewModel = hiltViewModel<NotesScreenViewModel>()
            val state = viewModel.state.collectAsState()
            LaunchedEffect(key1 = true) {
                viewModel.onEvent(NotesEvent.GetFolders)
                delay(300)
                viewModel.onEvent(NotesEvent.GetNotesByFolderId)
            }
            NotesScreen(
                state = viewModel.state.collectAsState().value,
                onEvent = { event ->
                    when (event) {
                        is NotesEvent.NavigateToFoldersDetails -> {
                            navController.navigate(
                                Screens.FoldersNoteScreen.route.replace(
                                    "{${Constants.FOLDER_ID_ARG}}",
                                    "${state.value.folderId}",
                                )
                            )
                        }
                        is NotesEvent.OpenBottomSheetToAdd -> {
                            navController.navigate(
                                BottomSheets.AddFolderSheet.route.replace(
                                    "{${Constants.FOLDER_ID_ARG}}/{${Constants.FOLDER_NAME_ARG}}",
                                    "-1/FOLDER_ARG",
                                )
                            )


                        }
                        is NotesEvent.NavigateToEditFolder -> {
                            navController.navigate(
                                BottomSheets.AddFolderSheet.route.replace(
                                    "{${Constants.FOLDER_ID_ARG}}/{${Constants.FOLDER_NAME_ARG}}",
                                    "${state.value.folderId}/${Constants.FOLDER_NAME.value}",
                                )
                            )
                        }
                        is NotesEvent.NavigateToShowNotesDetails -> {
                            navController.navigate(
                                Screens.ShowDetailsOfNoteScreen.route.replace(
                                    "{${Constants.FOLDER_ID_ARG}}/{${Constants.NOTE_ID_ARG}}",
                                    "${state.value.folderId}/${state.value.noteId}"
                                )
                            )
                        }
                        is NotesEvent.NavigateToAddNote -> {
                            navController.navigate(
                                Screens.AddNoteScreen.route.replace(
                                    "{${Constants.FOLDER_ID_ARG}}/{${Constants.NOTE_ID_ARG}}",
                                    "-1/-1"
                                )
                            )
                        }
                        is NotesEvent.NavigateToEditNote -> {
                            navController.navigate(
                                Screens.AddNoteScreen.route.replace(
                                    "{${Constants.FOLDER_ID_ARG}}/{${Constants.NOTE_ID_ARG}}",
                                    "-1/${state.value.noteId}"
                                )
                            )
                        }
                        else -> {
                            viewModel.onEvent(event)
                        }
                    }
                }
            )
        }
        bottomSheet(route = BottomSheets.AddFolderSheet.route,arguments = listOf(
            navArgument(Constants.FOLDER_ID_ARG) {
                type = NavType.IntType
            },
            navArgument(Constants.FOLDER_NAME_ARG) {
                type = NavType.StringType
            },
        )
            ) { backstackEntry ->
            val viewModel = hiltViewModel<BottomSheetInsertFolderViewModel>()
            val state = viewModel.state.collectAsState()
            val folderId = backstackEntry.arguments?.getInt(Constants.FOLDER_ID_ARG) ?: -1
            val folderName = backstackEntry.arguments?.getString(Constants.FOLDER_NAME_ARG) ?: "FOLDER_ARG"
            LaunchedEffect(key1 = false, block = {
                viewModel.flow.collect() { event ->
                    when (event) {
                        is BottomSheetInsertFolderOneTimeEvent.CloseBottomSheet -> {
                            navController.popBackStack()
                        }
                    }
                }
            })
            LaunchedEffect(key1 = true, block = {
                viewModel.onEvent(BottomSheetInsertFolderEvent.OnFolderNameChange(if (folderName == "FOLDER_ARG") "" else folderName))
                viewModel.onEvent(BottomSheetInsertFolderEvent.OnFolderIdChange(folderId))
            })
            BottomSheetInsertFolder(state = state.value, onEvent = { event ->
                when (event) {
                    else -> {
                        viewModel.onEvent(event)
                    }
                }
            })
        }
        composable(
            route = Screens.FoldersNoteScreen.route,
            arguments = listOf(navArgument(Constants.FOLDER_ID_ARG) {
                type = NavType.IntType
            })
        ) {
            val folderId = it.arguments?.getInt(Constants.FOLDER_ID_ARG) ?: -1
            val viewModel = hiltViewModel<FolderNotesViewModel>()
            LaunchedEffect(key1 = true) {
                viewModel.onEvent(FolderNotesEvent.OnFolderIdChange(folderId))
                viewModel.onEvent(FolderNotesEvent.GetNotesByFolderId)
            }
            val state = viewModel.state.collectAsState()
            FoldersNoteScreen(
                state = state.value,
            ) { event ->
                when (event) {
                    is FolderNotesEvent.NavigateToAddNote -> {
                        navController.navigate(
                            Screens.AddNoteScreen.route.replace(
                                "{${Constants.FOLDER_ID_ARG}}/{${Constants.NOTE_ID_ARG}}",
                                "${state.value.folderId}/-1"
                            )
                        )
                        Constants.WHICH_NOTE.value = Constants.ADD_NOTE
                    }
                    is FolderNotesEvent.NavigateToEditNote -> {
                        Constants.WHICH_NOTE.value = Constants.EDIT_NOTE
                        navController.navigate(
                            Screens.AddNoteScreen.route.replace(
                                "{${Constants.FOLDER_ID_ARG}}/{${Constants.NOTE_ID_ARG}}",
                                "${state.value.folderId}/${state.value.noteId}"
                            )
                        )
                        Log.e("LOG","${state.value.folderId}/${state.value.noteId}")
                    }
                    is FolderNotesEvent.NavigateToShowDetails -> {
                        navController.navigate(
                            Screens.ShowDetailsOfNoteScreen.route.replace(
                                "{${Constants.FOLDER_ID_ARG}}/{${Constants.NOTE_ID_ARG}}",
                                "${state.value.folderId}/${state.value.noteId}"
                            )
                        )
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
                navArgument(Constants.FOLDER_ID_ARG) {
                    type = NavType.IntType
                },
                navArgument(Constants.NOTE_ID_ARG) {
                    type = NavType.IntType
                }
            )
        ) {
            val folderId = it.arguments?.getInt(Constants.FOLDER_ID_ARG) ?: -1
            val noteId = it.arguments?.getInt(Constants.NOTE_ID_ARG) ?: -1
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
                navArgument(Constants.FOLDER_ID_ARG) {
                    type = NavType.IntType
                },
                navArgument(Constants.NOTE_ID_ARG) {
                    type = NavType.IntType
                }
            )
        ) {
            ShowDetailsOfNoteScreen()
        }
        composable(
            route = Screens.SearchNotesScreen.route
        ) {
            val viewModel = hiltViewModel<SearchNotesScreenViewModel>()
            val searchState = viewModel.state.collectAsState()
            viewModel.onEvent(SearchNotesEvent.OnSearchChange(stateSearch.value))
            SearchNotesScreen(
                state = searchState.value,
                onEvent = { event ->
                    when (event) {
                        is SearchNotesEvent.NavigateToEditNote -> {
                            navController.navigate(
                                Screens.AddNoteScreen.route.replace(
                                    "{${Constants.FOLDER_ID_ARG}}/{${Constants.NOTE_ID_ARG}}",
                                    "${searchState.value.folderId}/${searchState.value.noteId}"
                                )
                            )
                        }
                        is SearchNotesEvent.NavigateToShowDetails -> {
                            navController.navigate(
                                Screens.ShowDetailsOfNoteScreen.route.replace(
                                    "{${Constants.FOLDER_ID_ARG}}/{${Constants.NOTE_ID_ARG}}",
                                    "${searchState.value.folderId}/${searchState.value.noteId}"
                                )
                            )
                        }
                        else -> viewModel.onEvent(event)
                    }
                },
            )
        }

    }
}