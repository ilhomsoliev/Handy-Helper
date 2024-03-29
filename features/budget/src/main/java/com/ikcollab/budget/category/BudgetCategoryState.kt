package com.ikcollab.budget.category

import com.ikcollab.model.dto.budget.BudgetCategoryDto

data class BudgetCategoryState(
    val expensesCategories: List<BudgetCategoryDto> = emptyList(),
    val incomeCategories: List<BudgetCategoryDto> = emptyList(),
    var isCategoryDialogState:Boolean = false,
    val deleteCategoryId:Int = -1,
    val deleteCategoryName:String = ""
)