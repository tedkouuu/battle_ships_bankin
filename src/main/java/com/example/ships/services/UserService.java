package com.example.ships.services;

import com.example.ships.models.User;
import com.example.ships.models.dto.LoginDTO;
import com.example.ships.models.dto.RegisterDTO;
import com.example.ships.repositories.UserRepository;
import com.example.ships.session.LoggedUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LoggedUser userSession;

    public UserService(UserRepository userRepository, LoggedUser userSession) {
        this.userRepository = userRepository;
        this.userSession = userSession;
    }


    public boolean register(RegisterDTO registerDTO) {

        Optional<User> checkUserUsername = userRepository.
                findByUserName(registerDTO.getUserName());

        if (checkUserUsername.isPresent()) {
            return false;
        }

        Optional<User> checkUserEmail = userRepository.
                findByEmail(registerDTO.getEmail());

        if (checkUserEmail.isPresent()) {
            return false;
        }

        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            return false;
        }

        User user = new User().setUserName(registerDTO.getUserName()).setPassword(registerDTO.getPassword())
                .setEmail(registerDTO.getEmail()).setFullName(registerDTO.getFullName());

        this.userRepository.save(user);

        return true;
    }

    public boolean login(LoginDTO loginDTO) {

        Optional<User> user = this.userRepository.findByUserNameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());

        if (user.isEmpty()) {
            return false;
        }

        this.userSession.login(user.get());

        return true;

    }

    public void logout() {
        this.userSession.logout();
    }
}
















