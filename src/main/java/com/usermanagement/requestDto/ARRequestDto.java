package com.usermanagement.requestDto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//ARRequestDto.java
public class ARRequestDto {

 @NotNull(message = "Missing date is required")
 private LocalDate missingDate;

 @NotBlank(message = "Reason is required")
 @Size(max = 500, message = "Reason cannot exceed 500 characters")
 private String reason;

 private LocalTime requestedPunchIn;
 private LocalTime requestedPunchOut;

 public LocalDate getMissingDate() { return missingDate; }
 public void setMissingDate(LocalDate missingDate) { this.missingDate = missingDate; }
 public String getReason() { return reason; }
 public void setReason(String reason) { this.reason = reason; }
 public LocalTime getRequestedPunchIn() { return requestedPunchIn; }
 public void setRequestedPunchIn(LocalTime requestedPunchIn) { this.requestedPunchIn = requestedPunchIn; }
 public LocalTime getRequestedPunchOut() { return requestedPunchOut; }
 public void setRequestedPunchOut(LocalTime requestedPunchOut) { this.requestedPunchOut = requestedPunchOut; }
}
