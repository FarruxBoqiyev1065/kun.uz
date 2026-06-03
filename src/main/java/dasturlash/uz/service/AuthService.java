package dasturlash.uz.service;

import dasturlash.uz.dto.ProfileDto;
import dasturlash.uz.dto.auth.AuthDto;
import dasturlash.uz.dto.auth.RegistrationDto;
import dasturlash.uz.entities.ProfileEntity;
import dasturlash.uz.enums.Role;
import dasturlash.uz.enums.Status;
import dasturlash.uz.exceptions.BadRequestException;
import dasturlash.uz.repository.ProfileRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository repository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ProfileRoleService profileRoleService;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private EmailHistoryService emailHistoryService;

    public String registration(@Valid RegistrationDto dto) {
        Optional<ProfileEntity> existOptional = repository.findByUsernameAndVisibleIsTrue(dto.getUsername());
        if (existOptional.isPresent()) {
            ProfileEntity existProfile = existOptional.get();
            if (existProfile.getStatus().equals(Status.NOT_ACTIVE)) {
                profileRoleService.deleteRolesByProfileId(existProfile.getId());
                repository.deleteById(existProfile.getId());
            } else {
                throw new BadRequestException("Username already exists");
            }
        }

            ProfileEntity entity = new ProfileEntity();
            entity.setName(dto.getName());
            entity.setSurname(dto.getSurname());
            entity.setUsername(dto.getUsername());
            entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
            entity.setVisible(true);
            entity.setStatus(Status.NOT_ACTIVE);
            repository.save(entity);
            profileRoleService.create(entity.getId(), Role.USER);

            emailSenderService.sendRegistrationStyledEmail(entity.getUsername());

            return "Send confirmation code!";
    }

    public String regEmailVerification(String username, Integer smsCode) {
        Optional<ProfileEntity> existOptional = repository.findByUsernameAndVisibleIsTrue(username);
        if (existOptional.isEmpty()) {
            throw new BadRequestException("Username not found");
        }
        ProfileEntity profile = existOptional.get();
        if (!profile.getStatus().equals(Status.NOT_ACTIVE)) {
            throw new BadRequestException("Username int wrong status");
        }
        // check fo sms code and time
        if (emailHistoryService.isSmsSendToAccount(username, smsCode)) {
            profile.setStatus(Status.ACTIVE);
            repository.save(profile);
            return "Verification successfully completed";
        }
        throw new BadRequestException("Not completed");
    }
    public ProfileDto login(AuthDto dto) {
        Optional<ProfileEntity> profileOptional = repository.findByUsernameAndVisibleIsTrue(dto.getUsername());
        if (profileOptional.isEmpty()) {
            throw new BadRequestException("Username or password wrong");
        }
        ProfileEntity entity = profileOptional.get();
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), entity.getPassword())) {
            throw new BadRequestException("Username or password wrong");
        }
        if (!entity.getStatus().equals(Status.ACTIVE)) {
            throw new BadRequestException("User in wrong status");
        }

        ProfileDto response = new ProfileDto();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setSurname(entity.getSurname());
        response.setUsername(entity.getUsername());
        response.setRoleList(profileRoleService.getByProfileId(entity.getId()));
        return response;
    }
}
