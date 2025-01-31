package com.myTask.sistema_gerenciamento_tarefas.controller;
import com.myTask.sistema_gerenciamento_tarefas.model.TaskModel;
import com.myTask.sistema_gerenciamento_tarefas.model.TaskRequestDto;
import com.myTask.sistema_gerenciamento_tarefas.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskModel> createTask(@RequestBody TaskRequestDto task) {
        TaskModel createdTask = taskService.createTask(task);
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping
    public ResponseEntity<List<TaskModel>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskModel> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskModel> updateTask(@PathVariable Long id, @RequestBody TaskRequestDto task) {
        try {
            return ResponseEntity.ok(taskService.updateTask(id, task));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<TaskModel>> filterTasks(
                @RequestParam(required = false) String status,
            @RequestParam(required = false) Long userId) {

        List<TaskModel> tasks = taskService.filterTasks(status, userId);
        return ResponseEntity.ok(tasks);
    }
}

