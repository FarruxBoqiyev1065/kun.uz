package dasturlash.uz.service.custom;

import dasturlash.uz.config.CustomUserDetails;
import dasturlash.uz.entities.ProfileEntity;
import dasturlash.uz.enums.Status;
import dasturlash.uz.exceptions.BadRequestException;
import dasturlash.uz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ProfileRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ProfileEntity> optional = repository.findByUsernameAndVisibleIsTrue(username);
        if (optional.isEmpty()) {
            throw new BadRequestException("User not found");
        }

        ProfileEntity profile = optional.get();
        return new CustomUserDetails(
                profile.getId(),
                profile.getUsername(),
                profile.getPassword(),
                profile.getStatus(),
                profile.getRoleList()
        );
    }
}
