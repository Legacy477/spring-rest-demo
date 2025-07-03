package brendenx7.gmailcom.example.demo;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/prospects")
public class ProspectListController {

    /**
     * Endpoint to filter data from bundled Excel files in resources.
     *
     * @return filtered Excel file as download (currently just echoes the target file)
     */
    @PostMapping("/filter")
    public ResponseEntity<ByteArrayResource> filter(@RequestParam("targetList") MultipartFile targetList,
                                                    @RequestParam("dataSource") MultipartFile dataSource) {
        try {

           // TODO: Write log statement to check if datasource/targetList is being read NULL or Not NULL
            // Load the Excel files from the classpath (resources folder
            ClassPathResource targetListResource = new ClassPathResource("filteredList.xlsx");


            // Read bytes from filteredList.xlsx (you can also read datasource if needed)
            byte[] excelBytes;
            try (InputStream targetInputStream = targetListResource.getInputStream()) {
                excelBytes = targetInputStream.readAllBytes();
            }

            // TODO: Add your filtering logic here with targetListResource and datasourceResource

            ByteArrayResource resource = new ByteArrayResource(excelBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=filtered_data.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = "Error processing files: " + e.getMessage();
            return ResponseEntity.internalServerError()
                    .body(new ByteArrayResource(errorMsg.getBytes()));
        }
    }
}
