package dasturlash.uz.service;

import dasturlash.uz.entities.ProfileRoleEntity;
import dasturlash.uz.enums.Role;
import dasturlash.uz.reposiroty.ProfileRoleRepository;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileRoleService {
    @Autowired
    private ProfileRoleRepository profileRoleRepository;

    public void merge(Integer profileId,  List<Role> roleList) {
        List<Role> oldList = profileRoleRepository.getRoleListByProfileId(profileId);
        roleList.stream().filter(n -> !oldList.contains(n)).forEach(pe -> create(profileId, pe)); // create
        oldList.stream().filter(old -> !roleList.contains(old)).forEach(pe -> profileRoleRepository.deleteByIdAndRoleEnum(profileId, pe));
    }

    public void create(Integer profileId, Role role) {
        ProfileRoleEntity roleEntity = new ProfileRoleEntity();
        roleEntity.setProfileId(profileId);
        roleEntity.setRoles(role);
        profileRoleRepository.save(roleEntity);
    }

    public List<Role> getByProfileId(Integer id) {
        return profileRoleRepository.getRoleListByProfileId(id);
    }
}
