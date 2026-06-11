package dasturlash.uz.controller;

import dasturlash.uz.dto.ProfileDto;
import dasturlash.uz.dto.auth.AuthDto;
import dasturlash.uz.dto.auth.RegistrationDto;
import dasturlash.uz.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/registration/email/verification/{username}/{smsCode}")
    public ResponseEntity<String> registration(@PathVariable("username") String username, @PathVariable("smsCode") Integer smsCode) {
        return ResponseEntity.ok(service.regEmailVerification(username, smsCode));
    }

//    @GetMapping("/registration//resend")
//    public ResponseEntity<String> registration(@RequestBody RegistrationResendDto dto){
//        return ResponseEntity.ok(service.regResend(dto));
//    }
}
