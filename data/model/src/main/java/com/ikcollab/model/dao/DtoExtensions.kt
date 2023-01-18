package com.ikcollab.model.dao

import com.ikcollab.model.dao.goals.GoalDto
import com.ikcollab.model.dao.goals.StepGoalDto
import com.ikcollab.model.dao.note.FolderDto
import com.ikcollab.model.dao.note.NoteDto
import com.ikcollab.model.local.goals.GoalEntity
import com.ikcollab.model.local.goals.StepGoalEntity
import com.ikcollab.model.local.note.FolderEntity
import com.ikcollab.model.local.note.NoteEntity

fun NoteEntity.toNoteDto() = NoteDto(
    id = id,
    title = title,
    description = description,
    dateCreated = dateCreated,
    folderId = folderId,
)

fun NoteDto.toNoteEntity() = NoteEntity(
    id = id,
    title = title,
    description = description,
    dateCreated = dateCreated,
    folderId = folderId,
)

fun FolderEntity.toFolderDto() = FolderDto(
    id = id,
    name = name,
    dateCreated = dateCreated,
)

fun FolderDto.toFolderEntity() = FolderEntity(
    id = id,
    name = name,
    dateCreated = dateCreated,
)

fun GoalEntity.toGoalDto(stepsCount: Int, completedStepsCount: Int) = GoalDto(
    id = id,
    name = name,
    dateCreated = dateCreated,
    dateStart = dateStart,
    dateEnd = dateEnd,
    stepsCount = stepsCount,
    completedStepsCount = completedStepsCount,
)

fun GoalDto.toGoalEntity() = GoalEntity(
    id = id,
    name = name,
    dateCreated = dateCreated,
    dateStart = dateStart,
    dateEnd = dateEnd,
)

fun StepGoalEntity.toStepGoalDto(stepsCount: Int, completedStepsCount: Int) = StepGoalDto(
    id = id,
    name = name,
    dateCreated = dateCreated,
    deadline = deadline,
    isCompleted = isCompleted,
)

fun StepGoalDto.toStepGoalEntity() = StepGoalEntity(
    id = id,
    name = name,
    dateCreated = dateCreated,
    deadline = deadline,
    isCompleted = isCompleted,
)
