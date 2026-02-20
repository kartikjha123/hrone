package com.usermanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.usermanagement.requestDto.CommonRequestDto;
import com.usermanagement.requestDto.ItemMasterRequestDto;
import com.usermanagement.responseDto.ItemMasterResposneDto;

public interface ItemService {
	
	public void addItemMaster(ItemMasterRequestDto itemMasterRequestDto);

	public Page<ItemMasterResposneDto> getAllItems(CommonRequestDto commonRequestDto);
	
	public Page<ItemMasterResposneDto> getAllItems(Pageable pageable);

	public void updateItemMaster(Long id, ItemMasterRequestDto itemMasterRequestDto);

	public void deleteItemMaster(Long id);

}
