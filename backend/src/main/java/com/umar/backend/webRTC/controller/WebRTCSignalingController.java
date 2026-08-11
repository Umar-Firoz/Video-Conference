package com.umar.backend.webRTC.controller;

import com.umar.backend.webRTC.dto.WebRTCSignalDTO;
import com.umar.backend.webRTC.service.WebRTCSignalingService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebRTCSignalingController {

    private final WebRTCSignalingService signalingService;

    @MessageMapping("/webrtc")
    public void signal(WebRTCSignalDTO signal) {

        signalingService.forwardSignal(signal);
    }
}