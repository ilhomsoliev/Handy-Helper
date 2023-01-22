package com.ikcollab.domain.usecase.goals.goal

import com.ikcollab.model.dto.goals.GoalDto
import com.ikcollab.model.dto.goals.GoalWithStepsDto
import com.ikcollab.model.dto.toGoalDto
import com.ikcollab.model.dto.toGoalWithStepsDto
import com.ikcollab.model.dto.toStepGoalDto
import com.ikcollab.repository.goals.GoalsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetGoalsWithStepsUseCase @Inject constructor(
    private val repository: GoalsRepository
) {
    suspend operator fun invoke(): Flow<List<GoalWithStepsDto>> =
        repository.getGoals().map { goals ->
            goals.sortedBy { it.dateCreated }.map {
                it.toGoalWithStepsDto(
                    steps = repository.getStepsGoalSByGoalIdList(it.id!!).map { it.toStepGoalDto() }
                )
            }
        }
}