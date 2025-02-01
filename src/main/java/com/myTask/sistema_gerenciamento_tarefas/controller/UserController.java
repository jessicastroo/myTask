package com.myTask.sistema_gerenciamento_tarefas.controller;
import com.myTask.sistema_gerenciamento_tarefas.exception.UserException;
import com.myTask.sistema_gerenciamento_tarefas.model.UserModel;
import com.myTask.sistema_gerenciamento_tarefas.model.UserRequestDto;
import com.myTask.sistema_gerenciamento_tarefas.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Criar usuário", description = "Cria um novo usuário no sistema",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            })
    public ResponseEntity<UserModel> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserModel createdUser = userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }


    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
            })
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            })
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        try {
            UserModel user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            })
    public ResponseEntity<UserModel> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDto userDetails) {
        try {
            UserModel updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário", description = "Deleta um usuário pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            })
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

