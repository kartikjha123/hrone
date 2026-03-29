package com.usermanagement.biometric;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/biometric")
public class BiometricSyncController {

//    private final BiometricSyncService syncService;
//
//    public BiometricSyncController(BiometricSyncService syncService) {
//        this.syncService = syncService;
//    }
//
//    // Manual sync - aaj ka
//    @PostMapping("/sync/today")
//    public ResponseEntity<String> syncToday() {
//        return ResponseEntity.ok(syncService.syncTodayAttendance());
//    }
//
//    // Kisi bhi date ka sync
//    @PostMapping("/sync/date")
//    public ResponseEntity<String> syncByDate(
//            @RequestParam String date) { // format: MM/dd/yyyy
//        return ResponseEntity.ok(syncService.syncAttendanceForDate(date));
//    }
}
