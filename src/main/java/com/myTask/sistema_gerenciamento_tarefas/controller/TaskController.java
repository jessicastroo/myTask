package com.myTask.sistema_gerenciamento_tarefas.controller;
import com.myTask.sistema_gerenciamento_tarefas.model.TaskModel;
import com.myTask.sistema_gerenciamento_tarefas.model.TaskRequestDto;
import com.myTask.sistema_gerenciamento_tarefas.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Tarefas", description = "Endpoints para gerenciamento de tarefas")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    @Operation(summary = "Criar tarefa", description = "Cria uma nova tarefa no sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tarefa criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            })
    public ResponseEntity<TaskModel> createTask(@RequestBody TaskRequestDto task) {
        TaskModel createdTask = taskService.createTask(task);
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping
    @Operation(summary = "Listar todas as tarefas", description = "Retorna uma lista de todas as tarefas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso")
            })
    public ResponseEntity<List<TaskModel>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tarefa por ID", description = "Retorna uma tarefa específica pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tarefa encontrada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
            })
    public ResponseEntity<TaskModel> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tarefa", description = "Atualiza os dados de uma tarefa existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
            })
    public ResponseEntity<TaskModel> updateTask(@PathVariable Long id, @RequestBody TaskRequestDto task) {
        try {
            return ResponseEntity.ok(taskService.updateTask(id, task));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar tarefa", description = "Deleta uma tarefa pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
            })
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filter")
    @Operation(summary = "Filtrar tarefas", description = "Filtra tarefas com base no status e no ID do usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de tarefas filtradas retornada com sucesso")
            })
    public ResponseEntity<List<TaskModel>> filterTasks(
                @RequestParam(required = false) String status,
            @RequestParam(required = false) Long userId) {

        List<TaskModel> tasks = taskService.filterTasks(status, userId);
        return ResponseEntity.ok(tasks);
    }
}

