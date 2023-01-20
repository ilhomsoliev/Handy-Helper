package com.ikcollab.domain.usecase.budget.category

import com.ikcollab.model.dto.budget.BudgetCategoryDto
import com.ikcollab.model.dto.toBudgetCategoryDto
import com.ikcollab.model.dto.toTodoCategoryDto
import com.ikcollab.model.dto.todo_list.TodoCategoryDto
import com.ikcollab.repository.budget.BudgetRepository
import com.ikcollab.repository.todo_list.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBudgetCategoriesByTypeUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    operator fun invoke(type: String): Flow<List<BudgetCategoryDto>> {
        return repository.getCategoriesByType(type = type).map { todos ->
            todos.sortedBy { it.dateCreated }.map {
                it.toBudgetCategoryDto()
            }
        }
    }
}