package dasturlash.uz.controller;

import dasturlash.uz.dto.RegionDto;
import dasturlash.uz.enums.Languages;
import dasturlash.uz.service.RegionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {
    @Autowired
    private RegionService service;

    @PostMapping("")
    public ResponseEntity<RegionDto> create(@Valid @RequestBody RegionDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<RegionDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegionDto> update(@PathVariable Integer id, @Valid @RequestBody RegionDto newDto) {
        return ResponseEntity.ok(service.update(id, newDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/lang")
    public ResponseEntity<List<RegionDto>> getByLang(@RequestHeader(name = "Accept-Language", defaultValue = "uz") Languages language) {
        return ResponseEntity.ok(service.getAllByLang(language));
    }
}
