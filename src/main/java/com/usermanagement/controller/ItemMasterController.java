package com.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.requestDto.CommonRequestDto;
import com.usermanagement.requestDto.ItemMasterRequestDto;
import com.usermanagement.responseDto.ItemMasterResposneDto;
import com.usermanagement.responseDto.ResponseMessageDto;
import com.usermanagement.service.ItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Item Master Management", description = "APIs for Item Master Management")
@RestController
@RequestMapping("/items")
public class ItemMasterController {

	@Autowired
	private ItemService itemService;

	@Operation(summary = "Add Item Master", description = "Create Item Master")
	@PostMapping("/add")
	public ResponseEntity<?> addItemMaster(@RequestBody ItemMasterRequestDto itemMasterRequestDto) {
		itemService.addItemMaster(itemMasterRequestDto);
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Item Created Sucessfully"));
	}

	@Operation(summary = "Get All Items", description = "Fetch list of all items")
	@PostMapping("/all")
	public ResponseEntity<?> getAllItems(@RequestBody CommonRequestDto requestDto) {

		requestDto.setPage(requestDto.getPage() == null ? 0 : requestDto.getPage());
		requestDto.setSize(requestDto.getSize() == null ? 10 : requestDto.getSize());

		Page<ItemMasterResposneDto> pageResult = itemService.getAllItems(requestDto);
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Item Fetched Sucessfully", pageResult));
	}

	@Operation(summary = "Update Item Master", description = "Updates an existing item")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateItemMaster(@PathVariable Long id,
			@RequestBody ItemMasterRequestDto itemMasterRequestDto) {
		itemService.updateItemMaster(id, itemMasterRequestDto);
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Item Updated Successfully"));
	}

	@Operation(summary = "Delete Item Master", description = "Deletes an item")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteItemMaster(@PathVariable Long id) {
		itemService.deleteItemMaster(id);
		return ResponseEntity.ok(new ResponseMessageDto(HttpStatus.OK.value(), "Item Deleted Successfully"));
	}
	
	

}
