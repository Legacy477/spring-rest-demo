package brendenx7.gmailcom.example.demo;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/prospects")
public class ProspectListController {

    /**
     * Compare new and old prospect Excel files and return updated version as download.
     *
     * @param targetList  New incoming Excel file (filtered list)
     * @param dataSource  Existing Excel file (master datasource)
     * @return Excel file download containing updated list
     */
    @PostMapping("/compare-and-download")
    public ResponseEntity<ByteArrayResource> compareAndDownload(
            @RequestParam("targetList") MultipartFile targetList,
            @RequestParam("dataSource") MultipartFile dataSource) {

        try {
            // Write uploaded files to temporary disk files
            File targetTemp = File.createTempFile("target_", ".xlsx");
            targetList.transferTo(targetTemp);

            File datasourceTemp = File.createTempFile("datasource_", ".xlsx");
            dataSource.transferTo(datasourceTemp);

            // Run merge and get result
            File updatedFile = ProspectService.compareAndUpdateProspects(targetTemp, datasourceTemp);

            // Read final result into memory
            byte[] excelBytes = Files.readAllBytes(updatedFile.toPath());
            ByteArrayResource resource = new ByteArrayResource(excelBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=updated_prospects.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(excelBytes.length)
                    .contentType(MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = "Error: " + e.getMessage();
            return ResponseEntity.internalServerError()
                    .body(new ByteArrayResource(errorMsg.getBytes()));
        }
    }
}
