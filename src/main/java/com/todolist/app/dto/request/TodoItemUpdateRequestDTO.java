package com.todolist.app.dto.request;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * DTO for updating an existing TodoItem.
 * Supports partial updates with optional fields.
 */
public class TodoItemUpdateRequestDTO {

    private String title;
    private String description;
    private String dueDate;
    private Integer priority; // Allows null to indicate no update
    private Boolean completed; // Allows null to indicate no update

    // Default constructor for deserialization
    public TodoItemUpdateRequestDTO() {}

    // Constructor for easy instantiation
    public TodoItemUpdateRequestDTO(String title, String description, String dueDate, Integer priority, Boolean completed) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completed = completed;
    }

    // Getters and Setters for encapsulation
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    /**
     * Converts the dueDate string to LocalDateTime.
     *
     * @return LocalDateTime object representing the due date.
     */
    public LocalDate getParsedDueDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(this.dueDate, formatter);
    }
}
