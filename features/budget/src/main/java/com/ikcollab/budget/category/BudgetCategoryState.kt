package com.ikcollab.budget.category

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.ikcollab.model.dto.budget.BudgetCategoryDto

data class BudgetCategoryState(
    val expensesCategories: List<BudgetCategoryDto> = emptyList(),
    val incomeCategories: List<BudgetCategoryDto> = emptyList(),
    var isCategoryDialogState:Boolean = false
)