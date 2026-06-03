package dasturlash.uz.service;

import dasturlash.uz.dto.SectionDto;
import dasturlash.uz.entities.SectionEntity;
import dasturlash.uz.exceptions.BadRequestException;
import dasturlash.uz.mapper.SectionMapper;
import dasturlash.uz.repository.SectionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SectionService {
    @Autowired
    private SectionRepository repository;

    public SectionDto create(@Valid SectionDto dto) {
        Optional<SectionEntity> optional = repository.findBySectionKey(dto.getSectionKey());
        if (optional.isPresent()) {
            throw new BadRequestException("Section key already exist");
        }

        SectionEntity entity = new SectionEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setSectionKey(dto.getSectionKey());
        entity.setOrderNumber(dto.getOrderNumber());
        repository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public List<SectionDto> getAll() {
        Iterable<SectionEntity> all = repository.findAll();
        List<SectionDto> dtoList = new java.util.ArrayList<>();
        all.forEach(entity -> {
            dtoList.add(toDto(entity));
        });
        return dtoList;
    }

    public SectionDto toDto(SectionEntity entity){
        SectionDto dto = new SectionDto();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setSectionKey(entity.getSectionKey());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public List<SectionDto> getAllByLang(String language) {
        Iterable<SectionMapper> all = repository.getByLang(language);
        List<SectionDto> dtoList = new LinkedList<>();
        all.forEach(mapper -> {;
            SectionDto dto = new SectionDto();
            dto.setId(mapper.getId());
            dto.setName(mapper.getName());
            dto.setOrderNumber(mapper.getOrderNumber());
            dto.setSectionKey(mapper.getSectionKey());
            dtoList.add(dto);
         });
        return dtoList;
        }


    public SectionDto update(Integer id, @Valid SectionDto newDto) {
        Optional<SectionEntity> optional = repository.findByIdAndVisibleIsTrue(id);
        if (optional.isEmpty()) {
            throw new BadRequestException("Section not found");
        }
        Optional<SectionEntity> keyOptional = repository.findBySectionKey(newDto.getSectionKey());
        if (keyOptional.isPresent() && !id.equals(keyOptional.get().getId())) {
            throw new BadRequestException("Section present");
        }

        SectionEntity entity = optional.get();
        entity.setNameUz(newDto.getNameUz());
        entity.setNameRu(newDto.getNameRu());
        entity.setNameEn(newDto.getNameEn());
        entity.setSectionKey(newDto.getSectionKey());
        entity.setOrderNumber(newDto.getOrderNumber());
        repository.save(entity);

        newDto.setId(entity.getId());
        return newDto;
    }

    public Boolean delete(Integer id) {
        return repository.updateVisibleById(id) == 1;
    }
}
