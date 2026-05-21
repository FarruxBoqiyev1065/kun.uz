package dasturlash.uz.controller;

import dasturlash.uz.dto.ProfileDto;
import dasturlash.uz.dto.auth.AuthDto;
import dasturlash.uz.dto.auth.RegistrationDto;
import dasturlash.uz.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationDto dto){
        return ResponseEntity.ok(service.registration(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileDto> login(@Valid @RequestBody AuthDto dto){
        return ResponseEntity.ok(service.login(dto));
    }
}
