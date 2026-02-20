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

import com.usermanagement.service.NotificationService;
import com.usermanagement.requestDto.BulkProductionRequestDto;
import java.util.stream.Collectors;

@Service
public class ProductionServiceImpl implements ProductionService {

	@Autowired
	private ItemMasterRepository itemMasterRepository;

	@Autowired
	private ProductionEntryRepository productionEntryRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private NotificationService notificationService;

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
		entry.setStatus("PENDING");
		productionEntryRepository.save(entry);

		// Notify Manager
		if (emp.getManager() != null) {
			notificationService.sendNotification(emp.getManager(), 
				"Daily Task Submitted", 
				emp.getFirstName() + " has submitted a production entry for approval.",
				"PROD_SUBMITTED");
		}
	}

	@Override
	public void addBulkProductionEntries(BulkProductionRequestDto bulkRequest) {
		Employee emp = employeeRepository.findById(bulkRequest.getEmployeeId())
				.orElseThrow(() -> new RuntimeException("Employee not found"));

		List<ProductionEntry> entries = bulkRequest.getItems().stream().map(itemDto -> {
			ItemMaster item = itemMasterRepository.findById(itemDto.getItemId())
					.orElseThrow(() -> new RuntimeException("Item not found: " + itemDto.getItemId()));
			ProductionEntry entry = new ProductionEntry();
			entry.setEmployee(emp);
			entry.setItem(item);
			entry.setQuantity(itemDto.getQuantity());
			entry.setAmount(item.getRate() * itemDto.getQuantity());
			entry.setWorkDate(bulkRequest.getWorkDate());
			entry.setRemarks(itemDto.getRemarks());
			entry.setStatus("PENDING");
			return entry;
		}).collect(Collectors.toList());

		productionEntryRepository.saveAll(entries);

		// Notify Manager
		if (emp.getManager() != null) {
			notificationService.sendNotification(emp.getManager(), 
				"Bulk Task Submitted", 
				emp.getFirstName() + " has submitted " + entries.size() + " production entries for approval.",
				"PROD_SUBMITTED");
		}
	}

	@Override
	public void approveProductionEntry(Long entryId, String status, String comments) {
		ProductionEntry entry = productionEntryRepository.findById(entryId)
				.orElseThrow(() -> new RuntimeException("Entry not found"));
		entry.setStatus(status);
		entry.setSupervisorComments(comments);
		productionEntryRepository.save(entry);

		// Notify Employee
		notificationService.sendNotification(entry.getEmployee(), 
			"Task " + status, 
			"Your production entry for " + entry.getWorkDate() + " has been " + status,
			"PROD_STATUS_UPDATE");
	}

	@Override
	public Page<ProductionEntryResponseDto> getAllProductionEntries(ProductionFilterRequestDto req) {
		Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
		// Basic implementation without full filtering logic for now
		return productionEntryRepository.findAll(pageable).map(this::mapToResponseDto);
	}

	@Override
	public void updateProductionEntry(Long id, ProductionEntryRequestDto dto) {
		ProductionEntry entry = productionEntryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Production entry not found with id: " + id));
		if (!"PENDING".equals(entry.getStatus())) {
			throw new RuntimeException("Only pending production entries can be updated");
		}
		ItemMaster item = itemMasterRepository.findById(dto.getItemId())
				.orElseThrow(() -> new RuntimeException("Item not found with id: " + dto.getItemId()));
		entry.setItem(item);
		entry.setQuantity(dto.getQuantity());
		entry.setAmount(item.getRate() * dto.getQuantity());
		productionEntryRepository.save(entry);
	}

	@Override
	public void deleteProductionEntry(Long id) {
		ProductionEntry entry = productionEntryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Production entry not found with id: " + id));
		if (!"PENDING".equals(entry.getStatus())) {
			throw new RuntimeException("Only pending production entries can be deleted");
		}
		productionEntryRepository.delete(entry);
	}

	private ProductionEntryResponseDto mapToResponseDto(ProductionEntry entry) {
		ProductionEntryResponseDto dto = new ProductionEntryResponseDto();
		dto.setProductionId(entry.getId());
		dto.setItemName(entry.getItem().getItemName());
		dto.setQuantity(entry.getQuantity());
		dto.setAmount(entry.getAmount());
		dto.setStatus(entry.getStatus());
		dto.setWorkDate(entry.getWorkDate());
		dto.setEmployeeName(entry.getEmployee().getFirstName() + " " + entry.getEmployee().getLastName());
		return dto;
	}
}
