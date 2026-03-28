package com.usermanagement.responseDto;



import java.util.List;

public class TodayAttendanceSummaryDto {

    // Summary counts
    private int totalEmployees;
    private int presentCount;
    private int absentCount;
    private int notPunchedIn;
    private int punchedOutCount;

    // Paginated list
    private List<TodayAttendanceRecordDto> records;
    private int currentPage;
    private int totalPages;
    private long totalRecords;

    // Getters & Setters
    public int getTotalEmployees() { return totalEmployees; }
    public void setTotalEmployees(int totalEmployees) { this.totalEmployees = totalEmployees; }

    public int getPresentCount() { return presentCount; }
    public void setPresentCount(int presentCount) { this.presentCount = presentCount; }

    public int getAbsentCount() { return absentCount; }
    public void setAbsentCount(int absentCount) { this.absentCount = absentCount; }

    public int getNotPunchedIn() { return notPunchedIn; }
    public void setNotPunchedIn(int notPunchedIn) { this.notPunchedIn = notPunchedIn; }

    public int getPunchedOutCount() { return punchedOutCount; }
    public void setPunchedOutCount(int punchedOutCount) { this.punchedOutCount = punchedOutCount; }

    public List<TodayAttendanceRecordDto> getRecords() { return records; }
    public void setRecords(List<TodayAttendanceRecordDto> records) { this.records = records; }

    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public long getTotalRecords() { return totalRecords; }
    public void setTotalRecords(long totalRecords) { this.totalRecords = totalRecords; }
}