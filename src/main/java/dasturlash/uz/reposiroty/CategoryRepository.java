package dasturlash.uz.reposiroty;

import dasturlash.uz.entities.CategoryEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CategoryRepository  extends CrudRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByIdAndVisibleIsTrue(Integer id);
    Optional<CategoryEntity> findByCategoryKey(String key);

    @Transactional
    @Modifying
    @Query("update CategoryEntity set visible = false where id = ?1")
    int updateVisibleById(Integer id);
}
