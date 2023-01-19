package com.ikcollab.domain.usecase.goals.goal

import com.ikcollab.repository.goals.GoalsRepository
import com.ikcollab.repository.notes.NotesRepository
import javax.inject.Inject

class GetFolderByIdUseCase @Inject constructor(
    private val repository: GoalsRepository
) {
    suspend operator fun invoke(goalId: Int) = repository.getGoalById(goalId)

}