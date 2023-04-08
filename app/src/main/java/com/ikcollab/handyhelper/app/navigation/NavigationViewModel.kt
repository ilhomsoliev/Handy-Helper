package com.ikcollab.handyhelper.app.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.InsertAllPresetEntitiesUseCase
import com.ikcollab.domain.usecase.budget.story.DeleteStoriesByTypeUseCase
import com.ikcollab.domain.usecase.todo_list.todo.InsertTodoUseCase
import com.ikcollab.domain.usecase.todo_list.todoCategory.InsertTodoCategoryUseCase
import com.ikcollab.model.dto.todo_list.TodoCategoryDto
import com.ikcollab.model.dto.todo_list.TodoDto
import com.ikcollab.model.local.budget.EXPENSES_TYPE
import com.ikcollab.model.local.budget.INCOME_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class NavigationViewModel @Inject constructor(
    private val insertTodoCategoryUseCase: InsertTodoCategoryUseCase,
    private val insertTodoUseCase: InsertTodoUseCase,
    private val deleteStoriesByTypeUseCase: DeleteStoriesByTypeUseCase,
    private val insertAllPresetEntitiesUseCase: InsertAllPresetEntitiesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NavigationState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        NavigationState()
    )

    init {
       /* viewModelScope.launch {
            insertAllPresetEntitiesUseCase()
        }*/
    }
    fun onEvent(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.StartOverExpenses -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteStoriesByTypeUseCase(EXPENSES_TYPE)
                }
            }
            is NavigationEvent.OnTodoCategoryNameChange -> {
                _state.update {
                    it.copy(
                        todoCategoryName = event.value
                    )
                }
            }
            is NavigationEvent.StartOverIncome -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteStoriesByTypeUseCase(INCOME_TYPE)
                }
            }
            is NavigationEvent.OnDeleteExpenseDialogChange -> {
                _state.update {
                    it.copy(isDeleteBudgetExpenseTypeDialogActive = event.value)
                }
            }
            is NavigationEvent.OnDeleteIncomeDialogChange -> {
                _state.update {
                    it.copy(isDeleteBudgetIncomeTypeDialogActive = event.value)
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
            is NavigationEvent.OnSearchNotes -> {
                _state.update {
                    it.copy(
                        searchState = event.value
                    )
                }
            }
            else -> {

            }
        }
    }
}