package com.todolist.app.service.impl;

import com.todolist.app.model.TodoItem;
import com.todolist.app.repository.TodoRepository;
import com.todolist.app.service.TodoService;
import com.todolist.app.specification.TodoSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the TodoService interface for managing tasks.
 */
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * Get a list of tasks filtered by priority and completion status.
     *
     * @param priority  the priority level to filter by (nullable)
     * @param completed the completion status to filter by (nullable)
     * @return a list of tasks matching the criteria
     */
    @Override
    public List<TodoItem> getTasks(Integer priority, Boolean completed) {
        Specification<TodoItem> specification = TodoSpecification.filterByPriorityAndCompleted(priority, completed);
        Sort sort = Sort.by(Sort.Direction.DESC, "updatedAt"); // Sort by updatedAt in ascending order
        return todoRepository.findAll(specification, sort);
    }

    /**
     * Retrieve all tasks from the repository.
     *
     * This method fetches all the tasks stored in the database without any filtering or sorting.
     * @return a list of all TodoItem objects
     */
    @Override
    public List<TodoItem> getAllTasks() {
        // Fetch all tasks from the repository
        return todoRepository.findAll();
    }

    /**
     * Create a new task.
     *
     * @param todoItem the task to create
     * @return the created task
     */
    @Override
    public TodoItem createTask(TodoItem todoItem) {
        return todoRepository.save(todoItem);
    }

    /**
     * Update an existing task.
     *
     * @param id       the ID of the task to update
     * @param todoItem the task details to update
     * @return the updated task
     */
    @Override
    public TodoItem updateTask(Long id, TodoItem todoItem) {
        todoItem.setId(id); // Ensure the task ID is set for update
        return todoRepository.save(todoItem);
    }

    /**
     * Retrieve a task by its ID.
     *
     * @param id the ID of the task to retrieve
     * @return the task if found, or null if not found
     */
    @Override
    public TodoItem getTaskById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    /**
     * Delete a task by its ID.
     *
     * @param id the ID of the task to delete
     */
    @Override
    public void deleteTask(Long id) {
        todoRepository.deleteById(id);
    }
}
