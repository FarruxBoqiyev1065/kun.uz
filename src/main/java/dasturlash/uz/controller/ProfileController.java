package dasturlash.uz.controller;

import dasturlash.uz.dto.ProfileDto;
import dasturlash.uz.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    @Autowired
    private ProfileService service;

    @PostMapping("")
    public ResponseEntity<ProfileDto> create(@RequestBody ProfileDto dto){
        ProfileDto result = service.create(dto);
        return ResponseEntity.ok(result);
    }
}
