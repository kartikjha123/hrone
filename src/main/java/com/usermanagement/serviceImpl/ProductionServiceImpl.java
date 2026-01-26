package com.usermanagement.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.usermanagement.entity.Employee;
import com.usermanagement.entity.ItemMaster;
import com.usermanagement.entity.ProductionEntry;
import com.usermanagement.repository.EmployeeRepository;
import com.usermanagement.repository.ItemMasterRepository;
import com.usermanagement.repository.ProductionEntryRepository;
import com.usermanagement.requestDto.ProductionEntryRequestDto;
import com.usermanagement.requestDto.ProductionFilterRequestDto;
import com.usermanagement.responseDto.ProductionEntryResponseDto;
import com.usermanagement.service.ProductionService;

@Service
public class ProductionServiceImpl implements ProductionService {

	@Autowired
	private ItemMasterRepository itemMasterRepository;

	@Autowired
	private ProductionEntryRepository productionEntryRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void addProductionEntry(ProductionEntryRequestDto productionEntryRequestDto) {
		Employee emp = employeeRepository.findById(productionEntryRequestDto.getEmployeeId())
				.orElseThrow(() -> new RuntimeException("Employee not found"));
		ItemMaster item = itemMasterRepository.findById(productionEntryRequestDto.getItemId())
				.orElseThrow(() -> new RuntimeException("Item not found"));

		ProductionEntry entry = new ProductionEntry();
		entry.setEmployee(emp);
		entry.setItem(item);
		entry.setAmount(item.getRate() * productionEntryRequestDto.getQuantity());
		entry.setWorkDate(LocalDate.now());
		entry.setQuantity(productionEntryRequestDto.getQuantity());
		productionEntryRepository.save(entry);

	}

	@Override
	public Page<ProductionEntryResponseDto> getAllProductionEntries(
	        ProductionFilterRequestDto req) {

	    Pageable pageable = PageRequest.of(req.getPage(), req.getSize());

	    Page<ProductionEntry> entries =
	            productionEntryRepository.filterProductionEntries(
	                    req.getEmployeeId(),
	                    req.getItemId(),
	                    req.getFromDate(),
	                    req.getToDate(),
	                    pageable
	            );

	    return entries.map(pe -> {
	        ProductionEntryResponseDto dto = new ProductionEntryResponseDto();

	        dto.setProductionId(pe.getId());
	        dto.setEmployeeId(pe.getEmployee().getId());
	        dto.setEmployeeName(
	                pe.getEmployee().getFirstName() + " " + pe.getEmployee().getLastName()
	        );
	        dto.setEmployeeCode(pe.getEmployee().getEmployeeCode());

	        dto.setItemId(pe.getItem().getId());
	        dto.setItemName(pe.getItem().getItemName());
	        dto.setRate(pe.getItem().getRate());
	        dto.setUnit(pe.getItem().getUnit());

	        dto.setWorkDate(pe.getWorkDate());
	        dto.setQuantity(pe.getQuantity());
	        dto.setAmount(pe.getAmount());

	        return dto;
	    });
	}
}
