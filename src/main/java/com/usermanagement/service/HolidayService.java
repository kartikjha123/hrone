package com.usermanagement.service;

import com.usermanagement.requestDto.HolidayRequestDto;
import com.usermanagement.responseDto.HolidayResponseDto;

import java.util.List;

public interface HolidayService {

	HolidayResponseDto addHoliday(HolidayRequestDto dto);

	HolidayResponseDto updateHoliday(Long id, HolidayRequestDto dto);

	void deleteHoliday(Long id);

	HolidayResponseDto getById(Long id);

	List<HolidayResponseDto> getAllHolidays(Integer year, String type, String state);

	List<HolidayResponseDto> getUpcomingHolidays();
	
	List<HolidayResponseDto> getCurrentMonthHolidays();
}
