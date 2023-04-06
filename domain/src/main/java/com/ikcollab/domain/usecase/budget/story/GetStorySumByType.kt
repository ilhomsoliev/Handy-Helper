package com.ikcollab.domain.usecase.budget.story

import com.ikcollab.repository.budget.BudgetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStorySumByType @Inject constructor(
    private val repository: BudgetRepository
) {
    operator fun invoke(type: String): Flow<Double?> {
        return repository.getStorySumByType(type)
    }
}