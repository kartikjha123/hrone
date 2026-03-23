package com.usermanagement.requestDto;

public class CommonRequestDto {

	private String search;
	private Integer page;
	private Integer size;
	
	public CommonRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommonRequestDto(String search,Integer page, Integer size) {
		super();
		this.search=search;
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	
	
	

}
