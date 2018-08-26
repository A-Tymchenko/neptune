package com.ra.advertisement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.ra.advertisement.dao.ProviderProjectDaoImpl;
import com.ra.advertisement.dto.ProviderDto;
import com.ra.advertisement.entity.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("providerService")
public class ProviderProjectServiceImpl implements ProjectService<ProviderDto> {

    private final transient ProviderProjectDaoImpl providerDao;
    private static Validator validator;

    @Autowired
    public ProviderProjectServiceImpl(final ProviderProjectDaoImpl providerDao,
                                      final BeanValidator beanValidator) {
        this.providerDao = providerDao;
        this.validator = beanValidator.getValidator();
    }

    /**
     * Method get dto and save entity int into Data Base and return Entity with id.
     *
     * @return entity
     */
    @Override
    public List<String> saveEntityService(final ProviderDto dto) {
        final List<String> allMessages = new ArrayList<>();
        final Set<ConstraintViolation<ProviderDto>> violations = validator.validate(dto);
        if (violations.isEmpty()) {
            final Provider provider = provCreator(dto);
            providerDao.create(provider);
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
    public List<ProviderDto> getAllEntityService() {
        final List<Provider> providerList = providerDao.getAll();
        final List<ProviderDto> providerDto = mapListEntityIntoDto(providerList);
        return providerDto;
    }

    /**
     * This method map data from dto Object on Entity.
     *
     * @param dto dto Object
     * @return Entity
     */
    public Provider provCreator(final ProviderDto dto) {
        final Provider provider = new Provider();
        provider.setName(dto.getName());
        provider.setAddress(dto.getAddress());
        provider.setTelephone(dto.getTelephone());
        provider.setCountry(dto.getCountry());
        return provider;
    }

    /**
     * This method convert List of Entities into List of dto.
     *
     * @param providerList list of Entities
     * @return List of dto.
     */
    public List<ProviderDto> mapListEntityIntoDto(final List<Provider> providerList) {
        final List<ProviderDto> providerDto = providerList.stream().map(
                s -> new ProviderDto(s.getProvId(), s.getName(), s.getAddress(), s.getTelephone(), s.getCountry()))
                .collect(Collectors.toList());
        return providerDto;
    }
}
