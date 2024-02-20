package com.ecfingerprint.ecfingerprint.service;

import com.ecfingerprint.ecfingerprint.entity.Info;
import com.ecfingerprint.ecfingerprint.entity.User;
import com.ecfingerprint.ecfingerprint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public boolean isValidUser(String username, String password) {
        Info info = userRepository.findByUsernameAndPassword(username, password);
        return info != null;
    }
}
