package com.myTask.sistema_gerenciamento_tarefas.repository;
import com.myTask.sistema_gerenciamento_tarefas.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsById(Long id);
}
