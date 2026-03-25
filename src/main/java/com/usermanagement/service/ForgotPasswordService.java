package com.usermanagement.service;

import com.usermanagement.requestDto.ChangePasswordDto;
import com.usermanagement.requestDto.ForgotPasswordRequestDto;
import com.usermanagement.requestDto.ResetPasswordRequestDto;
import com.usermanagement.requestDto.VerifyOtpRequestDto;

//Interface
public interface ForgotPasswordService {
 void sendOtp(ForgotPasswordRequestDto request);
 boolean verifyOtp(VerifyOtpRequestDto request);
 void resetPassword(ResetPasswordRequestDto request);
 
//ForgotPasswordService mein add karo
void changePassword(ChangePasswordDto request);
}
