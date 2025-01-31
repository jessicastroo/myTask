package com.myTask.sistema_gerenciamento_tarefas.service;
import com.myTask.sistema_gerenciamento_tarefas.exception.TaskException;
import com.myTask.sistema_gerenciamento_tarefas.model.TaskModel;
import com.myTask.sistema_gerenciamento_tarefas.model.TaskRequestDto;
import com.myTask.sistema_gerenciamento_tarefas.model.UserModel;
import com.myTask.sistema_gerenciamento_tarefas.repository.TaskRepository;
import com.myTask.sistema_gerenciamento_tarefas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;


    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }
    public TaskModel createTask(TaskRequestDto t) {

        UserModel user = validateUser(t.getUserId());

        TaskModel task = new TaskModel();
        task.setTitulo(t.getTitulo());
        task.setDescricao(t.getDescricao());
        task.setStatus(t.getStatus());
        task.setUserId(user);

        return taskRepository.save(task);

    }


    public List<TaskModel> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<TaskModel> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public TaskModel updateTask(Long id, TaskRequestDto updatedTask) {
        TaskModel existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskException("Tarefa não encontrada"));


        if (existingTask.getStatus() == TaskModel.TaskStatus.FINALIZADO) {
            throw new TaskException("Tarefas concluídas não podem ser editadas");
        }

        existingTask.setTitulo(updatedTask.getTitulo());
        existingTask.setDescricao(updatedTask.getDescricao());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setDataLimite(updatedTask.getDataLimite());

        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {
        TaskModel task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskException("Tarefa não encontrada"));
        taskRepository.delete(task);
    }

    public List<TaskModel> filterTasks(String status, Long userId) {
        TaskModel.TaskStatus st = TaskModel.TaskStatus.valueOf(status);

        if (status != null && userId != null) {
            return taskRepository.findByStatusAndUserId(st, userId);
        } else if (status != null) {
            return taskRepository.findByStatus(st);
        } else if (userId != null) {
            return taskRepository.findByUserId(userId);
        } else {
            return taskRepository.findAllTask();
        }
    }

    private UserModel validateUser(Long userId) {
       UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new TaskException("Usuário não encontrado"));

       return user;
    }
}
