package com.ikcollab.handyhelper.app.navigation.graph

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.ikcollab.budget.budget.BudgetEvent
import com.ikcollab.budget.budget.BudgetScreen
import com.ikcollab.budget.budget.BudgetViewModel
import com.ikcollab.budget.budget.bottomSheetAddStoryBudget.BottomSheetAddStoryBudget
import com.ikcollab.budget.budget.bottomSheetAddStoryBudget.BottomSheetAddStoryBudgetEvent
import com.ikcollab.budget.budget.bottomSheetAddStoryBudget.BottomSheetAddStoryBudgetOneTimeEvent
import com.ikcollab.budget.budget.bottomSheetAddStoryBudget.BottomSheetAddStoryBudgetViewModel
import com.ikcollab.budget.category.BudgetCategoryEvent
import com.ikcollab.budget.category.BudgetCategoryScreen
import com.ikcollab.budget.category.BudgetCategoryViewModel
import com.ikcollab.budget.category.bottomSheetAddCategory.BottomSheetAddCategory
import com.ikcollab.budget.category.bottomSheetAddCategory.BottomSheetAddCategoryEvent
import com.ikcollab.budget.category.bottomSheetAddCategory.BottomSheetAddCategoryOneTimeEvent
import com.ikcollab.budget.category.bottomSheetAddCategory.BottomSheetAddCategoryViewModel
import com.ikcollab.core.Constants
import com.ikcollab.handyhelper.app.navigation.Screens
import com.ikcollab.handyhelper.app.navigation.bottomSheet.BottomSheets

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.BudgetNavGraph(navController: NavController) {
    navigation(route = Graph.BudgetGraph.route, startDestination = Screens.BudgetScreen.route) {
        composable(route = Screens.BudgetScreen.route) {
            val viewModel = hiltViewModel<BudgetViewModel>()
            val state = viewModel.state.collectAsState().value
            BudgetScreen(state = state, onEvent = { event ->
                when (event) {
                    is BudgetEvent.OnEditStory -> {
                        navController.navigate(
                            BottomSheets.AddBudgetStorySheet.route.replace(
                                "{${Constants.CATEGORY_TYPE_ID_ARG}}/{${Constants.STORY_ID_ARG}}/{${Constants.CATEGORY_ID_ARG}}",
                                " /${event.storyId}/-1"
                            )
                        )
                    }
                    is BudgetEvent.OpenBottomSheet -> {
                        navController.navigate(
                            BottomSheets.AddBudgetStorySheet.route.replace(
                                "{${Constants.CATEGORY_TYPE_ID_ARG}}/{${Constants.STORY_ID_ARG}}/{${Constants.CATEGORY_ID_ARG}}",
                                "${event.type}/-1/${event.categoryId}"
                            )
                        )
                    }
                    else -> viewModel.onEvent(event)
                }
            }, viewModel.getStorySumByCategoryId)
        }
        composable(route = Screens.BudgetCategoryScreen.route) {
            val viewModel = hiltViewModel<BudgetCategoryViewModel>()
            val state = viewModel.state.collectAsState().value
            BudgetCategoryScreen(state = state, onEvent = { event ->
                when (event) {
                    is BudgetCategoryEvent.OnEditClick -> {
                        navController.navigate(
                            BottomSheets.AddBudgetCategorySheet.route.replace(
                                "{${Constants.CATEGORY_TYPE_ID_ARG}}/{${Constants.CATEGORY_ID_ARG}}",
                                "${event.type}/${event.id}"
                            )
                        )
                    }
                    is BudgetCategoryEvent.OpenBottomSheet -> {
                        navController.navigate(
                            BottomSheets.AddBudgetCategorySheet.route.replace(
                                "{${Constants.CATEGORY_TYPE_ID_ARG}}/{${Constants.CATEGORY_ID_ARG}}",
                                "${event.type}/-1"
                            )
                        )
                    }
                    else -> viewModel.onEvent(event)
                }
            })
        }
        bottomSheet(
            route = BottomSheets.AddBudgetCategorySheet.route, arguments = listOf(
                navArgument(Constants.CATEGORY_TYPE_ID_ARG) {
                    type = NavType.StringType
                },
                navArgument(Constants.CATEGORY_ID_ARG) {
                    type = NavType.IntType
                },
            )
        ) {
            val viewModel = hiltViewModel<BottomSheetAddCategoryViewModel>()
            val state = viewModel.state.collectAsState().value
            LaunchedEffect(key1 = false, block = {
                val type = it.arguments?.getString(Constants.CATEGORY_TYPE_ID_ARG) ?: ""
                val categoryId =
                    if (it.arguments?.getInt(Constants.CATEGORY_ID_ARG) == -1) null else it.arguments?.getInt(
                        Constants.CATEGORY_ID_ARG
                    )

                viewModel.onEvent(BottomSheetAddCategoryEvent.OnLoad(type, categoryId))
                viewModel.flow.collect() { event ->
                    when (event) {
                        is BottomSheetAddCategoryOneTimeEvent.CloseBottomSheet -> {
                            navController.popBackStack()
                        }
                    }
                }
            })
            BottomSheetAddCategory(state = state, onEvent = { event ->
                when (event) {
                    else -> viewModel.onEvent(event)
                }
            })
        }
        bottomSheet(
            route = BottomSheets.AddBudgetStorySheet.route, arguments = listOf(
                navArgument(Constants.CATEGORY_TYPE_ID_ARG) {
                    type = NavType.StringType
                },
                navArgument(Constants.STORY_ID_ARG) {
                    type = NavType.IntType
                },
                navArgument(Constants.CATEGORY_ID_ARG) {
                    type = NavType.IntType
                },
            )
        ) {
            val viewModel = hiltViewModel<BottomSheetAddStoryBudgetViewModel>()
            val state = viewModel.state.collectAsState().value
            LaunchedEffect(key1 = false, block = {
                val type = it.arguments?.getString(Constants.CATEGORY_TYPE_ID_ARG) ?: ""
                val storyId = if ((it.arguments?.getInt(Constants.STORY_ID_ARG)
                        ?: -1) == -1
                ) null else it.arguments?.getInt(Constants.STORY_ID_ARG)
                val categoryId =
                    if ((it.arguments?.getInt(Constants.CATEGORY_ID_ARG)
                            ?: -1) == -1
                    ) null else it.arguments?.getInt(
                        Constants.CATEGORY_ID_ARG
                    )
                viewModel.onEvent(BottomSheetAddStoryBudgetEvent.OnLoad(type, storyId, categoryId))
                viewModel.flow.collect() { event ->
                    when (event) {
                        is BottomSheetAddStoryBudgetOneTimeEvent.CloseBottomSheet -> {
                            navController.popBackStack()
                        }
                    }
                }
            })
            BottomSheetAddStoryBudget(state = state, onEvent = { event ->
                when (event) {
                    else -> viewModel.onEvent(event)
                }
            })
        }
    }
}