package com.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.responseDto.LeaveBalanceResponseDTO;
import com.usermanagement.service.LeaveBalanceService;

@RestController
@RequestMapping("/api/leave-balance")
public class LeaveBalanceController {

    @Autowired
    private LeaveBalanceService leaveBalanceService;

   
    
    @GetMapping("/{employeeId}/{year}")
    public ResponseEntity<?> getLeaveBalance(  @PathVariable Long employeeId,
            @PathVariable int year)
    {
    	return new ResponseEntity( leaveBalanceService.getLeaveBalance(employeeId, year),HttpStatus.OK);
    }
   
}
