package com.ecommerce.userapi.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cpf;
    private String address;
    private String email;
    private String telephone;
    private LocalDateTime createdAt;
    private Boolean active;

    public User(UserDTO userDTO) {

        this.name = userDTO.name();
        this.cpf = userDTO.cpf();
        this.address = userDTO.address();
        this.email = userDTO.email();
        this.telephone = userDTO.telephone();
        this.createdAt = LocalDateTime.now();
        this.active = true;

    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void delete() {
        this.active = false;
    }

    public void update(UserDTO userDTO) {

        if (userDTO.email() != null && !this.getEmail().equals(userDTO.email())) {
            this.setEmail(userDTO.email());
        }
        if (userDTO.telephone() != null && !this.getTelephone().equals(userDTO.telephone())) {
            this.setTelephone(userDTO.telephone());
        }
        if (userDTO.address() != null && !this.getAddress().equals(userDTO.address())) {
            this.setAddress(userDTO.address());
        }

    }
}
