package com.usermanagement.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessageDto {
	
	private Integer status;
	private String message;
	private Integer count;
	private Object data;

	public ResponseMessageDto() {
	}

	public ResponseMessageDto(Integer status, String message, Integer count, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.count = count;
		this.data = data;
	}

	public ResponseMessageDto(Integer status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public ResponseMessageDto(Integer status, String message) {

		this.status = status;
		this.message = message;
	}

	public ResponseMessageDto(String message, Object data) {
		this.message = message;
		this.data = data;
	}
    
	public ResponseMessageDto(Integer status, Object data) {
		this.status = status;
		this.data = data;
	}
	public ResponseMessageDto(String message2) {
		this.message = message2;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	

}
