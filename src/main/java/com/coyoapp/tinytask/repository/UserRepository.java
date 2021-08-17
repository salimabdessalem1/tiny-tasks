package com.coyoapp.tinytask.repository;

import com.coyoapp.tinytask.domain.Task;
import com.coyoapp.tinytask.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
  List<User> findAll();
}
