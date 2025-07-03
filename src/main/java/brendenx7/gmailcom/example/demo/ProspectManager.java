package brendenx7.gmailcom.example.demo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ProspectManager {

    public static List<Prospect> loadProspectsFromExcel(String filePath) throws IOException {
        List<Prospect> prospects = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                String name = row.getCell(0).getStringCellValue();
                String email = row.getCell(1).getStringCellValue();
                boolean isDecisionMaker = row.getCell(2).getBooleanCellValue();

                prospects.add(new Prospect(name, email, isDecisionMaker));
            }
        }
        return prospects;
    }

    public static void updateProspects(List<Prospect> existing, List<Prospect> updated) {
        Map<String, Prospect> emailMap = new HashMap<>();
        for (Prospect p : existing) {
            emailMap.put(p.getEmail().toLowerCase(), p);
        }

        for (Prospect newProspect : updated) {
            Prospect existingProspect = emailMap.get(newProspect.getEmail().toLowerCase());
            if (existingProspect != null) {
                // Update if data is different
                if (!existingProspect.getName().equals(newProspect.getName())) {
                    existingProspect.setName(newProspect.getName());
                }
                if (existingProspect.isDecisionMaker() != newProspect.isDecisionMaker()) {
                    existingProspect.setDecisionMaker(newProspect.isDecisionMaker());
                }
            } else {
                existing.add(newProspect); // Add new entry
            }
        }
    }

    public static void writeProspectsToExcel(String filePath, List<Prospect> prospects) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Prospects");

        // Header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Name");
        header.createCell(1).setCellValue("Email");
        header.createCell(2).setCellValue("IsDecisionMaker");

        // Data rows
        int rowNum = 1;
        for (Prospect p : prospects) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(p.getName());
            row.createCell(1).setCellValue(p.getEmail());
            row.createCell(2).setCellValue(p.isDecisionMaker());
        }

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }
        workbook.close();
    }

    // Example main method
    public static void main(String[] args) {
        try {
            String existingFile = "ExistingProspects.xlsx";
            String newFile = "NewProspects.xlsx";
            String outputFile = "UpdatedProspects.xlsx";

            List<Prospect> existingProspects = loadProspectsFromExcel(existingFile);
            List<Prospect> newProspects = loadProspectsFromExcel(newFile);

            updateProspects(existingProspects, newProspects);
            writeProspectsToExcel(outputFile, existingProspects);

            System.out.println("Updated prospects written to: " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
