package com.ikcollab.handyhelper.app.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.budget.story.DeleteBudgetStoryByIdUseCase
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
    private val insertFolderUseCase: InsertFolderUseCase,
    private val insertTodoCategoryUseCase: InsertTodoCategoryUseCase,
    private val insertTodoUseCase: InsertTodoUseCase,
    private val deleteBudgetStoryByIdUseCase: DeleteBudgetStoryByIdUseCase

) : ViewModel() {

    private val _state = MutableStateFlow(NavigationState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        NavigationState()
    )
    fun onEvent(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.StartOverExpenses->{
                //TODO
            }
            is NavigationEvent.StartOverIncome->{
                //TODO
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
            is NavigationEvent.InsertFolder -> {
                val name = _state.value.folderName
                val dateCreated = System.currentTimeMillis()
                if (name.isEmpty()) return
                viewModelScope.launch {
                    insertFolderUseCase(
                        FolderDto(
                            name = name,
                            dateCreated = dateCreated,
                            id = if(_state.value.folderId!=-1) _state.value.folderId else null
                        )
                    )
                }
                onEvent(NavigationEvent.OnFolderNameChange(""))
            }
            is NavigationEvent.OnFolderIdChange->{
                _state.update {
                    it.copy(folderId = event.value)
                }
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
            else ->{

            }
        }
    }
}