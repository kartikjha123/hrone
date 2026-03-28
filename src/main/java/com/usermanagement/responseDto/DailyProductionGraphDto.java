package com.usermanagement.responseDto;



public class DailyProductionGraphDto {

    private String date;
    private int totalEntries;
    private int totalQuantity;
    private double totalAmount;
    private int approvedCount;
    private int pendingCount;
    private int rejectedCount;

    // ✅ Overtime fields add karo
    private int overtimeEntries;      // us din kitni overtime entries
    private int overtimeQuantity;     // overtime mein kitna quantity
    private double overtimeAmount;    // overtime mein kitna amount

    // Constructor
    public DailyProductionGraphDto(String date, int totalEntries, int totalQuantity,
            double totalAmount, int approvedCount, int pendingCount, int rejectedCount,
            int overtimeEntries, int overtimeQuantity, double overtimeAmount) {
        this.date = date;
        this.totalEntries = totalEntries;
        this.totalQuantity = totalQuantity;
        this.totalAmount = totalAmount;
        this.approvedCount = approvedCount;
        this.pendingCount = pendingCount;
        this.rejectedCount = rejectedCount;
        this.overtimeEntries = overtimeEntries;
        this.overtimeQuantity = overtimeQuantity;
        this.overtimeAmount = overtimeAmount;
    }

    // Getters & Setters
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public int getTotalEntries() { return totalEntries; }
    public void setTotalEntries(int totalEntries) { this.totalEntries = totalEntries; }

    public int getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(int totalQuantity) { this.totalQuantity = totalQuantity; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public int getApprovedCount() { return approvedCount; }
    public void setApprovedCount(int approvedCount) { this.approvedCount = approvedCount; }

    public int getPendingCount() { return pendingCount; }
    public void setPendingCount(int pendingCount) { this.pendingCount = pendingCount; }

    public int getRejectedCount() { return rejectedCount; }
    public void setRejectedCount(int rejectedCount) { this.rejectedCount = rejectedCount; }

    public int getOvertimeEntries() { return overtimeEntries; }
    public void setOvertimeEntries(int overtimeEntries) { this.overtimeEntries = overtimeEntries; }

    public int getOvertimeQuantity() { return overtimeQuantity; }
    public void setOvertimeQuantity(int overtimeQuantity) { this.overtimeQuantity = overtimeQuantity; }

    public double getOvertimeAmount() { return overtimeAmount; }
    public void setOvertimeAmount(double overtimeAmount) { this.overtimeAmount = overtimeAmount; }
}