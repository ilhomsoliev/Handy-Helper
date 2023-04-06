package com.ikcollab.domain.usecase.budget.category

import android.util.Log
import androidx.compose.runtime.collectAsState
import com.ikcollab.model.dto.budget.BudgetCategoryDto
import com.ikcollab.model.dto.budget.BudgetCategoryWithSumDto
import com.ikcollab.model.dto.toBudgetCategoryDto
import com.ikcollab.model.dto.toBudgetCategoryWithSumDto
import com.ikcollab.repository.budget.BudgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
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

class GetBudgetCategoriesWithSumByTypeUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    operator fun invoke(type: String): Flow<List<BudgetCategoryWithSumDto>> {
        return repository.getCategoriesByType(type = type).map {
            val list = mutableListOf<BudgetCategoryWithSumDto>()
            it.forEach { entity ->
                repository.getStorySumByCategoryId(entity.id!!).map { value ->
                    list.add(entity.toBudgetCategoryWithSumDto(value ?: 0.0))
                }
            }
            Log.d("Hello Repos", list.toString())
            list
        }
    }
}