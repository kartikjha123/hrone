package com.usermanagement.biometric;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AttendanceSyncScheduler {

    private final BiometricSyncService syncService;

    public AttendanceSyncScheduler(BiometricSyncService syncService) {
        this.syncService = syncService;
    }

//    // ✅ Har 15 minute mein auto sync
//    @Scheduled(fixedRate = 900000)
//    public void autoSync() {
//        System.out.println("⏰ Auto sync start...");
//        String result = syncService.syncTodayAttendance();
//        System.out.println("✅ " + result);
//    }
//
//    // ✅ Raat 11:59 PM pe full day sync
//    @Scheduled(cron = "0 59 23 * * ?")
//    public void nightSync() {
//        System.out.println("🌙 Night sync...");
//        syncService.syncTodayAttendance();
//    }
}