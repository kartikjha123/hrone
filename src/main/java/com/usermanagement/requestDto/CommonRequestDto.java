package com.usermanagement.requestDto;

public class CommonRequestDto {
	
	private Integer page;
	private Integer size;
	
	public CommonRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommonRequestDto(Integer page, Integer size) {
		super();
		this.page = page;
		this.size = size;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
	
	
	

}
