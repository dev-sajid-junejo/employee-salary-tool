package org.example;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class EmployeePayroll extends JFrame {
    // Components
    private String reportFilePath = null;
    private JTextField txtEmployeeID, txtEmployeeName, txtExchangeRate, txtBasicPay, txtLoanAmount, txtAdvanceAmount, txtEOBIDeduction, txtLeaveDeduction, txtTaxAmount;
    private JComboBox<String> cmbPaySchedule, cmbCurrency;
    private JButton btnCalculate, btnClear, btnGenerateReport;

    public EmployeePayroll() {
        // Frame settings
        setTitle("Employee Payroll System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new GridLayout(14, 2, 10, 10));

        // Employee Information
        add(new JLabel("Employee ID:"));
        txtEmployeeID = new JTextField();
        add(txtEmployeeID);

        add(new JLabel("Employee Name:"));
        txtEmployeeName = new JTextField();
        add(txtEmployeeName);

        add(new JLabel("Pay-Disbursement-Schedule:"));
        String[] schedules = {" – R(Regular)", "CO(Contract-Old)", "CN(Contract-New)"};
        cmbPaySchedule = new JComboBox<>(schedules);
        add(cmbPaySchedule);

        add(new JLabel("Pay-Currency:"));
        String[] currencies = {"USD", "PKR"};
        cmbCurrency = new JComboBox<>(currencies);
        add(cmbCurrency);

        add(new JLabel("Exchange Rate:"));
        txtExchangeRate = new JTextField();
        add(txtExchangeRate);

        add(new JLabel("Basic Pay Amount:"));
        txtBasicPay = new JTextField();
        add(txtBasicPay);

        // Deductions
        add(new JLabel("Loan Amount:"));
        txtLoanAmount = new JTextField();
        add(txtLoanAmount);

        add(new JLabel("Advance Amount:"));
        txtAdvanceAmount = new JTextField();
        add(txtAdvanceAmount);

        add(new JLabel("EOBI Deduction:"));
        txtEOBIDeduction = new JTextField();
        add(txtEOBIDeduction);

        add(new JLabel("Leave Deduction:"));
        txtLeaveDeduction = new JTextField();
        add(txtLeaveDeduction);

        // Tax
        add(new JLabel("Tax Amount:"));
        txtTaxAmount = new JTextField();
        add(txtTaxAmount);

        // Buttons
        btnCalculate = new JButton("Calculate");
        add(btnCalculate);

        btnClear = new JButton("Clear");
        add(btnClear);

        btnGenerateReport = new JButton("Generate Report");
        add(btnGenerateReport);

        // Button actions
        btnCalculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateSalary();
            }
        });

        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        btnGenerateReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });

        // Display the frame
        setVisible(true);
    }

    private void calculateSalary() {
        // Logic for calculating salary based on input fields
        JOptionPane.showMessageDialog(this, "Salary calculation not implemented.");
    }

    private void clearFields() {
        // Logic to clear all input fields
        txtEmployeeID.setText("");
        txtEmployeeName.setText("");
        txtExchangeRate.setText("");
        txtBasicPay.setText("");
        txtLoanAmount.setText("");
        txtAdvanceAmount.setText("");
        txtEOBIDeduction.setText("");
        txtLeaveDeduction.setText("");
        txtTaxAmount.setText("");
        cmbPaySchedule.setSelectedIndex(0);
        cmbCurrency.setSelectedIndex(0);
    }

    private void generateReport() {
        if (reportFilePath == null) {
            // Ask the user to select a file name and location the first time
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Report As");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                reportFilePath = fileToSave.getAbsolutePath();

                // Ensure the file has the correct extension
                if (!reportFilePath.endsWith(".xlsx")) {
                    reportFilePath += ".xlsx";
                }
            } else {
                // If the user cancels the file save dialog, exit the method
                return;
            }
        }

        Workbook workbook;
        Sheet sheet;
        int lastRow;

        try {
            File file = new File(reportFilePath);
            if (file.exists()) {
                // If the file exists, open it and get the sheet
                try (FileInputStream fileIn = new FileInputStream(file)) {
                    workbook = new XSSFWorkbook(fileIn);
                    sheet = workbook.getSheetAt(0);
                    lastRow = sheet.getLastRowNum() + 1;
                }
            } else {
                // If the file does not exist, create a new workbook and sheet
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Employee Payroll Report");
                lastRow = 0;

                // Create header row
                Row headerRow = sheet.createRow(lastRow++);
                headerRow.createCell(0).setCellValue("Employee ID");
                headerRow.createCell(1).setCellValue("Employee Name");
                headerRow.createCell(2).setCellValue("Pay-Disbursement-Schedule");
                headerRow.createCell(3).setCellValue("Pay-Currency:");
                headerRow.createCell(4).setCellValue("Exchange Rate");
                headerRow.createCell(5).setCellValue("Basic Pay Amount");
                headerRow.createCell(6).setCellValue("Loan Amount");
                headerRow.createCell(7).setCellValue("Advance Amount");
                headerRow.createCell(8).setCellValue("EOBI Deduction");
                headerRow.createCell(9).setCellValue("Leave Deduction");
                headerRow.createCell(10).setCellValue("Tax Amount");
            }

            // Create data rows
            Row dataRow = sheet.createRow(lastRow++);
            dataRow.createCell(0).setCellValue(txtEmployeeID.getText());
            dataRow.createCell(1).setCellValue(txtEmployeeName.getText());
            dataRow.createCell(2).setCellValue(cmbPaySchedule.getSelectedItem().toString());
            dataRow.createCell(3).setCellValue(cmbCurrency.getSelectedItem().toString());
            dataRow.createCell(4).setCellValue(txtExchangeRate.getText());
            dataRow.createCell(5).setCellValue(txtBasicPay.getText());
            dataRow.createCell(6).setCellValue(txtLoanAmount.getText());
            dataRow.createCell(7).setCellValue(txtAdvanceAmount.getText());
            dataRow.createCell(8).setCellValue(txtEOBIDeduction.getText());
            dataRow.createCell(9).setCellValue(txtLeaveDeduction.getText());
            dataRow.createCell(10).setCellValue(txtTaxAmount.getText());

            // Adjust column width for all columns where data is written
            for (int i = 0; i <= 10; i++) {
                sheet.autoSizeColumn(i);
            }

            // Save the Excel file
            try (FileOutputStream fileOut = new FileOutputStream(reportFilePath)) {
                workbook.write(fileOut);
            }
            workbook.close();
            JOptionPane.showMessageDialog(this, "Report generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error opening/creating file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        new EmployeePayroll();
    }
}
