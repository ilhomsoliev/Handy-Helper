package com.ikcollab.todolist.todoListScreen.todoHorizontalPageScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.todo_list.todo.GetTodosByCategoryIdUseCase
import com.ikcollab.todolist.todoListScreen.TodoListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class TodoHorizontalPageViewModel @Inject constructor(
    private val getTodosByCategoryIdUseCase: GetTodosByCategoryIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TodoHorizontalPageState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        TodoHorizontalPageState()
    )

    fun onEvent(event: TodoHorizontalPageEvent) {
        when (event) {
            is TodoHorizontalPageEvent.OnLoadPage -> {
                getTodosByCategory(event.id)
            }
            else -> {

            }
        }
    }

    private fun getTodosByCategory(categoryId: Int) {
        _state.update {
            it.copy(isLoading = true)
        }
        getTodosByCategoryIdUseCase(categoryId).onEach { list ->
            _state.update {
                it.copy(
                    todoList = list
                )
            }
        }.launchIn(viewModelScope)
    }
}