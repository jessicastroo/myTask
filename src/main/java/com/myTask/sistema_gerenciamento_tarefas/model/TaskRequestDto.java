package com.myTask.sistema_gerenciamento_tarefas.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class TaskRequestDto {
    @NotNull(message = "O ID do usuário é obrigatório")
    private Long userId;

    @NotBlank(message = "O título da tarefa é obrigatório")
    private String titulo;

    @NotBlank(message = "A descrição da tarefa é obrigatória")
    private String descricao;

    @NotNull(message = "O status da tarefa é obrigatório")
    private TaskModel.TaskStatus status;

    public TaskRequestDto() {}
    private LocalDateTime dataCriacao;
    private LocalDateTime dataLimite;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TaskModel.TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskModel.TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDateTime dataLimite) {
        this.dataLimite = dataLimite;
    }
}
