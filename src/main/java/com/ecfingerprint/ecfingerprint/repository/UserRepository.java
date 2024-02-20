package com.ecfingerprint.ecfingerprint.repository;

import com.ecfingerprint.ecfingerprint.entity.Info;
import com.ecfingerprint.ecfingerprint.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Info, Long> {

    Info findByUsernameAndPassword(String username, String password);
}
