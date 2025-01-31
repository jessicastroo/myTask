package com.myTask.sistema_gerenciamento_tarefas.controller;
import com.myTask.sistema_gerenciamento_tarefas.model.TaskModel;
import com.myTask.sistema_gerenciamento_tarefas.model.TaskRequestDto;
import com.myTask.sistema_gerenciamento_tarefas.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void testCreateTask() throws Exception {
        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setTitulo("Task 1");
        taskRequestDto.setDescricao("Description");
        taskRequestDto.setStatus(TaskModel.TaskStatus.PENDENTE);
        taskRequestDto.setUserId(1L);

        TaskModel taskModel = new TaskModel();
        taskModel.setId(1L);
        taskModel.setTitulo("Task 1");
        taskModel.setDescricao("Description");
        taskModel.setStatus(TaskModel.TaskStatus.PENDENTE);

        when(taskService.createTask(any(TaskRequestDto.class))).thenReturn(taskModel);

        mockMvc.perform(post("/api/tasks")
                        .contentType("application/json")
                        .content("{\"titulo\":\"Task 1\", \"descricao\":\"Description\", \"status\":\"PENDENTE\", \"userId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Task 1"))
                .andExpect(jsonPath("$.descricao").value("Description"))
                .andExpect(jsonPath("$.status").value("PENDENTE"));

        verify(taskService, times(1)).createTask(any(TaskRequestDto.class));
    }


    @Test
    public void testGetTaskByIdNotFound() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).getTaskById(1L);
    }
}
