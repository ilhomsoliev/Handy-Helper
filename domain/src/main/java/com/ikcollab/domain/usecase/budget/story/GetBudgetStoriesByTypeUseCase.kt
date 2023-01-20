package com.ikcollab.domain.usecase.budget.story

import com.ikcollab.model.dto.budget.BudgetCategoryDto
import com.ikcollab.model.dto.budget.BudgetStoryDto
import com.ikcollab.model.dto.toBudgetCategoryDto
import com.ikcollab.model.dto.toBudgetStoryDto
import com.ikcollab.repository.budget.BudgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBudgetStoriesByTypeUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    operator fun invoke(type: String): Flow<List<BudgetStoryDto>> {
        return repository.getStoriesByType(type = type).map { todos ->
            todos.sortedBy { it.dateCreated }.map {
                it.toBudgetStoryDto(repository.getCategoryById(it.categoryId)!!.name)
            }
        }
    }
}