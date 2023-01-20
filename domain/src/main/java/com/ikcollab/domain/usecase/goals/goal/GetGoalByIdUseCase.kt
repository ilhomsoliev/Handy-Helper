package com.ikcollab.domain.usecase.goals.goal

import com.ikcollab.repository.goals.GoalsRepository
import javax.inject.Inject

class GetGoalByIdUseCase @Inject constructor(
    private val repository: GoalsRepository
) {
    suspend operator fun invoke(goalId: Int) = repository.getGoalById(goalId)

}