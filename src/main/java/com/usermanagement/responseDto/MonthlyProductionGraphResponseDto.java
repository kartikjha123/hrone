package com.usermanagement.responseDto;



import java.util.List;

public class MonthlyProductionGraphResponseDto {

    private String month;
    private int totalDaysWithData;

    // Normal summary
    private long totalEntries;
    private int totalQuantity;
    private double totalAmount;
    private long totalApproved;
    private long totalPending;
    private long totalRejected;

    // ✅ Overtime month-level summary
    private long totalOvertimeEntries;    // pure month mein kitni OT entries
    private int totalOvertimeQuantity;    // pure month mein OT quantity
    private double totalOvertimeAmount;   // pure month mein OT amount

    // Graph data
    private List<DailyProductionGraphDto> dailyData;

    public MonthlyProductionGraphResponseDto() {}

    // Getters & Setters — normal fields
    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public int getTotalDaysWithData() { return totalDaysWithData; }
    public void setTotalDaysWithData(int totalDaysWithData) { this.totalDaysWithData = totalDaysWithData; }

    public long getTotalEntries() { return totalEntries; }
    public void setTotalEntries(long totalEntries) { this.totalEntries = totalEntries; }

    public int getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(int totalQuantity) { this.totalQuantity = totalQuantity; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public long getTotalApproved() { return totalApproved; }
    public void setTotalApproved(long totalApproved) { this.totalApproved = totalApproved; }

    public long getTotalPending() { return totalPending; }
    public void setTotalPending(long totalPending) { this.totalPending = totalPending; }

    public long getTotalRejected() { return totalRejected; }
    public void setTotalRejected(long totalRejected) { this.totalRejected = totalRejected; }

    // ✅ Overtime getters & setters
    public long getTotalOvertimeEntries() { return totalOvertimeEntries; }
    public void setTotalOvertimeEntries(long totalOvertimeEntries) { this.totalOvertimeEntries = totalOvertimeEntries; }

    public int getTotalOvertimeQuantity() { return totalOvertimeQuantity; }
    public void setTotalOvertimeQuantity(int totalOvertimeQuantity) { this.totalOvertimeQuantity = totalOvertimeQuantity; }

    public double getTotalOvertimeAmount() { return totalOvertimeAmount; }
    public void setTotalOvertimeAmount(double totalOvertimeAmount) { this.totalOvertimeAmount = totalOvertimeAmount; }

    public List<DailyProductionGraphDto> getDailyData() { return dailyData; }
    public void setDailyData(List<DailyProductionGraphDto> dailyData) { this.dailyData = dailyData; }
}