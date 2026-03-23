package com.usermanagement.utility;

//com/usermanagement/util/OvertimeCalculator.java


public class OvertimeCalculator {

 public static final double SHIFT_HOURS = 8.0;

 /**
  * Overtime hours calculate karo
  * totalHours - 8 = overtime (agar > 8 hai toh)
  */
 public static double calculateOvertimeHours(double totalHours) {
     return totalHours > SHIFT_HOURS ? totalHours - SHIFT_HOURS : 0.0;
 }

 /**
  * Hourly rate nikalo monthly salary se
  * Formula: monthlySalary / 26 working days / 8 hours
  */
 public static double getHourlyRate(double monthlySalary) {
     return monthlySalary / 26 / SHIFT_HOURS;
 }

 /**
  * Overtime amount = overtimeHours * hourlyRate * 1.5 (1.5x overtime rate)
  */
 public static double calculateOvertimeAmount(double overtimeHours, double hourlyRate) {
     return overtimeHours * hourlyRate * 1.5;
 }
}