package brendenx7.gmailcom.example.demo;

import java.io.File;

public class ExcelFile {
    private String fileName;
    private String absolutePath;

    public ExcelFile(File file) {
        this.fileName = file.getName();
        this.absolutePath = file.getAbsolutePath();
    }

    public String getFileName() {
        return fileName;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }
}
