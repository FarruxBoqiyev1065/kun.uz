package dasturlash.uz.controller;

import dasturlash.uz.dto.SectionDto;
import dasturlash.uz.service.SectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/section")
public class SectionController {
    @Autowired
    private SectionService service;

    @PostMapping("/admin")
    public ResponseEntity<SectionDto> create(@Valid @RequestBody SectionDto dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/admin")
    public ResponseEntity<List<SectionDto>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/lang")
    public ResponseEntity<List<SectionDto>> getByLang(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String language){
        return ResponseEntity.ok(service.getAllByLang(language));
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<SectionDto> update(@PathVariable Integer id, @Valid @RequestBody SectionDto newDto){
        return ResponseEntity.ok(service.update(id, newDto));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id){
        return ResponseEntity.ok(service.delete(id));
    }
}
