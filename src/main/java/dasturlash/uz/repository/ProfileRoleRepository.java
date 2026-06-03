package dasturlash.uz.repository;

import dasturlash.uz.entities.ProfileRoleEntity;
import dasturlash.uz.enums.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProfileRoleRepository extends CrudRepository<ProfileRoleEntity, Integer> {
    @Query("select roles from ProfileRoleEntity where profileId =?1")
    List<Role> getRoleListByProfileId(Integer profileId);

    @Transactional
    @Modifying
    @Query("Delete from ProfileRoleEntity where profileId =?1 and roles =?2")
    void deleteByIdAndRoleEnum(Integer profileId, Role role);

    @Transactional
    @Modifying
    @Query("delete from ProfileRoleEntity where profileId =?1")
    void deleteByProfileId(Integer profileId);
}
