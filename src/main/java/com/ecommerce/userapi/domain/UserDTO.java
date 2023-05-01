package com.ecommerce.userapi.domain;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record UserDTO(
        @NotBlank(message = "Nome é obrigatório")
        String name,
        @NotBlank(message = "CPF é obrigatório")
        String cpf,
        String address,
        @NotBlank(message = "E-mail é obrigatório")
        String email,
        String telephone,
        LocalDateTime createdAt
) {

        public UserDTO(User user) {
                this(
                        user.getName(),
                        user.getCpf(),
                        user.getAddress(),
                        user.getEmail(),
                        user.getTelephone(),
                        LocalDateTime.now()
                );
        }
}
