package com.usermanagement.biometric;
import com.usermanagement.entity.Attendance;
import com.usermanagement.entity.Employee;
import com.usermanagement.repository.AttendanceRepository;
import com.usermanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BiometricSyncService {

    private final EsslBiometricService esslService;
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public BiometricSyncService(EsslBiometricService esslService,
                                 AttendanceRepository attendanceRepository,
                                 EmployeeRepository employeeRepository) {
        this.esslService = esslService;
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    // ─────────────────────────────────────────────
    // Main Sync Method - Aaj ka data sync karo
    // ─────────────────────────────────────────────
    @Transactional
    public String syncTodayAttendance() {
        String today = LocalDate.now()
            .format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        return syncAttendanceForDate(today);
    }

    @Transactional
    public String syncAttendanceForDate(String date) {
        try {
            // eBioServer se logs lo
            String xmlResponse = esslService.getDeviceLogs(date);
            String rawData = esslService.parseResult(
                xmlResponse, "GetDeviceLogsResult"
            );

            if (rawData == null || rawData.isEmpty()) {
                return "No data from eBioServer for date: " + date;
            }

            // Parse karo aur save karo
            return processRawData(rawData, date);

        } catch (Exception e) {
            return "Sync failed: " + e.getMessage();
        }
    }

    // ─────────────────────────────────────────────
    // Raw Data Process karo
    // Format: "EmpCode,DateTime,DeviceName,Location,Direction;NextRecord"
    // ─────────────────────────────────────────────
    private String processRawData(String rawData, String dateStr) {

        // Records semicolon se split hote hain
        String[] records = rawData.split(";");

        // Employee wise punch times collect karo
        // Map<EmployeeCode, List<LocalTime>>
        Map<String, List<LocalTime>> punchMap = new HashMap<>();

        for (String record : records) {
            if (record.trim().isEmpty()) continue;

            // Fields comma se split hote hain
            String[] fields = record.trim().split(",");
            if (fields.length < 2) continue;

            try {
                String empCode = fields[0].trim();
                String dateTime = fields[1].trim(); // "MM/dd/yyyy HH:mm:ss"

                LocalTime punchTime = parsePunchTime(dateTime);
                if (punchTime != null) {
                    punchMap.computeIfAbsent(empCode, k -> new ArrayList<>())
                            .add(punchTime);
                }
            } catch (Exception e) {
                System.out.println("Record skip: " + record + " | " + e.getMessage());
            }
        }

        // Attendance save karo
        int saved = 0;
        LocalDate attendanceDate = parseDate(dateStr);

        for (Map.Entry<String, List<LocalTime>> entry : punchMap.entrySet()) {
            String empCode = entry.getKey();
            List<LocalTime> punches = entry.getValue();
            Collections.sort(punches); // time sort karo

            // Employee dhundo
            Optional<Employee> empOpt = employeeRepository
                .findByEmployeeCode(empCode);

            if (empOpt.isEmpty()) {
                System.out.println("Employee not found: " + empCode);
                continue;
            }

            Employee emp = empOpt.get();
            LocalTime firstPunch = punches.get(0);
            LocalTime lastPunch = punches.get(punches.size() - 1);

            // Existing record check karo
            Attendance attendance = attendanceRepository
                .findByEmployeeIdAndDate(emp.getId(), attendanceDate);

            if (attendance == null) {
                attendance = new Attendance();
                attendance.setEmployee(emp);
                attendance.setDate(attendanceDate);
                attendance.setApprovalStatus("PENDING");
            }

            // Punch In/Out set karo
            attendance.setPunchIn(firstPunch);

            if (punches.size() > 1) {
                attendance.setPunchOut(lastPunch);

                // Total hours calculate
                double totalHours = Duration.between(firstPunch, lastPunch)
                    .toMinutes() / 60.0;
                attendance.setTotalHours(
                    Math.round(totalHours * 100.0) / 100.0
                );

                // Overtime calculate (8 se zyada)
                double ot = totalHours > 8.0 ? totalHours - 8.0 : 0.0;
                attendance.setOvertimeHours(Math.round(ot * 100.0) / 100.0);

                // Status set karo
                attendance.setStatus(totalHours >= 4.0 ? "PP" : "HD");
            } else {
                // Sirf punch in hua - still PP (punch out pending)
                attendance.setStatus("PP");
            }

            attendanceRepository.save(attendance);
            saved++;
        }

        return "Sync complete. Records processed: " + saved;
    }

    // ─────────────────────────────────────────────
    // Time Parse karo
    // ─────────────────────────────────────────────
    private LocalTime parsePunchTime(String dateTimeStr) {
        try {
            // eBioServer format: "MM/dd/yyyy HH:mm:ss"
            String[] parts = dateTimeStr.split(" ");
            if (parts.length >= 2) {
                return LocalTime.parse(parts[1],
                    DateTimeFormatter.ofPattern("HH:mm:ss"));
            }
        } catch (Exception e) {
            System.out.println("Time parse error: " + dateTimeStr);
        }
        return null;
    }

    private LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr,
                DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        } catch (Exception e) {
            return LocalDate.now();
        }
    }
}
