package com.todolist.app.repository;

import com.todolist.app.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repository interface for accessing TodoItem entities.
 */
public interface TodoRepository extends JpaRepository<TodoItem, Long>, JpaSpecificationExecutor<TodoItem> {

}
