package dasturlash.uz.service;

import dasturlash.uz.dto.CategoryDto;
import dasturlash.uz.entities.CategoryEntity;
import dasturlash.uz.exceptions.BadRequestException;
import dasturlash.uz.reposiroty.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    public CategoryDto create(@Valid CategoryDto dto) {
        Optional<CategoryEntity> optional = repository.findByCategoryKey(dto.getCategoryKey());
        if (optional.isPresent()) {
            throw new BadRequestException("Category key already exist");
        }
        CategoryEntity entity = new CategoryEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setCategoryKey(dto.getCategoryKey());
        entity.setVisible(Boolean.TRUE);
        repository.save(entity);
        // response
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public List<CategoryDto> getAll() {
        Iterable<CategoryEntity> all = repository.findAll();
        List<CategoryDto> list = new LinkedList<>();
        all.forEach(entity -> {
            list.add(toDto(entity));
        });
        return list;
    }

    public CategoryDto toDto(CategoryEntity entity){
        CategoryDto dto = new CategoryDto();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCategoryKey(entity.getCategoryKey());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public CategoryDto update(Integer id, @Valid CategoryDto newDto) {
        Optional<CategoryEntity> optional = repository.findByIdAndVisibleIsTrue(id);
        if (optional.isEmpty()) {
            throw new BadRequestException("Category not found");
        }
        Optional<CategoryEntity> keyOptional = repository.findByCategoryKey(newDto.getCategoryKey());
        if (keyOptional.isPresent() && !id.equals(keyOptional.get().getId())) {
            throw new BadRequestException("CategoryKey present");
        }
        CategoryEntity entity = optional.get();
        entity.setOrderNumber(newDto.getOrderNumber());
        entity.setNameUz(newDto.getNameUz());
        entity.setNameRu(newDto.getNameRu());
        entity.setNameEn(newDto.getNameEn());
        entity.setCategoryKey(newDto.getCategoryKey());
        repository.save(entity);

        newDto.setId(entity.getId());
        return newDto;
    }

    public Boolean delete(Integer id) {
        return repository.updateVisibleById(id) == 1;
    }

    public List<CategoryDto> getAllByLang(String language) {
        Iterable<CategoryEntity> all = repository.findAll();
        List<CategoryDto> list = new LinkedList<>();
        all.forEach(entity -> {
            CategoryDto dto = new CategoryDto();
            dto.setId(entity.getId());
            dto.setOrderNumber(entity.getOrderNumber());
            dto.setCategoryKey(entity.getCategoryKey());
            switch (language) {
                case "uz" -> dto.setName(entity.getNameUz());
                case "ru" -> dto.setName(entity.getNameRu());
                case "en" -> dto.setName(entity.getNameEn());
                default -> throw new BadRequestException("Language not supported");
            }
            list.add(dto);
        });
        return list;
    }
}
