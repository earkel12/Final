package com.pm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class SessionController {
	
	@GetMapping("/checkSession")
    public ResponseEntity<Map<String, Object>> checkSession(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        String sid = (String) session.getAttribute("sid");

        if (sid == null) {
            response.put("loggedIn", false);
            return ResponseEntity.ok(response);
        }

        response.put("loggedIn", true);
        return ResponseEntity.ok(response);
    }
}