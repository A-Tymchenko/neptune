package com.ra.advertisement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.ra.advertisement.dao.AdvertisementProjectDaoImpl;
import com.ra.advertisement.dto.AdvertisementDto;
import com.ra.advertisement.entity.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("advertService")
public class AdvertisementProjectServiceImpl implements ProjectService<AdvertisementDto> {

    private final transient AdvertisementProjectDaoImpl advertDao;
    private static Validator validator;

    @Autowired
    public AdvertisementProjectServiceImpl(final AdvertisementProjectDaoImpl advertDao,
                                           final BeanValidator beanValidator) {
        this.advertDao = advertDao;
        this.validator = beanValidator.getValidator();
    }

    /**
     * Method get dto and save entity int into Data Base and return Entity with id.
     *
     * @return entity
     */
    @Override
    public List<String> saveEntityService(final AdvertisementDto dto) {
        final List<String> allMessages = new ArrayList<>();
        final Set<ConstraintViolation<AdvertisementDto>> violations = validator.validate(dto);
        if (violations.isEmpty()) {
            final Advertisement advertisement = advCreator(dto);
            advertDao.create(advertisement);
            allMessages.add("Object has been saved successfully");
        } else {
            violations.stream().forEach(item -> allMessages.add(item.getMessage()));
        }
        return allMessages;
    }

    /**
     * Method get list of entities and transfer it into list of Dto.
     *
     * @return list of Dto
     */
    @Override
    public List<AdvertisementDto> getAllEntityService() {
        final List<Advertisement> advertisementList = advertDao.getAll();
        final List<AdvertisementDto> advertDtoList = mapListEntityIntoDto(advertisementList);
        return advertDtoList;
    }

    /**
     * This method map data from dto Object on Entity.
     *
     * @param dto dto Object
     * @return Entity
     */
    public Advertisement advCreator(final AdvertisementDto dto) {
        final Advertisement advertisement = new Advertisement();
        advertisement.setAdId(dto.getAdId());
        advertisement.setTitle(dto.getTitle());
        advertisement.setContext(dto.getContext());
        advertisement.setImageUrl(dto.getImageUrl());
        advertisement.setLanguage(dto.getLanguage());
        return advertisement;
    }

    /**
     * This method convert List of Entities into List of dto.
     *
     * @param advertisementList list of Entities
     * @return List of dto.
     */
    public List<AdvertisementDto> mapListEntityIntoDto(final List<Advertisement> advertisementList) {
        final List<AdvertisementDto> advertDtoList = advertisementList.stream().map(
                s -> new AdvertisementDto(s.getAdId(), s.getTitle(), s.getContext(), s.getImageUrl(), s.getLanguage())
        ).collect(Collectors.toList());
        return advertDtoList;
    }
}
