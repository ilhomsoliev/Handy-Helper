package com.ikcollab.handyhelper.app.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.goals.goal.InsertGoalUseCase
import com.ikcollab.domain.usecase.goals.stepGoal.InsertStepGoalUseCase
import com.ikcollab.domain.usecase.notes.folder.InsertFolderUseCase
import com.ikcollab.domain.usecase.todo_list.todo.InsertTodoUseCase
import com.ikcollab.domain.usecase.todo_list.todoCategory.InsertTodoCategoryUseCase
import com.ikcollab.model.dto.goals.GoalDto
import com.ikcollab.model.dto.goals.StepGoalDto
import com.ikcollab.model.dto.note.FolderDto
import com.ikcollab.model.dto.todo_list.TodoCategoryDto
import com.ikcollab.model.dto.todo_list.TodoDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class NavigationViewModel @Inject constructor(
    private val insertGoalUseCase: InsertGoalUseCase,
    private val insertFolderUseCase: InsertFolderUseCase,
    private val insertStepGoalUseCase: InsertStepGoalUseCase,
    private val insertTodoCategoryUseCase: InsertTodoCategoryUseCase,
    private val insertTodoUseCase: InsertTodoUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(NavigationState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        NavigationState()
    )

    fun onEvent(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.OnNewGoalNameChange -> {
                _state.update {
                    it.copy(
                        goalName = event.value
                    )
                }
            }
            is NavigationEvent.OnNewStepGoalNameChange -> {
                _state.update {
                    it.copy(
                        stepGoalName = event.value
                    )
                }
            }

            is NavigationEvent.OnNewStepGoalIdChange -> {
                _state.update {
                    it.copy(
                        stepGoalId = event.value
                    )
                }
            }
            is NavigationEvent.OnFolderNameChange -> {
                _state.update {
                    it.copy(
                        folderName = event.value
                    )
                }
            }
            is NavigationEvent.OnTodoCategoryNameChange -> {
                _state.update {
                    it.copy(
                        todoCategoryName = event.value
                    )
                }
            }
            is NavigationEvent.OnNewGoalStartDateChange -> {
                _state.update {
                    it.copy(
                        goalStartDate = event.value
                    )
                }
            }
            is NavigationEvent.OnNewStepGoalDeadlineChange -> {
                _state.update {
                    it.copy(
                        stepGoalDeadline = event.value
                    )
                }
            }
            is NavigationEvent.OnNewGoalEndDateChange -> {
                _state.update {
                    it.copy(
                        goalEndDate = event.value
                    )
                }
            }
            is NavigationEvent.InsertFolder -> {
                val name = _state.value.folderName
                val dateCreated = System.currentTimeMillis()
                if (name.isEmpty()) return
                viewModelScope.launch {
                    insertFolderUseCase(
                        FolderDto(
                            name = name,
                            dateCreated = dateCreated,
                            id = null
                        )
                    )
                }
                onEvent(NavigationEvent.OnFolderNameChange(""))
            }
            is NavigationEvent.InsertTodoCategory -> {
                val name = _state.value.todoCategoryName
                val dateCreated = System.currentTimeMillis()
                if (name.isEmpty()) return
                viewModelScope.launch {
                    insertTodoCategoryUseCase(
                        TodoCategoryDto(
                            title = name,
                            dateCreated = dateCreated,
                            id = null,
                            todosCount = 0
                        )
                    )
                }
                onEvent(NavigationEvent.OnTodoCategoryNameChange(""))
            }

            is NavigationEvent.InsertStepGoalToDatabase -> {
                _state.value.apply {
                    if (stepGoalName.isEmpty() && stepGoalId != -1) return
                    viewModelScope.launch {
                        insertStepGoalUseCase(
                            StepGoalDto(
                                id = null,
                                name = stepGoalName,
                                isCompleted = false,
                                dateCreated = System.currentTimeMillis(),
                                deadline = stepGoalDeadline,
                                goalId = stepGoalId,
                            )
                        )
                        onEvent(NavigationEvent.OnNewStepGoalDeadlineChange(System.currentTimeMillis()))
                        onEvent(NavigationEvent.OnNewStepGoalNameChange(""))
                    }
                    onEvent(NavigationEvent.OnTodoCategoryNameChange(""))
                }
            }
            is NavigationEvent.InsertGoalToDatabase -> {
                _state.value.apply {
                    if (goalName.isEmpty()) return
                    viewModelScope.launch {
                        insertGoalUseCase(
                            GoalDto(
                                id = null,
                                name = goalName,
                                stepsCount = 0,
                                completedStepsCount = 0,
                                dateCreated = System.currentTimeMillis(),
                                dateStart = goalStartDate,
                                dateEnd = goalEndDate,
                            )
                        )
                        onEvent(NavigationEvent.OnNewGoalNameChange(""))
                    }
                }
            }
            is NavigationEvent.InsertTodoTaskToDatabase -> {
                _state.value.apply {
                    if (todoTaskName.isEmpty()) return
                    viewModelScope.launch {
                        insertTodoUseCase(
                            TodoDto(
                                id = null,
                                title = todoTaskName,
                                dateCreated = System.currentTimeMillis(),
                                deadline = todoTaskDeadline,
                                priority = "",// TODO
                                repeatStatus = "", // TODO
                                categoryId = todoCategoryId,
                                isCompleted = false,
                            )
                        )
                        onEvent(NavigationEvent.OnNewGoalNameChange(""))
                    }
                }
            }
            is NavigationEvent.OnTodoCategoryIdChange -> {
                _state.update {
                    it.copy(
                        todoCategoryId = event.value
                    )
                }
            }
            is NavigationEvent.OnTodoNameChange -> {
                _state.update {
                    it.copy(
                        todoTaskName = event.value
                    )
                }
            }
            is NavigationEvent.OnSearchNotes->{
                _state.update {
                    it.copy(
                        searchState = event.value
                    )
                }
            }
        }
    }
}