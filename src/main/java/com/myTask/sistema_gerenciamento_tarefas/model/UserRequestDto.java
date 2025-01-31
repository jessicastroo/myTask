package com.myTask.sistema_gerenciamento_tarefas.model;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRequestDto {

    @NotBlank(message = "Nome não pode estar em branco")
    private String name;

    @Email(message = "Email deve ser válido")
    @NotBlank(message = "Email não pode estar em branco")
    private String email;

    public UserRequestDto() {}

    public UserRequestDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}