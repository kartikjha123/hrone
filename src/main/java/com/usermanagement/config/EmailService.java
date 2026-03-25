package com.usermanagement.config;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String toEmail, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Password Reset OTP");
            message.setText(
            	    "Your Password Reset OTP is: " + otp + "\n\n" +
            	    "This OTP will expire in 10 minutes.\n\n" +
            	    "If you did not request this, please ignore this email.\n\n" +
            	    "HR Management System"
            	);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Email sending  error: " + e.getMessage());
        }
    }
}
