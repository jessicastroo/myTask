package com.myTask.sistema_gerenciamento_tarefas.controller;

import com.myTask.sistema_gerenciamento_tarefas.exception.UserException;
import com.myTask.sistema_gerenciamento_tarefas.model.UserModel;
import com.myTask.sistema_gerenciamento_tarefas.model.UserRequestDto;
import com.myTask.sistema_gerenciamento_tarefas.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testCreateUser() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setName("John Doe");
        userRequestDto.setEmail("john.doe@example.com");

        UserModel userModel = new UserModel();
        userModel.setName("John Doe");
        userModel.setEmail("john.doe@example.com");

        // Mockando a chamada ao serviço
        when(userService.createUser(Mockito.any(UserRequestDto.class))).thenReturn(userModel);

        mockMvc.perform(post("/api/users")
                        .contentType("application/json")
                        .content("{ \"name\": \"John Doe\", \"email\": \"john.doe@example.com\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        UserModel userModel1 = new UserModel();
        userModel1.setName("John Doe");
        userModel1.setEmail("john.doe@example.com");

        UserModel userModel2 = new UserModel();
        userModel2.setName("Jane Doe");
        userModel2.setEmail("jane.doe@example.com");

        when(userService.getAllUsers()).thenReturn(List.of(userModel1, userModel2));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"));
    }

    @Test
    public void testGetUserById() throws Exception {
        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setName("John Doe");
        userModel.setEmail("john.doe@example.com");

        when(userService.getUserById(1L)).thenReturn(userModel);

        mockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testGetUserByIdNotFound() throws Exception {
        when(userService.getUserById(99L)).thenThrow(new UserException("Usuário não encontrado"));

        mockMvc.perform(get("/api/users/{id}", 99L))
                .andExpect(status().isNotFound());
    }
}
