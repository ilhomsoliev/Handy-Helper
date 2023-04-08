package com.ikcollab.budget.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ikcollab.budget.category.components.BudgetCategoriesList
import com.ikcollab.components.CustomDialog
import com.ikcollab.components.customTabs.CustomTab
import com.ikcollab.model.local.budget.EXPENSES_TYPE
import com.ikcollab.model.local.budget.INCOME_TYPE
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BudgetCategoryScreen(
    state: BudgetCategoryState,
    onEvent: (BudgetCategoryEvent) -> Unit
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val deleteCategoryId = remember {
        mutableStateOf(-1)
    }
    val deleteCategoryName = remember {
        mutableStateOf("")
    }
    CustomDialog(
        text = "Attention",
        description = "You are deleting a category:\n '${deleteCategoryName.value}'. All expense records in this category will also be deleted",
        okBtnClick =
        {
            onEvent(BudgetCategoryEvent.DeleteCategory(deleteCategoryId.value))
            state.isCategoryDialogState = false
        },
        cancelBtnClick = { state.isCategoryDialogState = false },
        isDialogOpen = state.isCategoryDialogState,
        okBtnText = "Delete",
        cancelBtnText = "Cancel"
    ) {
        state.isCategoryDialogState = false
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            CustomTab(
                items = listOf("Expenses", "Income"),
                selectedItemIndex = pagerState.currentPage,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(it)
                    }
                },
            )
        }
        HorizontalPager(
            count = 2,
            state = pagerState,
        ) {
            when (pagerState.currentPage) {
                0 -> {
                    BudgetCategoriesList(
                        categories = state.expensesCategories,
                        onAddClick = {
                            onEvent(BudgetCategoryEvent.OpenBottomSheet(EXPENSES_TYPE))
                        },
                        onDeleteClick = { id,name->
                            state.isCategoryDialogState = true
                            deleteCategoryId.value = id
                            deleteCategoryName.value = name
                        },
                        onEditClick = {
                            onEvent(BudgetCategoryEvent.OnEditClick(EXPENSES_TYPE, it))
                        }
                    )
                }
                1 -> {
                    BudgetCategoriesList(
                        categories = state.incomeCategories,
                        onAddClick = {
                            onEvent(BudgetCategoryEvent.OpenBottomSheet(INCOME_TYPE))
                        },
                        onDeleteClick = { id,name->
                            state.isCategoryDialogState = true
                            deleteCategoryId.value = id
                            deleteCategoryName.value = name
                        },
                        onEditClick = {
                            onEvent(BudgetCategoryEvent.OnEditClick(INCOME_TYPE, it))
                        }
                    )
                }
            }
        }
    }
}