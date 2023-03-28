package com.ikcollab.budget.category

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
import com.ikcollab.budget.category.components.BudgetCategoriesList
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
                        }
                    )
                }
                1 -> {
                    BudgetCategoriesList(
                        categories = state.incomeCategories,
                        onAddClick = {
                            onEvent(BudgetCategoryEvent.OpenBottomSheet(INCOME_TYPE))
                        }
                    )
                }
            }
        }
    }
}