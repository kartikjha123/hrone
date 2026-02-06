package com.usermanagement.requestDto;

public class ApproveLeaveRequestDto {

	private Long leaveApplicationId;
	private String comment;

	public ApproveLeaveRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApproveLeaveRequestDto(Long leaveApplicationId, String comment) {
		super();
		this.leaveApplicationId = leaveApplicationId;
		this.comment = comment;
	}

	public Long getLeaveApplicationId() {
		return leaveApplicationId;
	}

	public void setLeaveApplicationId(Long leaveApplicationId) {
		this.leaveApplicationId = leaveApplicationId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
