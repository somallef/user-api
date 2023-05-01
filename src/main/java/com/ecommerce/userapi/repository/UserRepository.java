package com.ecommerce.userapi.repository;

import com.ecommerce.userapi.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByCpf(String cpf);
    Page<User> findByNameLike(Pageable pageable, String name);
    Page<User> findAllByActiveTrue(Pageable pageable);
}
