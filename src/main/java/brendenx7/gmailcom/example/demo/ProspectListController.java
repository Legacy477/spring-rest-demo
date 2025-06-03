package brendenx7.gmailcom.example.demo;

// src/main/java/com/example/demo/ProspectListController.java


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prospect-list")
public class ProspectListController {

    @GetMapping
    public String getProspectList() {
        return "GET: Prospect list fetched successfully!";
    }

    @PostMapping
    public String postProspectList() {
        return "POST: Prospect list created successfully!";
    }
}
