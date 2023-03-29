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
import com.ikcollab.model.local.budget.EXPENSES_TYPE
import com.ikcollab.model.local.budget.INCOME_TYPE
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BudgetScreen(
    state: BudgetState,
    onEvent: (BudgetEvent) -> Unit,
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
                        total = "0.0",
                        balance = "0.0",
                        onAddClick = {
                            onEvent(BudgetEvent.OpenBottomSheet(EXPENSES_TYPE))
                        },
                        stories = state.expensesStories
                    )
                }
                1 -> {
                    ExpensesScreen(
                        categories = state.incomeCategories,
                        total = "0.0",
                        balance = "0.0",
                        onAddClick = {
                            onEvent(BudgetEvent.OpenBottomSheet(INCOME_TYPE))
                        },
                        stories = state.incomeStories
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
