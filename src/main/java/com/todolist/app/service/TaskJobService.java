package com.todolist.app.service;

import com.todolist.app.model.TodoItem;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskJobService {

    private final TodoService todoService;

    @Autowired
    public TaskJobService(TodoService todoService) {
        this.todoService = todoService;
    }

    // Scheduled job to run every hour (you can adjust the frequency)
    @Scheduled(cron = "0 0 * * * *")  // Runs at the top of every hour
    public void markDueTasksAsCompleted() {
        // Fetch all tasks with dueDate not null
        List<TodoItem> tasks = todoService.getAllTasks();

        for (TodoItem task : tasks) {
            // Check if the task is due (dueDate is not null and is in the past or present)
            if (task.getDueDate() != null && task.getDueDate().isBefore(LocalDateTime.now()) || task.getDueDate().isEqual(LocalDateTime.now())) {
                if (!task.isCompleted()) {
                    // Mark the task as completed
                    task.setCompleted(true);

                    // Save the task after updating the completed status
                    todoService.updateTask(task.getId(), task);
                    System.out.println("Task " + task.getId() + " marked as completed");
                }
            }
        }
    }
}

