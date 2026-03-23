package com.usermanagement.requestDto;

import java.util.List;

public class BulkApproveRequestDto {
    private List<Long> attendanceIds;
    private Long managerId;

    public List<Long> getAttendanceIds() { return attendanceIds; }
    public void setAttendanceIds(List<Long> attendanceIds) { this.attendanceIds = attendanceIds; }
    public Long getManagerId() { return managerId; }
    public void setManagerId(Long managerId) { this.managerId = managerId; }
}
