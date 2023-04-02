package com.onionit.ebank.service;

import com.onionit.ebank.model.Todo;
import com.onionit.ebank.payload.ApiResponse;
import com.onionit.ebank.payload.PagedResponse;
import com.onionit.ebank.security.UserPrincipal;

public interface TodoService {

	Todo completeTodo(Long id, UserPrincipal currentUser);

	Todo unCompleteTodo(Long id, UserPrincipal currentUser);

	PagedResponse<Todo> getAllTodos(UserPrincipal currentUser, int page, int size);

	Todo addTodo(Todo todo, UserPrincipal currentUser);

	Todo getTodo(Long id, UserPrincipal currentUser);

	Todo updateTodo(Long id, Todo newTodo, UserPrincipal currentUser);

	ApiResponse deleteTodo(Long id, UserPrincipal currentUser);

}
