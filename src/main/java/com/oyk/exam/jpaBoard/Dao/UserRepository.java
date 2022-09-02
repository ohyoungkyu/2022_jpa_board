package com.oyk.exam.jpaBoard.Dao;

import com.oyk.exam.jpaBoard.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
}
