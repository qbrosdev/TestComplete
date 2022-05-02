package com.qbros.testcomplete.controller;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Value
public class ErrorResp {

    String message;
    LocalDateTime timestamp = LocalDateTime.now();
    Map<String, Object> attributes = new HashMap<>();

    public ErrorResp addAttribute(String key, Object val) {
        attributes.put(key, val);
        return this;
    }

}
