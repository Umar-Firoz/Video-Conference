package com.umar.backend.common.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MeetingCodeGenerator {

    public String generateCode() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 6)
                .toUpperCase();
    }
}