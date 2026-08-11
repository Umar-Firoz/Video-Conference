package com.umar.backend.user.controller;

import com.umar.backend.user.dto.UpdateProfileDTO;
import com.umar.backend.user.dto.UserResponseDTO;
import com.umar.backend.user.dto.UserSummaryDTO;
import com.umar.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getProfile() {
        return ResponseEntity.ok(userService.getProfile());
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseDTO> update(
            @RequestBody UpdateProfileDTO updateProfileDTO) {
        return ResponseEntity.ok(userService.update(updateProfileDTO));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteAccount() {
        userService.deleteAccount();
        return ResponseEntity.noContent().build();
    }
}
