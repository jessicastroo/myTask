package com.myTask.sistema_gerenciamento_tarefas.repository;

import com.myTask.sistema_gerenciamento_tarefas.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskModel, Long> {
    @Query("SELECT t FROM TaskModel t WHERE t.titulo = :titulo")
    Optional<TaskModel> findByTitle(String titulo);

    @Query("SELECT t FROM TaskModel t WHERE t.user.id = :userId")
    List<TaskModel> findByUserId(Long userId);

    @Query("SELECT t FROM TaskModel t WHERE t.status = :status")
    List<TaskModel> findByStatus(TaskModel.TaskStatus status);

        @Query("SELECT t FROM TaskModel t WHERE t.status = :status AND t.user.id = :userId")
    List<TaskModel> findByStatusAndUserId(TaskModel.TaskStatus status, Long userId);

    @Query("SELECT t FROM TaskModel t")
    List<TaskModel> findAllTask();

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM TaskModel t WHERE t.user.id = :userId AND t.status IN :statuses")
    boolean existsByUserIdAndStatusIn(Long userId, List<String> statuses);

}
