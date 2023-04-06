package com.ikcollab.budget.budget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ikcollab.budget.budget.components.ExpensesScreen
import com.ikcollab.components.customTabs.CustomTab
import com.ikcollab.domain.usecase.budget.story.GetStorySumByCategoryId
import com.ikcollab.model.local.budget.EXPENSES_TYPE
import com.ikcollab.model.local.budget.INCOME_TYPE
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BudgetScreen(
    state: BudgetState,
    onEvent: (BudgetEvent) -> Unit,
    getStorySumByCategoryId: GetStorySumByCategoryId
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
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
                    ExpensesScreen(
                        categories = state.expensesCategories,
                        total = state.totalSumExpense.toString(),
                        balance = (state.totalSumIncome - state.totalSumExpense).toString(),
                        onAddClick = {
                            onEvent(BudgetEvent.OpenBottomSheet(type = EXPENSES_TYPE, it))
                        },
                        stories = state.expensesStories,
                        onEditClick = {
                            onEvent(BudgetEvent.OnEditStory(it))
                        },
                        onDeleteClick = {
                            onEvent(BudgetEvent.DeleteStory(it))
                        },
                        getStorySumByType = getStorySumByCategoryId
                    )
                }
                1 -> {
                    ExpensesScreen(
                        categories = state.incomeCategories,
                        total = state.totalSumIncome.toString(),
                        balance = "",
                        onAddClick = {
                            onEvent(BudgetEvent.OpenBottomSheet(INCOME_TYPE, it))
                        },
                        stories = state.incomeStories,
                        onEditClick = {
                            onEvent(BudgetEvent.OnEditStory(it))
                        },
                        onDeleteClick = {
                            onEvent(BudgetEvent.DeleteStory(it))

                        },
                        getStorySumByType = getStorySumByCategoryId
                    )
                    /*IncomeScreen(
                        categories = state.incomeCategories,
                        total = 0,
                        balance = 0,
                    )*/
                }
            }
        }
    }
}
