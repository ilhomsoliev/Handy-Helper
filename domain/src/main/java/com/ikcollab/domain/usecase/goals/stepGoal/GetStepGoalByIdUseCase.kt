package com.ikcollab.domain.usecase.goals.stepGoal

import com.ikcollab.repository.goals.GoalsRepository
import javax.inject.Inject

class GetStepGoalByIdUseCase @Inject constructor(
    private val repository: GoalsRepository
) {
    suspend operator fun invoke(stepGoalId: Int) = repository.getStepGoalById(stepGoalId)

}