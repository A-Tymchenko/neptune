package com.ra.advertisement.service;

import com.ra.advertisement.dao.ProviderAdvertisementDaoImpl;
import com.ra.advertisement.dto.ProviderDto;
import com.ra.advertisement.entity.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("providerService")
public class ProviderAdvertisementServiceImpl implements AdvertisementService<ProviderDto, Provider> {

    private final transient ProviderAdvertisementDaoImpl providerDao;
    private static Validator validator;

    @Autowired
    public ProviderAdvertisementServiceImpl(final ProviderAdvertisementDaoImpl providerDao) {
        this.providerDao = providerDao;
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Method get dto and save entity int into Data Base and return Entity with id.
     *
     * @return entity
     */
    @Override
    public List<String> saveEntityService(final HttpServletRequest request) {
        final List<String> allMessages = new ArrayList<>();
        final ProviderDto dto = provDtoCreator(request);
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
     * This method maps data from HttpServletRequest request on dto Object.
     * @param request HttpServletRequest request
     * @return dto Object
     */
    public static ProviderDto provDtoCreator(final HttpServletRequest request) {
        final ProviderDto providerDto = new ProviderDto();
        providerDto.setName(request.getParameter("name"));
        providerDto.setAddress(request.getParameter("address"));
        providerDto.setTelephone(request.getParameter("telephone"));
        providerDto.setCountry(request.getParameter("country"));
        return providerDto;
    }

    /**
     * This method map data from dto Object on Entity.
     * @param dto dto Object
     * @return Entity
     */
    public static Provider provCreator(final ProviderDto dto) {
        final Provider provider = new Provider();
        provider.setName(dto.getName());
        provider.setAddress(dto.getAddress());
        provider.setTelephone(dto.getTelephone());
        provider.setCountry(dto.getCountry());
        return provider;
    }

    /**
     * This method convert List of Entities into List of dto.
     * @param providerList list of Entities
     * @return List of dto.
     */
    public List<ProviderDto> mapListEntityIntoDto(List<Provider> providerList) {
        List<ProviderDto> providerDto = providerList.stream().map(
                s -> new ProviderDto(s.getProvId(),s.getName(), s.getAddress(), s.getTelephone(), s.getCountry()))
                .collect(Collectors.toList());
        return providerDto;
    }
}
