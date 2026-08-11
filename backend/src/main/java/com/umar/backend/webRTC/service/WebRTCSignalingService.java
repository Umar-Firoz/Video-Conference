package com.umar.backend.webRTC.service;

import com.umar.backend.webRTC.dto.WebRTCSignalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebRTCSignalingService {

    private final SimpMessagingTemplate messagingTemplate;

    public void forwardSignal(WebRTCSignalDTO signal) {

        messagingTemplate.convertAndSend(
                "/topic/meeting/"
                        + signal.getMeetingCode()
                        + "/webrtc/"
                        + signal.getReceiverId(),
                signal
        );
    }
}