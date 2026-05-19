package dasturlash.uz.controller;

import dasturlash.uz.dto.ProfileDto;
import dasturlash.uz.dto.ProfileUpdateDto;
import dasturlash.uz.service.ProfileService;
import dasturlash.uz.util.PageUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService service;

    @PostMapping("")
    public ResponseEntity<ProfileDto> create(@Valid @RequestBody ProfileDto dto){
        ProfileDto result = service.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDto> getById(@PathVariable Integer id){
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileDto> update(@PathVariable Integer id, @Valid @RequestBody ProfileUpdateDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id){
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/pagenation")
    public ResponseEntity<PageImpl<ProfileDto>> pagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(service.pagination(PageUtil.page(page), size));
    }
}
