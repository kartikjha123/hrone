package com.usermanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.requestDto.ChangePasswordDto;
import com.usermanagement.requestDto.ForgotPasswordRequestDto;
import com.usermanagement.requestDto.ResetPasswordRequestDto;
import com.usermanagement.requestDto.VerifyOtpRequestDto;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.ForgotPasswordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Forgot Password", description = "OTP based password reset")
@RestController
@RequestMapping("/auth")
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    public ForgotPasswordController(ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }

    @Operation(summary = "Step 1 — Send OTP to email")
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @RequestBody ForgotPasswordRequestDto request) {
        forgotPasswordService.sendOtp(request);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(),
                "OTP has been sent to your email"));
    }

    @Operation(summary = "Step 2 — Verify OTP")
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(
            @RequestBody VerifyOtpRequestDto request) {
        boolean valid = forgotPasswordService.verifyOtp(request);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(),
                valid ? "OTP verified successfully" : "Invalid OTP"));
    }

    @Operation(summary = "Step 3 — Reset Password")
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequestDto request) {
        forgotPasswordService.resetPassword(request);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(),
                "Password has been reset successfully"));
    }
    
    @Operation(summary = "Change Password", description = "Logged-in user apna password change kare")
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordDto request) {
        forgotPasswordService.changePassword(request);
        return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(),
                "Password changed successfully"));
    }
}
