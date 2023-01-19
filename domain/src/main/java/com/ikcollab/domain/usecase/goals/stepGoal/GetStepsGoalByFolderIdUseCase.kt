package com.ikcollab.domain.usecase.goals.stepGoal

import com.ikcollab.model.dao.goals.StepGoalDto
import com.ikcollab.model.dao.toStepGoalDto
import com.ikcollab.repository.goals.GoalsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetStepsGoalByFolderIdUseCase @Inject constructor(
    private val repository: GoalsRepository
){
    operator fun invoke(goalId:Int): Flow<List<StepGoalDto>> {
        return repository.getStepsGoalSByGoalId(goalId = goalId).map { stepGoals ->
            stepGoals.sortedBy { it.dateCreated }.map {
                it.toStepGoalDto()
            }
        }
    }
}