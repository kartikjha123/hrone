package com.usermanagement.serviceImpl;

import org.springframework.stereotype.Service;

import com.usermanagement.responseDto.DashboardResponseDto;
import com.usermanagement.service.DashboardService;



import com.usermanagement.entity.LeaveBalance;
import com.usermanagement.repository.*;
import com.usermanagement.responseDto.*;
import com.usermanagement.service.DashboardService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final AttendanceRepository attendanceRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;
    private final PayrollRepository payrollRepository;
    private final HolidayRepository holidayRepository;

    public DashboardServiceImpl(AttendanceRepository attendanceRepository,
                                 LeaveBalanceRepository leaveBalanceRepository,
                                 PayrollRepository payrollRepository,
                                 HolidayRepository holidayRepository) {
        this.attendanceRepository = attendanceRepository;
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.payrollRepository = payrollRepository;
        this.holidayRepository = holidayRepository; 
    }

    @Override
    public DashboardResponseDto getDashboard(Long empId) {

        LocalDate now = LocalDate.now();
        DashboardResponseDto dto = new DashboardResponseDto();

        // 1. ✅ Attendance count
        int count = attendanceRepository.findByEmployeeIdAndDateBetween(
            empId, now.withDayOfMonth(1), now
        ).size();
        dto.setMonthlyAttendanceCount(count);

        // 2. ✅ Leave Balances - Flat DTO mein convert karo
        List<LeaveBalance> balances = leaveBalanceRepository.findByEmployeeId(empId);
        List<LeaveBalanceDto> leaveBalanceDtos = balances.stream().map(lb -> {
            LeaveBalanceDto lbDto = new LeaveBalanceDto();
            lbDto.setId(lb.getId());
            lbDto.setYear(lb.getYear());
            lbDto.setTotalLeaves(lb.getTotalLeaves());
            lbDto.setUsedLeaves(lb.getUsedLeaves());
            lbDto.setRemainingLeaves(lb.getRemainingLeaves());
            lbDto.setCarryForwarded(lb.getCarryForwarded());
            // ✅ LeaveType se sirf naam lo
            if (lb.getLeaveType() != null) {
                lbDto.setLeaveTypeName(lb.getLeaveType().getName());
            }
            return lbDto;
        }).collect(Collectors.toList());
        dto.setLeaveBalances(leaveBalanceDtos);

        // 3. ✅ Last Payslip
        payrollRepository.findByMonthAndYear(
            now.minusMonths(1).getMonthValue(),
            now.minusMonths(1).getYear()
        ).stream()
         .filter(p -> p.getEmployee().getId().equals(empId))
         .findFirst()
         .ifPresent(p -> dto.setLastPayslip(p));

//        // 4. ✅ Upcoming Holidays
//        dto.setUpcomingHolidays(List.of(
//            Map.of("date", "2026-01-26", "name", "Republic Day"),
//            Map.of("date", "2026-08-15", "name", "Independence Day")
//        ));
        
     // ✅ BAAD MEIN (DB se real data)
     // 4. ✅ Upcoming Holidays — DB se real data (hardcoded hataya)
        List<Map<String, String>> upcomingHolidays = holidayRepository
            .findUpcoming(LocalDate.now())
            .stream()
            .map(h -> Map.of("date", h.getDate().toString(), "name", h.getName()))
            .collect(Collectors.toList());
        dto.setUpcomingHolidays(upcomingHolidays);

        return dto;
    }
    }
