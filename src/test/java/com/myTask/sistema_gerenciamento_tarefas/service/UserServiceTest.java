package com.myTask.sistema_gerenciamento_tarefas.service;
import com.myTask.sistema_gerenciamento_tarefas.exception.UserException;
import com.myTask.sistema_gerenciamento_tarefas.model.*;
import com.myTask.sistema_gerenciamento_tarefas.repository.TaskRepository;
import com.myTask.sistema_gerenciamento_tarefas.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private UserService userService;

    private UserModel user;
    private UserRequestDto userRequestDto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Setup mock data
        user = new UserModel();
        user.setId(1L);
        user.setName("User1");
        user.setEmail("user1@example.com");

        userRequestDto = new UserRequestDto("User1", "user1@example.com");
    }

    @Test
    public void testCreateUser_Success() {
        when(userRepository.findByEmail(userRequestDto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(UserModel.class))).thenReturn(user);

        UserModel createdUser = userService.createUser(userRequestDto);

        assertNotNull(createdUser);
        assertEquals("User1", createdUser.getName());
        assertEquals("user1@example.com", createdUser.getEmail());
    }

    @Test
    public void testCreateUser_EmailAlreadyExists() {
        when(userRepository.findByEmail(userRequestDto.getEmail())).thenReturn(Optional.of(user));

        assertThrows(UserException.class, () -> userService.createUser(userRequestDto));
    }

    @Test
    public void testUpdateUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.existsByUserIdAndStatusIn(1L, List.of("PENDENTE", "EM_PROGRESSO"))).thenReturn(false);
        when(userRepository.save(any(UserModel.class))).thenReturn(user);

        UserModel updatedUser = userService.updateUser(1L, userRequestDto);

        assertNotNull(updatedUser);
        assertEquals("User1", updatedUser.getName());
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> userService.updateUser(1L, userRequestDto));
    }

    @Test
    public void testDeleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(taskRepository.findByUserId(1L)).thenReturn(List.of());

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteUser_UserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(UserException.class, () -> userService.deleteUser(1L));
    }

    @Test
    public void testDeleteUser_HasTasks() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(taskRepository.findByUserId(1L)).thenReturn(List.of(new TaskModel()));

        assertThrows(UserException.class, () -> userService.deleteUser(1L));
    }
}
