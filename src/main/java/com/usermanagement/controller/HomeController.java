package com.usermanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HomeController {
	
	
	@PostMapping("/home")
	public ResponseEntity<?> home(HttpServletRequest request) {

	    String clientIp = request.getHeader("X-Client-IP");
	    String forwardedIp = request.getHeader("X-Forwarded-For");

	    System.out.println("Client IP: " + clientIp);
	    System.out.println("Forwarded IP: " + forwardedIp);

	    return ResponseEntity.ok("Hello from backend");
	}

}
