package dasturlash.uz.service;

import dasturlash.uz.dto.RegionDto;
import dasturlash.uz.entities.RegionEntity;
import dasturlash.uz.enums.Languages;
import dasturlash.uz.exceptions.BadRequestException;
import dasturlash.uz.mapper.RegionMapper;
import dasturlash.uz.repository.RegionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    private RegionRepository repository;

    public RegionDto create(RegionDto dto) {
        Optional<RegionEntity> optional = repository.findByRegionKey(dto.getRegionKey());
        if (optional.isPresent()) {
            throw new BadRequestException("Region key already exist");
        }

        RegionEntity entity = new RegionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setRegionKey(dto.getRegionKey());
        repository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public List<RegionDto> getAll() {
        Iterable<RegionEntity> all = repository.findAll();
        List<RegionDto> dtoList = new LinkedList<>();
        all.forEach(entity -> {
            dtoList.add(toDto(entity));
        });
        return dtoList;
    }

    public RegionDto toDto(RegionEntity entity){
        RegionDto dto = new RegionDto();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setRegionKey(entity.getRegionKey());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public RegionDto update(Integer id, @Valid RegionDto newDto) {
        Optional<RegionEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new BadRequestException("Region not found");
        }
        Optional<RegionEntity> keyOptional = repository.findByRegionKey(newDto.getRegionKey());
        if (keyOptional.isPresent() && !id.equals(keyOptional.get().getId())) {
            throw new BadRequestException("Region key already exist");
        }

        RegionEntity entity = optional.get();
        entity.setOrderNumber(newDto.getOrderNumber());
        entity.setNameUz(newDto.getNameUz());
        entity.setNameRu(newDto.getNameRu());
        entity.setNameEn(newDto.getNameEn());
        entity.setRegionKey(newDto.getRegionKey());
        repository.save(entity);

        newDto.setId(entity.getId());
        return newDto;
    }

    public Boolean delete(Integer id) {
        return repository.updateVisibleById(id) == 1;
    }

    public List<RegionDto> getAllByLang(Languages language) {
        Iterable<RegionMapper> iterable = repository.getByLang(language.name());
        List<RegionDto> dtoList = new LinkedList<>();
        iterable.forEach(mapper -> {
            RegionDto dto = new RegionDto();
            dto.setId(mapper.getId());
            dto.setName(mapper.getName());
            dto.setRegionKey(mapper.getRegionKey());
            dtoList.add(dto);
        });
        return dtoList;
    }
}
