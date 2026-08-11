package com.umar.backend.user.service;

import com.umar.backend.common.util.CurrentUser;
import com.umar.backend.user.dto.UpdateProfileDTO;
import com.umar.backend.user.dto.UserResponseDTO;
import com.umar.backend.user.entity.User;
import com.umar.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final CurrentUser currentUser;
    private final PasswordEncoder passwordEncoder;

    User getCurrentUser() {
        return currentUser.getCurrentUser();
    }

    public UserResponseDTO update(UpdateProfileDTO updateProfileDTO) {

        User user = getCurrentUser();
        logger.info("Updating profile for user with id: {}", user.getId());
        if (updateProfileDTO.getEmail() != null
                && !updateProfileDTO.getEmail().isBlank()) {
            user.setEmail(updateProfileDTO.getEmail());
        }

        if (updateProfileDTO.getPassword() != null && !updateProfileDTO.getPassword().isBlank()) {
            user.setPassword(
                    passwordEncoder.encode(updateProfileDTO.getPassword()));
        }
        if (updateProfileDTO.getUsername() != null && !updateProfileDTO.getUsername().isBlank()) {
            user.setUsername(updateProfileDTO.getUsername());
        }

        userRepository.save(user);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        return userResponseDTO;
    }

    public UserResponseDTO getProfile() {

        User user = getCurrentUser();
        logger.info("Fetching profile for user with id: {}", user.getId());
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());

        return userResponseDTO;
    }

    public void deleteAccount() {

        User user = getCurrentUser();
        logger.info("Deleting account for user with id: {}", user.getId());
        userRepository.delete(user);
    }
}