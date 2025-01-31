package com.myTask.sistema_gerenciamento_tarefas.service;

import com.myTask.sistema_gerenciamento_tarefas.model.TaskModel;
import com.myTask.sistema_gerenciamento_tarefas.repository.TaskRepository;
import com.myTask.sistema_gerenciamento_tarefas.repository.UserRepository;
import com.myTask.sistema_gerenciamento_tarefas.exception.UserException;
import com.myTask.sistema_gerenciamento_tarefas.model.UserModel;
import com.myTask.sistema_gerenciamento_tarefas.model.UserRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public UserService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public UserModel createUser(UserRequestDto userRequestDto) {
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new UserException("Email já cadastrado");
        }

        // Convertendo DTO para Model
        UserModel user = new UserModel();
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());

        return userRepository.save(user);
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserModel getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException("Usuário não encontrado"));
    }

    public UserModel updateUser(Long id, UserRequestDto userDetails) {
        Optional<UserModel> findUser = userRepository.findById(id);

        if (findUser.isEmpty()) {
            throw new UserException("Usuário não encontrado");
        }

        // se o usuário possui tarefas em progresso ou pendentes
        boolean hasOpenTasks = taskRepository.existsByUserIdAndStatusIn(id, List.of("PENDENTE", "EM_PROGRESSO"));
        if (hasOpenTasks) {
            throw new UserException("Usuário possui tarefas pendentes ou em progresso e não pode ser atualizado.");
        }

        UserModel user = findUser.get();
        user.setEmail(userDetails.getEmail());
        user.setName(userDetails.getName());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserException("Usuário não encontrado");
        }

        // se o usuário possui tarefas antes de excluir
        List<TaskModel> task = taskRepository.findByUserId(id);
        if (!task.isEmpty()) {
            throw new UserException("Usuário possui tarefas associadas e não pode ser excluído.");
        }

        userRepository.deleteById(id);

        return;
    }
}
