package dasturlash.uz.reposiroty;

import dasturlash.uz.entities.ProfileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {
    Optional<ProfileEntity> findByUsernameAndVisibleIsTrue(String username);
    Optional<ProfileEntity> findByIdAndVisibleIsTrue(Integer id);
}
