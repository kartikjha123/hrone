package com.usermanagement.service;

import java.util.List;

import com.usermanagement.requestDto.ARApproveDto;
import com.usermanagement.requestDto.ARRequestDto;
import com.usermanagement.responseDto.ARBalanceDto;
import com.usermanagement.responseDto.ARResponseDto;

public interface AttendanceRegularizationService {
    ARResponseDto applyAR(Long employeeId, ARRequestDto request);
    void approveAR(Long arId, ARApproveDto dto);
   // List<ARResponseDto> getEmployeeARHistory(Long employeeId);
    List<ARResponseDto> getPendingARForManager(Long managerId);
    ARResponseDto updateAR(Long id, ARRequestDto request);
    void deleteAR(Long id);
    ARBalanceDto getARBalance(Long employeeId);
    
 // ✅ New
    List<ARResponseDto> getEmployeeARHistory(Long employeeId, Integer month, Integer year);
}