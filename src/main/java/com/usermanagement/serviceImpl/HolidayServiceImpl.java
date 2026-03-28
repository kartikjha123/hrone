package com.usermanagement.serviceImpl;

import com.usermanagement.entity.Holiday;
import com.usermanagement.repository.HolidayRepository;
import com.usermanagement.requestDto.HolidayRequestDto;
import com.usermanagement.responseDto.HolidayResponseDto;
import com.usermanagement.service.HolidayService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HolidayServiceImpl implements HolidayService {

	private final HolidayRepository holidayRepository;

	public HolidayServiceImpl(HolidayRepository holidayRepository) {
		this.holidayRepository = holidayRepository;
	}

	@Override
	public HolidayResponseDto addHoliday(HolidayRequestDto dto) {
		Holiday holiday = new Holiday();

		holiday.setName(dto.getName());
		holiday.setDate(dto.getDate());
		holiday.setType(dto.getType().toUpperCase());
		holiday.setDescription(dto.getDescription());
		holiday.setState(dto.getState());
		holiday.setActive(dto.isActive());

		return toDto(holidayRepository.save(holiday));
	}

	@Override
	public HolidayResponseDto updateHoliday(Long id, HolidayRequestDto dto) {
		Holiday holiday = holidayRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Holiday not found with id: " + id));

		holiday.setName(dto.getName());
		holiday.setDate(dto.getDate());
		holiday.setType(dto.getType().toUpperCase());
		holiday.setDescription(dto.getDescription());
		holiday.setState(dto.getState());
		holiday.setActive(dto.isActive());

		return toDto(holidayRepository.save(holiday));
	}

	@Override
	public void deleteHoliday(Long id) {
		Holiday holiday = holidayRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Holiday not found with id: " + id));
		holidayRepository.delete(holiday);
	}

	@Override
	public HolidayResponseDto getById(Long id) {
		Holiday holiday = holidayRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Holiday not found with id: " + id));
		return toDto(holiday);
	}

	@Override
	public List<HolidayResponseDto> getAllHolidays(Integer year, String type, String state) {
		List<Holiday> holidays;

		if (year != null && type != null) {
			holidays = holidayRepository.findByYearAndType(year, type);
		} else if (year != null) {
			holidays = holidayRepository.findByYear(year);
		} else if (type != null) {
			holidays = holidayRepository.findByTypeIgnoreCaseAndActiveTrue(type);
		} else if (state != null) {
			holidays = holidayRepository.findByStateIgnoreCaseAndActiveTrue(state);
		} else {
			holidays = holidayRepository.findAll();
		}

		return holidays.stream().map(this::toDto).collect(Collectors.toList());
	}

	@Override
	public List<HolidayResponseDto> getUpcomingHolidays() {
		return holidayRepository.findUpcoming(LocalDate.now()).stream().map(this::toDto).collect(Collectors.toList());
	}

	// ✅ Entity → DTO mapper (aapke DashboardServiceImpl style ke according)
	private HolidayResponseDto toDto(Holiday h) {
		HolidayResponseDto dto = new HolidayResponseDto();
		dto.setId(h.getId());
		dto.setName(h.getName());
		dto.setDate(h.getDate());
		dto.setType(h.getType());
		dto.setDescription(h.getDescription());
		dto.setState(h.getState());
		dto.setActive(h.isActive());
		return dto;
	}
	
	@Override
	public List<HolidayResponseDto> getCurrentMonthHolidays() {
	    LocalDate now = LocalDate.now();
	    return holidayRepository.findByMonthAndYear(now.getMonthValue(), now.getYear())
	            .stream()
	            .map(this::toDto)
	            .collect(Collectors.toList());
	}
}
