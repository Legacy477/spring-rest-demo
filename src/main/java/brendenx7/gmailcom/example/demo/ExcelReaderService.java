package brendenx7.gmailcom.example.demo;

import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@Service
public class ExcelReaderService {

    public String readExcelAsText(MultipartFile file) throws Exception {
        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            StringBuilder builder = new StringBuilder();

            for (Row row : sheet) {
                for (Cell cell : row) {
                    builder.append(cell.toString()).append("\t");
                }
                builder.append("\n");
            }

            return builder.toString();
        }
    }
}



   /* public ExcelFile filter(ExcelFile targetList, List<ExcelFile> datasource) {
        System.out.println("Filtering with target: " + targetList.getFileName());
        System.out.println("Using " + datasource.size() + " data sources...");
        // TODO: Move real filter logic to a service
        return targetList; // Just returning original for now
    }*/

  /*  @GetMapping("/filter")
    public ResponseEntity<ByteArrayResource> filter(MultipartFile targetList, MultipartFile datasource ) {
        try {
            // Load target and datasource Excel files from resources folder
            File targetFile = new ClassPathResource("data/filteredList.xlsx").getFile();
            File sourceFile1 = new ClassPathResource("data/datasource.xlsx").getFile();
            File sourceFile2 = new ClassPathResource("data/sourceData2.xlsx").getFile();

            //ExcelFile targetList = new ExcelFile(targetFile);
            List<ExcelFile> dataSources = List.of(new ExcelFile(sourceFile1), new ExcelFile(sourceFile2));

            // Call the filter method
            ExcelFile filteredOutput = filter(targetList, dataSources);

            // Return dummy output file (targetList for now)
            FileInputStream fis = new FileInputStream(filteredOutput.getAbsolutePath());
            //InputStreamResource resource = new InputStreamResource(fis);



            byte[] excelBytes = null;// TODO: Read/Convert file into a byte array
            ByteArrayResource resource = new ByteArrayResource(excelBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

            *//*return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filteredOutput.getFileName())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);*//*

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }*/