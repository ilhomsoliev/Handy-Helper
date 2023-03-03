package com.ikcollab.model.dto

import com.ikcollab.model.dto.budget.BudgetCategoryDto
import com.ikcollab.model.dto.budget.BudgetStoryDto
import com.ikcollab.model.dto.goals.GoalDto
import com.ikcollab.model.dto.goals.GoalWithStepsDto
import com.ikcollab.model.dto.goals.StepGoalDto
import com.ikcollab.model.dto.note.FolderDto
import com.ikcollab.model.dto.note.NoteDto
import com.ikcollab.model.dto.todo_list.TodoCategoryDto
import com.ikcollab.model.dto.todo_list.TodoDto
import com.ikcollab.model.local.budget.BudgetCategoryEntity
import com.ikcollab.model.local.budget.BudgetStoryEntity
import com.ikcollab.model.local.goals.GoalEntity
import com.ikcollab.model.local.goals.StepGoalEntity
import com.ikcollab.model.local.note.FolderEntity
import com.ikcollab.model.local.note.NoteEntity
import com.ikcollab.model.local.todo_list.TodoCategoryEntity
import com.ikcollab.model.local.todo_list.TodoEntity

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

fun FolderEntity.toFolderDto(notesCount:Int) = FolderDto(
    id = id,
    name = name,
    dateCreated = dateCreated,
    countOfNotes = notesCount
)

fun FolderDto.toFolderEntity() = FolderEntity(
    id = id,
    name = name,
    dateCreated = dateCreated
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

fun GoalEntity.toGoalWithStepsDto(steps: List<StepGoalDto>) = GoalWithStepsDto(
    id = id,
    name = name,
    dateCreated = dateCreated,
    steps = steps,
)

fun GoalDto.toGoalEntity() = GoalEntity(
    id = id,
    name = name,
    dateCreated = dateCreated,
    dateStart = dateStart,
    dateEnd = dateEnd,
)

fun StepGoalEntity.toStepGoalDto() = StepGoalDto(
    id = id,
    name = name,
    dateCreated = dateCreated,
    deadline = deadline,
    isCompleted = isCompleted,
    goalId = goalId,
)

fun StepGoalDto.toStepGoalEntity() = StepGoalEntity(
    id = id,
    name = name,
    dateCreated = dateCreated,
    deadline = deadline,
    isCompleted = isCompleted,
    goalId = goalId,
)

fun TodoDto.toTodoEntity() = TodoEntity(
    id = id,
    title = title,
    dateCreated = dateCreated,
    deadline = deadline,
    priority = priority,
    repeatStatus = repeatStatus,
    categoryId = categoryId,
    isCompleted = isCompleted,
)

fun TodoEntity.toTodoDto() = TodoDto(
    id = id,
    title = title,
    dateCreated = dateCreated,
    deadline = deadline,
    priority = priority,
    repeatStatus = repeatStatus,
    categoryId = categoryId,
    isCompleted = isCompleted,
)

fun TodoCategoryDto.toTodoCategoryEntity() = TodoCategoryEntity(
    id = id,
    title = title,
    dateCreated = dateCreated,
)

fun TodoCategoryEntity.toTodoCategoryDto(todosCount: Int) = TodoCategoryDto(
    id = id,
    title = title,
    dateCreated = dateCreated,
    todosCount = todosCount,
)

fun BudgetStoryEntity.toBudgetStoryDto(categoryName: String) = BudgetStoryDto(
    id = id,
    comment = comment,
    value = value,
    type = type,
    dateCreated = dateCreated,
    categoryId = categoryId,
    categoryName = categoryName,
)

fun BudgetStoryDto.toBudgetStoryEntity() = BudgetStoryEntity(
    id = id,
    comment = comment,
    value = value,
    type = type,
    dateCreated = dateCreated,
    categoryId = categoryId,
)

fun BudgetCategoryEntity.toBudgetCategoryDto() = BudgetCategoryDto(
    id = id,
    name = name,
    dateCreated = dateCreated,
    type = type,
)

fun BudgetCategoryDto.toBudgetCategoryEntity() = BudgetCategoryEntity(
    id = id,
    name = name,
    dateCreated = dateCreated,
    type = type,
)