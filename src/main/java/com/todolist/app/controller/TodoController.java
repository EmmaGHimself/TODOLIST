package com.todolist.app.controller;

import com.todolist.app.dto.request.TodoItemCreateRequestDTO;
import com.todolist.app.dto.response.TodoItemResponseDTO;
import com.todolist.app.dto.request.TodoItemUpdateRequestDTO;
import com.todolist.app.model.TodoItem;
import com.todolist.app.dto.response.ApiResponse;
import com.todolist.app.service.TodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/todos")
@Api(value = "Todo Management System", tags = {"Todos"})
@Validated
public class TodoController {

    @Autowired
    private TodoService todoService;

    /**
     * Converts a TodoItem entity to its corresponding response DTO.
     *
     * @param todoItem the TodoItem entity to convert
     * @return the corresponding TodoItemResponseDTO
     */
    private TodoItemResponseDTO convertToResponseDTO(TodoItem todoItem) {
        return new TodoItemResponseDTO(
                todoItem.getId(),
                todoItem.getTitle(),
                todoItem.getDescription(),
                todoItem.getDueDate(),
                todoItem.isCompleted(),
                todoItem.getPriority(),
                todoItem.getCreatedAt(),
                todoItem.getUpdatedAt()
        );
    }

    /**
     * Get all tasks with optional filters for priority and completion status.
     *
     * @param priority  optional priority filter
     * @param completed optional completion status filter
     * @return a list of filtered tasks
     */
    @GetMapping
    @ApiOperation(value = "Get all tasks", response = ApiResponse.class)
    public ResponseEntity<ApiResponse<List<TodoItemResponseDTO>>> getTasks(
            @ApiParam(value = "Filter by priority", required = false) @RequestParam(required = false) Integer priority,
            @ApiParam(value = "Filter by completion status", required = false) @RequestParam(required = false) Boolean completed) {

        // Fetch tasks with optional filters
        List<TodoItem> tasks = todoService.getTasks(priority, completed);

        // Convert entities to DTOs
        List<TodoItemResponseDTO> taskDTOs = tasks.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        // Return response with task DTOs
        ApiResponse<List<TodoItemResponseDTO>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Tasks retrieved successfully",
                taskDTOs
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Create a new task.
     *
     * @param todoItemCreateRequestDTO the request body containing task details
     * @return the created task as a response DTO
     */
    @PostMapping
    @ApiOperation(value = "Create a new task", response = ApiResponse.class)
    public ResponseEntity<ApiResponse<TodoItemResponseDTO>> createTask(
            @Valid @RequestBody TodoItemCreateRequestDTO todoItemCreateRequestDTO) {

        // Convert dueDate string to LocalDate
        LocalDate dueDate = todoItemCreateRequestDTO.getParsedDueDate();

        // Ensure the dueDate is not in the past
        if (dueDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date must be in the future or present");
        }

        // Create a new task entity from the request DTO
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle(todoItemCreateRequestDTO.getTitle());
        todoItem.setDescription(todoItemCreateRequestDTO.getDescription());
        todoItem.setDueDate(LocalDateTime.of(dueDate, LocalDateTime.now().toLocalTime()));
        todoItem.setPriority(todoItemCreateRequestDTO.getPriority());

        // Save the task and convert it to a DTO
        TodoItem createdTask = todoService.createTask(todoItem);
        TodoItemResponseDTO createdTaskDTO = convertToResponseDTO(createdTask);

        // Return response with the created task
        ApiResponse<TodoItemResponseDTO> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Task created successfully",
                createdTaskDTO
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update an existing task by its ID.
     *
     * @param id            the ID of the task to update
     * @param updateRequest the request body containing updated fields
     * @return the updated task as a response DTO
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing task", response = ApiResponse.class)
    public ResponseEntity<ApiResponse<TodoItemResponseDTO>> updateTask(
            @ApiParam(value = "ID of the task to update", required = true) @PathVariable Long id,
            @Valid @RequestBody TodoItemUpdateRequestDTO updateRequest) {

        // Fetch the existing task
        TodoItem existingTask = todoService.getTaskById(id);

        // Handle task not found
        if (existingTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Task not found", null));
        }

        // Validate the due date if it's provided (not null or empty)
        if (updateRequest.getDueDate() != null && !updateRequest.getDueDate().isEmpty()) {
            LocalDate dueDate = null;
            try {
                // Parsing the string to LocalDate (yyyy-MM-dd format expected)
                dueDate = LocalDate.parse(updateRequest.getDueDate());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Invalid due date format, expected yyyy-MM-dd", null));
            }

            // Set the time to the current time
            LocalDateTime dueDateTime = LocalDateTime.of(dueDate, LocalDateTime.now().toLocalTime());

            // Validate the due date: ensure it is in the future or present
            if (dueDateTime.isBefore(LocalDateTime.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Due date must be in the future or present", null));
            }

            // Update the due date if it's valid
            existingTask.setDueDate(dueDateTime);
        }

        // Validate the priority if it's provided (not null)
        if (updateRequest.getPriority() != null) {
            // Validate the priority: ensure it is between 1 and 5
            if (updateRequest.getPriority() < 1 || updateRequest.getPriority() > 5) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Priority must be between 1 and 5", null));
            }

            // Update the priority
            existingTask.setPriority(updateRequest.getPriority());
        }

        // Update fields only if provided in the request and not empty
        Optional.ofNullable(updateRequest.getTitle())
                .filter(title -> !title.trim().isEmpty())  // Check if title is not null or empty
                .ifPresent(existingTask::setTitle);

        Optional.ofNullable(updateRequest.getDescription())
                .filter(description -> !description.trim().isEmpty())  // Check if description is not null or empty
                .ifPresent(existingTask::setDescription);

        Optional.ofNullable(updateRequest.getCompleted())
                .ifPresent(existingTask::setCompleted);

        // Save the updated task and convert it to a DTO
        TodoItem updatedTask = todoService.updateTask(id, existingTask);

        // Return response with the updated task
        ApiResponse<TodoItemResponseDTO> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Task updated successfully",
                convertToResponseDTO(updatedTask)
        );
        return ResponseEntity.ok(response);
    }



    /**
     * Delete a task by its ID.
     *
     * @param id the ID of the task to delete
     * @return a response confirming deletion
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a task")
    public ResponseEntity<ApiResponse<Void>> deleteTask(
            @ApiParam(value = "ID of the task to delete", required = true) @PathVariable Long id) {

        // Perform the delete operation
        todoService.deleteTask(id);

        // Return a confirmation response
        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.NO_CONTENT.value(),
                "Task deleted successfully",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
