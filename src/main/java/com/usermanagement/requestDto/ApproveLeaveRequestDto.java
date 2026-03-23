package com.usermanagement.requestDto;

public class ApproveLeaveRequestDto {

	 private Long leaveApplicationId;
	    private String status;   // APPROVED / REJECTED
	    private String comment;

	    public Long getLeaveApplicationId() { return leaveApplicationId; }
	    public void setLeaveApplicationId(Long leaveApplicationId) { this.leaveApplicationId = leaveApplicationId; }
	    public String getStatus() { return status; }
	    public void setStatus(String status) { this.status = status; }
	    public String getComment() { return comment; }
	    public void setComment(String comment) { this.comment = comment; }
}
