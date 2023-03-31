package com.ikcollab.budget.category.bottomSheetAddCategory

import com.ikcollab.model.dto.budget.BudgetCategoryDto
import com.ikcollab.model.local.budget.EXPENSES_TYPE

data class BottomSheetAddCategoryState(
    val category: BudgetCategoryDto = BudgetCategoryDto(
        null, "", System.currentTimeMillis(),
        EXPENSES_TYPE
    ),
)