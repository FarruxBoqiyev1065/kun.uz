package dasturlash.uz.repository;

import dasturlash.uz.entities.RegionEntity;
import dasturlash.uz.mapper.RegionMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {
    Optional<RegionEntity> findByRegionKey(String regionKey);

    @Query("from RegionEntity where visible = true order by orderNumber")
    Iterable<RegionEntity> findAll();

    @Transactional
    @Modifying
    @Query("update RegionEntity set visible = false where id = ?1")
    int updateVisibleById(Integer id);

    Optional<RegionEntity> findByIdAndVisibleIsTrue(Integer id);

    @Query("select c.id as id, " +
            "case :lang " +
            "   when 'UZ' then c.nameUz " +
            "   when 'RU' then c.nameRu " +
            "   when 'EN' then c.nameEn " +
            "end as name, " +
            "c.orderNumber as orderNumber, " +
            "c.regionKey as regionKey " +
            "from RegionEntity c where c.visible = true order by c.orderNumber asc")
    List<RegionMapper> getByLang(@Param("lang") String name);
}
