package com.umar.backend.message.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SendMessageRequestDTO {
    @NotBlank
    private String content;
}
