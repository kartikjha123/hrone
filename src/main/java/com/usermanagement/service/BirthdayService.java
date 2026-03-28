package com.usermanagement.service;

import java.util.List;

import com.usermanagement.responseDto.BirthdayResponseDto;

public interface BirthdayService {
	
	List<BirthdayResponseDto> getTodayBirthdays();
    List<BirthdayResponseDto> getUpcomingBirthdaysThisMonth();

}
