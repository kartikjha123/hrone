package com.usermanagement.serviceImpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.usermanagement.entity.ItemMaster;
import com.usermanagement.repository.ItemMasterRepository;
import com.usermanagement.requestDto.CommonRequestDto;
import com.usermanagement.requestDto.ItemMasterRequestDto;
import com.usermanagement.responseDto.ItemMasterResposneDto;
import com.usermanagement.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMasterRepository itemMasterRepository;

	@Override
	public void addItemMaster(ItemMasterRequestDto itemMasterRequestDto) {
		// TODO Auto-generated method stub
		ItemMaster itemMaster = new ItemMaster();
		itemMaster.setItemName(itemMasterRequestDto.getItemName());
		itemMaster.setRate(itemMasterRequestDto.getRate());
		itemMaster.setUnit(itemMasterRequestDto.getUnit());
		// itemMaster.setCreatedBy(getCurrentUserProfile().getUsername());
		itemMaster.setCreatedDate(LocalDate.now());

		itemMasterRepository.save(itemMaster);
	}

	@Override
	public Page<ItemMasterResposneDto> getAllItems(CommonRequestDto commonRequestDto) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(commonRequestDto.getPage(), commonRequestDto.getSize());

		Page<ItemMaster> page = itemMasterRepository.findAll(pageable);

		return page.map(item -> new ItemMasterResposneDto(item.getId(), item.getItemName(), item.getRate(), // NOT
																											// getClass()
				item.getUnit()));
	}

	@Override
	public Page<ItemMasterResposneDto> getAllItems(Pageable pageable) {
		// TODO Auto-generated method stub
		Page<ItemMaster> page = itemMasterRepository.findAll(pageable);
		return page.map(item -> new ItemMasterResposneDto(item.getId(), item.getItemName(), item.getRate(), // NOT
				// getClass()
				item.getUnit()));
	}
}
