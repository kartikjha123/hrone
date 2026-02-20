package com.usermanagement.service;

import com.usermanagement.entity.Payroll;
import java.io.ByteArrayInputStream;

public interface PdfService {
    ByteArrayInputStream generateSalarySlip(Payroll payroll);
}
