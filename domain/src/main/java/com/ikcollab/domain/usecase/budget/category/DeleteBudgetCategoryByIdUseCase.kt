package com.ikcollab.domain.usecase.budget.category

import com.ikcollab.model.dto.budget.BudgetCategoryDto
import com.ikcollab.model.dto.toBudgetCategoryEntity
import com.ikcollab.model.dto.toTodoCategoryEntity
import com.ikcollab.model.dto.todo_list.TodoCategoryDto
import com.ikcollab.repository.budget.BudgetRepository
import com.ikcollab.repository.todo_list.TodoRepository
import javax.inject.Inject

class DeleteBudgetCategoryByIdUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(id:Int) {
        repository.deleteCategoryById(id)
    }
}