package com.ikcollab.domain.usecase.goals.goal

import com.ikcollab.local.dao.goals.GoalDao
import com.ikcollab.model.dao.goals.GoalDto
import com.ikcollab.model.dao.note.FolderDto
import com.ikcollab.model.dao.toFolderEntity
import com.ikcollab.model.dao.toGoalEntity
import com.ikcollab.repository.goals.GoalsRepository
import com.ikcollab.repository.notes.NotesRepository
import javax.inject.Inject

class InsertFolderUseCase @Inject constructor(
    private val repository: GoalsRepository
) {
    suspend operator fun invoke(goalDto: GoalDto) {
        repository.insertGoal(goalDto.toGoalEntity())
    }
}