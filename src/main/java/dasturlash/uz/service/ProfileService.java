package dasturlash.uz.service;

import dasturlash.uz.dto.ProfileDto;
import dasturlash.uz.dto.ProfileUpdateDto;
import dasturlash.uz.entities.ProfileEntity;
import dasturlash.uz.enums.Status;
import dasturlash.uz.exceptions.BadRequestException;
import dasturlash.uz.reposiroty.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository repository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ProfileRoleService profileRoleService;

    public ProfileDto create(ProfileDto dto) {
        Optional<ProfileEntity> optional = repository.findByUsernameAndVisibleIsTrue(dto.getUsername());
        if (optional.isPresent()) {
            throw new BadRequestException("Username exists");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUsername(dto.getUsername());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setVisible(Boolean.TRUE);
        entity.setStatus(Status.ACTIVE);
        repository.save(entity);
        // add user roles
        profileRoleService.merge(entity.getId(), dto.getRoleList());

        ProfileDto response = toDTO(entity);
        return response;
    }

    public ProfileDto toDTO(ProfileEntity entity) {
        ProfileDto dto = new ProfileDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setUsername(entity.getUsername());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public ProfileDto getById(Integer id) {
        ProfileEntity entity = get(id);
        ProfileDto dto = toDTO(entity);
        dto.setRoleList(profileRoleService.getByProfileId(id));
        return dto;
    }

    private ProfileEntity get(Integer id) {
        return repository.findByIdAndVisibleIsTrue(id).orElseThrow(
                () -> new BadRequestException("Profile not found"));
    }

    public ProfileDto update(Integer id, ProfileUpdateDto dto) {
        ProfileEntity entity = get(id);
        Optional<ProfileEntity> optional = repository.findByUsernameAndVisibleIsTrue(dto.getUsername());
        if (optional.isPresent() && !optional.get().getId().equals(id)) {
            throw new BadRequestException("Username exists");
        }
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUsername(dto.getUsername());
        repository.save(entity);
        // add user roles
        profileRoleService.merge(entity.getId(), dto.getRoleList());

        ProfileDto response = toDTO(entity);
        response.setRoleList(dto.getRoleList());
        return response;
    }

    public Boolean delete(Integer id) {
        ProfileEntity entity = get(id);
        entity.setVisible(Boolean.FALSE);
        repository.save(entity);
        return Boolean.TRUE;
    }

    public PageImpl<ProfileDto> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<ProfileEntity> result = repository.findAllWithRoles(pageable);

        List<ProfileDto> dtoList = new LinkedList<>();
        for (ProfileEntity entity : result.getContent()) {
            dtoList.add(toDTO(entity));
        }
        return new PageImpl<>(dtoList, pageable, result.getTotalElements());
    }
}
