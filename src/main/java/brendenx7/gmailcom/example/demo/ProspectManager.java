package brendenx7.gmailcom.example.demo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ProspectManager {

    public static List<Prospect> readProspectsFromExcel(File file) throws IOException {
        List<Prospect> prospects = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                String name = row.getCell(0).getStringCellValue();
                String email = row.getCell(1).getStringCellValue();
                boolean decision = row.getCell(2).getBooleanCellValue();

                prospects.add(new Prospect(name, email, decision));
            }
        }
        return prospects;
    }

    public static void writeProspectsToExcel(List<Prospect> list, File file) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Prospects");

        // Write header
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Name");
        header.createCell(1).setCellValue("Email");
        header.createCell(2).setCellValue("Decision Maker");

        int rowNum = 1;
        for (Prospect p : list) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(p.getName());
            row.createCell(1).setCellValue(p.getEmail());
            row.createCell(2).setCellValue(p.isDecisionMaker());
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
        }
        workbook.close();
    }
}
