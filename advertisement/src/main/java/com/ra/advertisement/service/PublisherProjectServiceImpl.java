package com.ra.advertisement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.ra.advertisement.dao.PublisherProjectDaoImpl;
import com.ra.advertisement.dto.PublisherDto;
import com.ra.advertisement.entity.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("publisherService")
public class PublisherProjectServiceImpl implements ProjectService<PublisherDto> {

    private final transient PublisherProjectDaoImpl publisherDao;
    private static Validator validator;

    @Autowired
    public PublisherProjectServiceImpl(final PublisherProjectDaoImpl publisherDao,
                                       final BeanValidator beanValidator) {
        this.publisherDao = publisherDao;
        this.validator = beanValidator.getValidator();
    }

    /**
     * Method get dto and save entity int into Data Base and return Entity with id.
     *
     * @return entity
     */
    @Override
    public List<String> saveEntityService(final PublisherDto dto) {
        final List<String> allMessages = new ArrayList<>();
        final Set<ConstraintViolation<PublisherDto>> violations = validator.validate(dto);
        if (violations.isEmpty()) {
            final Publisher publisher = pubCreator(dto);
            publisherDao.create(publisher);
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
    public List<PublisherDto> getAllEntityService() {
        final List<Publisher> publisherList = publisherDao.getAll();
        final List<PublisherDto> publisherDtoList = publisherList.stream().map(
                s -> new PublisherDto(s.getName(), s.getAddress(), s.getTelephone(), s.getCountry())
        ).collect(Collectors.toList());
        return publisherDtoList;
    }

    /**
     * This method map data from dto Object on Entity.
     *
     * @param dto dto Object
     * @return Entity
     */
    public Publisher pubCreator(final PublisherDto dto) {
        final Publisher publisher = new Publisher();
        publisher.setName(dto.getName());
        publisher.setAddress(dto.getAddress());
        publisher.setTelephone(dto.getTelephone());
        publisher.setCountry(dto.getCountry());
        return publisher;
    }

    /**
     * This method convert List of Entities into List of dto.
     *
     * @param publisherList list of Entities
     * @return List of dto.
     */
    public List<PublisherDto> mapListEntityIntoDto(final List<Publisher> publisherList) {
        final List<PublisherDto> publisherDto = publisherList.stream().map(
                s -> new PublisherDto(s.getPubId(), s.getName(), s.getAddress(), s.getTelephone(), s.getCountry()))
                .collect(Collectors.toList());
        return publisherDto;
    }
}
