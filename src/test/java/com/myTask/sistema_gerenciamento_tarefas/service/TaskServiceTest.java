package com.myTask.sistema_gerenciamento_tarefas.service;
import com.myTask.sistema_gerenciamento_tarefas.exception.TaskException;
import com.myTask.sistema_gerenciamento_tarefas.model.*;
import com.myTask.sistema_gerenciamento_tarefas.repository.TaskRepository;
import com.myTask.sistema_gerenciamento_tarefas.repository.UserRepository;
import com.myTask.sistema_gerenciamento_tarefas.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;


public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private TaskModel task;
    private UserModel user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Setup mock data
        user = new UserModel();
        user.setId(1L);
        user.setName("User1");

        task = new TaskModel();
        task.setId(1L);
        task.setTitulo("Task 1");
        task.setDescricao("Task description");
        task.setStatus(TaskModel.TaskStatus.PENDENTE);
        task.setUserId(user);
    }

    @Test
    public void testCreateTask_Success() {

        TaskRequestDto taskRequest = new TaskRequestDto();
        taskRequest.setTitulo("Task 1");
        taskRequest.setDescricao("Task description");
        taskRequest.setStatus(TaskModel.TaskStatus.PENDENTE);
        taskRequest.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(TaskModel.class))).thenReturn(task);

        TaskModel createdTask = taskService.createTask(taskRequest);

        assertNotNull(createdTask);
        assertEquals("Task 1", createdTask.getTitulo());
        assertEquals(TaskModel.TaskStatus.PENDENTE, createdTask.getStatus());
    }

    @Test
    public void testCreateTask_UserNotFound() {

        TaskRequestDto taskRequest = new TaskRequestDto();
        taskRequest.setTitulo("Task 1");
        taskRequest.setDescricao("Task description");
        taskRequest.setStatus(TaskModel.TaskStatus.PENDENTE);
        taskRequest.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskException.class, () -> taskService.createTask(taskRequest));
    }

    @Test
    public void testUpdateTask_Success() {

        TaskRequestDto taskRequest = new TaskRequestDto();
        taskRequest.setTitulo("Updated Task");
        taskRequest.setDescricao("Updated description");
        taskRequest.setStatus(TaskModel.TaskStatus.EM_PROGRESSO);
        taskRequest.setUserId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(TaskModel.class))).thenReturn(task);

        TaskModel updatedTask = taskService.updateTask(1L, taskRequest);

        assertNotNull(updatedTask);
        assertEquals("Updated Task", updatedTask.getTitulo());
    }

    @Test
    public void testUpdateTask_TaskNotFound() {

        TaskRequestDto taskRequest = new TaskRequestDto();
        taskRequest.setTitulo("Updated Task");
        taskRequest.setDescricao("Updated description");
        taskRequest.setStatus(TaskModel.TaskStatus.EM_PROGRESSO);
        taskRequest.setUserId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskException.class, () -> taskService.updateTask(1L, taskRequest));
    }


    @Test
    public void testDeleteTask_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    public void testDeleteTask_TaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskException.class, () -> taskService.deleteTask(1L));
    }
}
