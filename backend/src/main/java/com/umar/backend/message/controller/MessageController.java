package com.umar.backend.message.controller;

import com.umar.backend.message.dto.MessageResponseDTO;
import com.umar.backend.message.dto.SendMessageRequestDTO;
import com.umar.backend.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @MessageMapping("/meeting/{code}/message")
    @SendTo("/topic/meeting/{code}")
    public MessageResponseDTO sendMessage( @DestinationVariable String code, SendMessageRequestDTO request){
        return messageService.sendMessage(code, request);
    }

}
