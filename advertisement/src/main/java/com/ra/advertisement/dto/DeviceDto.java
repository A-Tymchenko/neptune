package com.ra.advertisement.dto;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class DeviceDto {
    private Long devId;

    @NotEmpty(message = "Name can't be empty")
    @Size(min = LengthHelper.MIN, max = LengthHelper.MAX, message = "Name should be longer than 4 and less than 50")
    @NonNull
    private String name;

    @NotEmpty(message = "Model can't be empty")
    @Size(min = LengthHelper.MIN, max = LengthHelper.MAX, message = "Model should be longer than 4 and less than 50")
    @NonNull
    private String model;

    @NotEmpty(message = "DeviceType can't be empty")
    @Size(min = LengthHelper.MIN, max = LengthHelper.MAX, message = "DeviceType should be longer than 4 and less than 50")
    @NonNull
    private String deviceType;

    private static final int MIN = 4;
    private static final int MAX = 50;
}
