package com.usermanagement.service;



import com.usermanagement.responseDto.ReportResponseDto;

public interface ReportService {
    ReportResponseDto getManagerReport(Long managerId, Integer month, Integer year);
    ReportResponseDto getSuperAdminReport(Integer month, Integer year);
}
