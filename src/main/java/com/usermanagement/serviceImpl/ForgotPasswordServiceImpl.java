package com.usermanagement.serviceImpl;



import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usermanagement.config.EmailService;
import com.usermanagement.entity.PasswordResetOtp;
import com.usermanagement.entity.User;
import com.usermanagement.repository.PasswordResetOtpRepository;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.requestDto.ChangePasswordDto;
import com.usermanagement.requestDto.ForgotPasswordRequestDto;
import com.usermanagement.requestDto.ResetPasswordRequestDto;
import com.usermanagement.requestDto.VerifyOtpRequestDto;
import com.usermanagement.service.ForgotPasswordService;

//ServiceImpl
@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

 private static final Logger log = LoggerFactory.getLogger(ForgotPasswordServiceImpl.class);
 private static final int OTP_EXPIRY_MINUTES = 10;

 private final UserRepository userRepository;
 private final PasswordResetOtpRepository otpRepository;
 private final EmailService emailService;
 private final BCryptPasswordEncoder passwordEncoder;

 public ForgotPasswordServiceImpl(UserRepository userRepository,
                                   PasswordResetOtpRepository otpRepository,
                                   EmailService emailService,
                                   BCryptPasswordEncoder passwordEncoder) {
     this.userRepository   = userRepository;
     this.otpRepository    = otpRepository;
     this.emailService     = emailService;
     this.passwordEncoder  = passwordEncoder;
 }

 // ════════════════════════════════════════════════
 // STEP 1 — OTP Generate + Email bhejo
 // ════════════════════════════════════════════════
 @Override
 @Transactional
 public void sendOtp(ForgotPasswordRequestDto request) {
     log.info("OTP request — email: {}", request.getEmail());

     // ✅ User exist karta hai?
     User user = userRepository.findByEmail(request.getEmail())
             .orElseThrow(() -> new RuntimeException(
                 "No account found with this email:: " + request.getEmail()));

     // ✅ Purane OTP delete karo
     otpRepository.deleteByEmail(request.getEmail());

     // ✅ 6 digit OTP generate karo
     String otp = generateOtp();

     // ✅ OTP save karo
     PasswordResetOtp resetOtp = new PasswordResetOtp();
     resetOtp.setEmail(request.getEmail());
     resetOtp.setOtp(otp);
     resetOtp.setExpiryTime(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));
     resetOtp.setUsed(false);
     resetOtp.setCreatedAt(LocalDateTime.now());
     otpRepository.save(resetOtp);

     // ✅ Email bhejo
     emailService.sendOtpEmail(request.getEmail(), otp);

     log.info("OTP bhej diya — email: {}", request.getEmail());
 }

 // ════════════════════════════════════════════════
 // STEP 2 — OTP Verify karo (optional step)
 // ════════════════════════════════════════════════
 @Override
 public boolean verifyOtp(VerifyOtpRequestDto request) {
     PasswordResetOtp otpRecord = otpRepository
             .findTopByEmailAndIsUsedFalseOrderByCreatedAtDesc(request.getEmail())
             .orElseThrow(() -> new RuntimeException("OTP not found or already expired"));

     // ✅ Expiry check
     if (LocalDateTime.now().isAfter(otpRecord.getExpiryTime())) {
         throw new RuntimeException("OTP has expired. Please request a new one.");
     }

     // ✅ OTP match check
     if (!otpRecord.getOtp().equals(request.getOtp())) {
         throw new RuntimeException("Invalid OTP entered.");
     }

     return true;
 }

 // ════════════════════════════════════════════════
 // STEP 3 — New Password Set karo
 // ════════════════════════════════════════════════
 @Override
 @Transactional
 public void resetPassword(ResetPasswordRequestDto request) {
     log.info("Password reset — email: {}", request.getEmail());

     // ✅ Password match check
     if (!request.getNewPassword().equals(request.getConfirmPassword())) {
         throw new RuntimeException("New password and confirm password do not match.");
     }

     // ✅ Password length check
     if (request.getNewPassword().length() < 6) {
         throw new RuntimeException("Password must be at least 6 characters.");
     }

     // ✅ OTP verify karo
     PasswordResetOtp otpRecord = otpRepository
             .findTopByEmailAndIsUsedFalseOrderByCreatedAtDesc(request.getEmail())
             .orElseThrow(() -> new RuntimeException("OTP not found or already expired"));

     if (LocalDateTime.now().isAfter(otpRecord.getExpiryTime())) {
         throw new RuntimeException("OTP has expired. Please request a new one.");
     }

     if (!otpRecord.getOtp().equals(request.getOtp())) {
         throw new RuntimeException("Invalid OTP entered.");
     }

     // ✅ User ka password update karo
     User user = userRepository.findByEmail(request.getEmail())
             .orElseThrow(() -> new RuntimeException("User not found"));

     user.setPassword(passwordEncoder.encode(request.getNewPassword()));
     userRepository.save(user);

     // ✅ OTP used mark karo
     otpRecord.setUsed(true);
     otpRepository.save(otpRecord);

     log.info("Password reset ho gaya — email: {}", request.getEmail());
 }

 // ✅ 6 digit random OTP
 private String generateOtp() {
     int otp = (int) (Math.random() * 900000) + 100000;
     return String.valueOf(otp);
 }
 
 @Override
 @Transactional
 public void changePassword(ChangePasswordDto request) {

     // ✅ User dhundo
     User user = userRepository.findById(request.getUserId())
             .orElseThrow(() -> new RuntimeException("User not found"));

     // ✅ Old password check
     if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
         throw new RuntimeException("Current password is incorrect");
     }

     // ✅ New aur confirm password match check
     if (!request.getNewPassword().equals(request.getConfirmPassword())) {
         throw new RuntimeException("New password and confirm password do not match");
     }

     // ✅ New password same as old check
     if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
         throw new RuntimeException("New password cannot be same as current password");
     }

     // ✅ Password length check
     if (request.getNewPassword().length() < 6) {
         throw new RuntimeException("Password must be at least 6 characters");
     }

     // ✅ Password update karo
     user.setPassword(passwordEncoder.encode(request.getNewPassword()));
     userRepository.save(user);
 }
}
