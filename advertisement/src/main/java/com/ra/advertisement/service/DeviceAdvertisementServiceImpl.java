package com.ra.advertisement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.ra.advertisement.dao.DeviceAdvertisementDaoImpl;
import com.ra.advertisement.dto.DeviceDto;
import com.ra.advertisement.entity.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("deviceService")
public class DeviceAdvertisementServiceImpl implements AdvertisementService<DeviceDto, Device> {

    private final transient DeviceAdvertisementDaoImpl deviceDao;
    private static Validator validator;

    @Autowired
    public DeviceAdvertisementServiceImpl(final DeviceAdvertisementDaoImpl deviceDao,
                                          final BeanValidator beanValidator) {
        this.deviceDao = deviceDao;
        this.validator = beanValidator.getValidator();
    }

    /**
     * Method get dto and save entity int into Data Base and return Entity with id.
     *
     * @return entity
     */
    @Override
    public List<String> saveEntityService(final HttpServletRequest request) {
        final List<String> allMessages = new ArrayList<>();
        final DeviceDto dto = devDtoCreator(request);
        final Set<ConstraintViolation<DeviceDto>> violations = validator.validate(dto);
        if (violations.isEmpty()) {
            final Device device = devCreator(dto);
            deviceDao.create(device);
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
    public List<DeviceDto> getAllEntityService() {
        final List<Device> deviceList = deviceDao.getAll();
        final List<DeviceDto> deviceDto = mapListEntityIntoDto(deviceList);
        return deviceDto;
    }

    /**
     * This method maps data from HttpServletRequest request on dto Object.
     *
     * @param request HttpServletRequest request
     * @return dto Object
     */
    public DeviceDto devDtoCreator(final HttpServletRequest request) {
        final DeviceDto devicetDto = new DeviceDto();
        devicetDto.setName(request.getParameter("name"));
        devicetDto.setModel(request.getParameter("model"));
        devicetDto.setDeviceType(request.getParameter("deviceType"));
        return devicetDto;
    }

    /**
     * This method map data from dto Object on Entity.
     *
     * @param dto dto Object
     * @return Entity
     */
    public Device devCreator(final DeviceDto dto) {
        final Device device = new Device();
        device.setName(dto.getName());
        device.setModel(dto.getModel());
        device.setDeviceType(dto.getDeviceType());
        return device;
    }

    /**
     * This method convert List of Entities into List of dto.
     *
     * @param deviceList list of Entities
     * @return List of dto.
     */
    public List<DeviceDto> mapListEntityIntoDto(final List<Device> deviceList) {
        final List<DeviceDto> deviceDto = deviceList.stream().map(
                s -> new DeviceDto(s.getDevId(), s.getName(), s.getModel(), s.getDeviceType()))
                .collect(Collectors.toList());
        return deviceDto;
    }
}
