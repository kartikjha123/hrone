package com.usermanagement.serviceImpl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.usermanagement.entity.Payroll;
import com.usermanagement.service.PdfService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public ByteArrayInputStream generateSalarySlip(Payroll payroll) {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Fonts
            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            // Header
            Paragraph header = new Paragraph("SALARY SLIP", headFont);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.add(new Paragraph(" "));

            // Employee Info Table
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.addCell(getLabelCell("Employee Name:", titleFont));
            infoTable.addCell(getValueCell(payroll.getEmployee().getFirstName() + " " + payroll.getEmployee().getLastName(), normalFont));
            infoTable.addCell(getLabelCell("Department:", titleFont));
            infoTable.addCell(getValueCell(payroll.getEmployee().getDepartment(), normalFont));
            infoTable.addCell(getLabelCell("Month/Year:", titleFont));
            infoTable.addCell(getValueCell(payroll.getMonth() + "/" + payroll.getYear(), normalFont));
            document.add(infoTable);
            document.add(new Paragraph(" "));

            // Earnings and Deductions Table
            PdfPTable salaryTable = new PdfPTable(2);
            salaryTable.setWidthPercentage(100);

            // Headers
            salaryTable.addCell(getHeaderCell("Earnings", titleFont));
            salaryTable.addCell(getHeaderCell("Amount", titleFont));

            salaryTable.addCell(getValueCell("Base Salary", normalFont));
            salaryTable.addCell(getValueCell(String.format("%.2f", payroll.getBaseSalary()), normalFont));

            salaryTable.addCell(getValueCell("Production Earnings", normalFont));
            salaryTable.addCell(getValueCell(String.format("%.2f", payroll.getProductionEarnings()), normalFont));

            salaryTable.addCell(getValueCell("OT Earnings", normalFont));
            salaryTable.addCell(getValueCell(String.format("%.2f", payroll.getOtEarnings()), normalFont));

            salaryTable.addCell(getHeaderCell("Deductions", titleFont));
            salaryTable.addCell(getHeaderCell("Amount", titleFont));

            salaryTable.addCell(getValueCell("Advance Deduction", normalFont));
            salaryTable.addCell(getValueCell(String.format("%.2f", payroll.getAdvanceDeduction()), normalFont));

            // Net Salary
            PdfPCell netLabelCell = new PdfPCell(new Phrase("Net Salary", titleFont));
            netLabelCell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
            salaryTable.addCell(netLabelCell);

            PdfPCell netValueCell = new PdfPCell(new Phrase(String.format("%.2f", payroll.getNetSalary()), titleFont));
            netValueCell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
            salaryTable.addCell(netValueCell);

            document.add(salaryTable);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Status: " + payroll.getStatus(), normalFont));
            document.add(new Paragraph("Processed Date: " + payroll.getProcessedDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), normalFont));

            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private PdfPCell getLabelCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        return cell;
    }

    private PdfPCell getValueCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        return cell;
    }

    private PdfPCell getHeaderCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
        cell.setPadding(5);
        return cell;
    }
}
