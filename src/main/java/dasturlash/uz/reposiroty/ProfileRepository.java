package dasturlash.uz.reposiroty;

import dasturlash.uz.entities.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer>, PagingAndSortingRepository<ProfileEntity, Integer> {
    Optional<ProfileEntity> findByUsernameAndVisibleIsTrue(String username);

    Optional<ProfileEntity> findByIdAndVisibleIsTrue(Integer id);

    @Query("From ProfileEntity p inner join fetch p.roleList where p.visible = true")
    Page<ProfileEntity> findAllWithRoles(Pageable pageable);
}
