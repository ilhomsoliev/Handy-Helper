package com.ikcollab.todolist.todoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.todo_list.todo.GetTodosByCategoryIdUseCase
import com.ikcollab.domain.usecase.todo_list.todoCategory.GetTodoCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val getTodoCategoriesUseCase: GetTodoCategoriesUseCase,
    private val getTodosByCategoryIdUseCase: GetTodosByCategoryIdUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(TodoListState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        TodoListState()
    )

    init {
        getTodoListCategories()
    }

    fun onEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.ClearTodoList -> {

            }
            is TodoListEvent.ChangePage -> {
                _state.update {
                    it.copy(
                        currentPage = event.page
                    )
                }
            }
            else -> Unit
        }
    }

    private fun getTodoListCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            getTodoCategoriesUseCase().onEach { list ->
                _state.update {
                    it.copy(
                        categories = list
                    )
                }
            }.launchIn(viewModelScope)
        }
    }
}