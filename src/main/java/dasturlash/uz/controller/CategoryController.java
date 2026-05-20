package dasturlash.uz.controller;

import dasturlash.uz.dto.CategoryDto;
import dasturlash.uz.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @PostMapping("")
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryDto>> all(){
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Integer id, @Valid @RequestBody CategoryDto newDto){
        return ResponseEntity.ok(service.update(id, newDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id){
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/lang")
    public ResponseEntity<List<CategoryDto>> getByLang(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String language){
        return ResponseEntity.ok(service.getAllByLang(language));
    }
}
