package com.ikcollab.domain.usecase.budget.story

import com.ikcollab.repository.budget.BudgetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStorySumByCategoryId @Inject constructor(
    private val repository: BudgetRepository
) {
    operator fun invoke(id: Int): Flow<Double?> {
        return repository.getStorySumByCategoryId(id)
    }
}