package com.todolist.app.dto.request;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * DTO for creating a new TodoItem.
 * Validates input fields such as title, description, due date, and priority.
 */
public class TodoItemCreateRequestDTO {

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Due date is mandatory")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Due date must be in the format YYYY-MM-DD")
    private String dueDate;

    @NotNull(message = "Priority is mandatory")
    @Min(value = 1, message = "Priority must be at least 1")
    @Max(value = 5, message = "Priority must be at most 5")
    private Integer priority;

    // Default constructor for deserialization
    public TodoItemCreateRequestDTO() {}

    // Constructor for easy instantiation
    public TodoItemCreateRequestDTO(String title, String description, String dueDate, int priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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

    @AssertTrue(message = "Due date must be in the future or present")
    public boolean isDueDateValid() {
        if (dueDate != null && !dueDate.isEmpty()) {
            try {
                // Specify the expected date format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dueDateLocal = LocalDate.parse(dueDate, formatter);
                return !dueDateLocal.isBefore(LocalDate.now());
            } catch (DateTimeParseException e) {
                // Log the exception or handle it as needed
                return false; // Return false if parsing fails
            }
        }
        return true; // If dueDate is null or empty, consider it valid
    }
}
