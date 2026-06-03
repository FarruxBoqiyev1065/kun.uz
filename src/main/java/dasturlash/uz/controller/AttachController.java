package dasturlash.uz.controller;

import dasturlash.uz.dto.AttachDTO;
import dasturlash.uz.service.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/attach")
public class AttachController {
    @Autowired
    private AttachService service;

//    @PostMapping("/upload")
//    public String upload(@RequestParam("file") MultipartFile file){
//        String fileName = service.saveToSystem(file);
//        return fileName;
//    }
    @PostMapping("/upload")
    public ResponseEntity<AttachDTO> create(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(service.upload(file));
    }

//    @GetMapping("/open/{fileName}")
//    public ResponseEntity<Resource> open(@PathVariable String fileName) throws Exception {
//        return service.openSimple(fileName);
//    }

    @GetMapping("/open/{fileName}")
    public ResponseEntity<Resource> open(@PathVariable String fileName) throws Exception {
        return service.open(fileName);
    }
}
