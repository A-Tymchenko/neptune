package com.ra.advertisement.dto;

import javax.validation.constraints.Pattern;
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
public class ProviderDto {
    private Long provId;

    @NotEmpty(message = "Name can't be empty")
    @Size(min = LengthHelper.MIN, max = LengthHelper.MAX, message = "Name should be longer than 4 and less than 50")
    @NonNull
    private String name;

    @NotEmpty(message = "Address can't be empty")
    @Size(min = LengthHelper.MIN, max = LengthHelper.MAX, message = "Address should be longer than 4 and less than 50")
    @NonNull
    private String address;

    @NotEmpty(message = "Telephone can't be empty")
    @Size(min = LengthHelper.MIN, max = LengthHelper.MAX, message = "Telephone should be longer than 4 and less than 50")
    @Pattern(regexp = "[0-9]+", message = "Telephone should be nummbers only")
    @NonNull
    private String telephone;

    @NotEmpty(message = "Country can't be empty")
    @Size(min = LengthHelper.MIN, max = LengthHelper.MAX, message = "Country should be longer than 4 and less than 50")
    @NonNull
    private String country;
}
