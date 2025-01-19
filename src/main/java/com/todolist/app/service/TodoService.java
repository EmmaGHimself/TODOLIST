package com.todolist.app.service;

import com.todolist.app.model.TodoItem;

import java.util.List;

/**
 * Service interface for managing Todo tasks.
 */
public interface TodoService {

    /**
     * Retrieve a list of tasks filtered by priority and completion status.
     *
     * @param priority  the priority level to filter by (nullable)
     * @param completed the completion status to filter by (nullable)
     * @return a list of tasks matching the filter criteria
     */
    List<TodoItem> getTasks(Integer priority, Boolean completed);


    /**
     * Retrieve all tasks from the repository.
     *
     * @return a list of all TodoItem objects
     */
    List<TodoItem> getAllTasks();

    /**
     * Create a new task.
     *
     * @param todoItem the task to create
     * @return the created task
     */
    TodoItem createTask(TodoItem todoItem);

    /**
     * Retrieve a task by its ID.
     *
     * @param id the ID of the task to retrieve
     * @return the task if found, or null if not found
     */
    TodoItem getTaskById(Long id);

    /**
     * Update an existing task.
     *
     * @param id       the ID of the task to update
     * @param todoItem the updated task details
     * @return the updated task
     */
    TodoItem updateTask(Long id, TodoItem todoItem);

    /**
     * Delete a task by its ID.
     *
     * @param id the ID of the task to delete
     */
    void deleteTask(Long id);
}
