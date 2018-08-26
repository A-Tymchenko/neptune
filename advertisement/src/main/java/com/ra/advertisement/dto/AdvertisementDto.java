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
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class AdvertisementDto {
    private Long adId;

    @NotEmpty(message = "Title can't be empty")
    @Size(min = LengthHelper.MIN, max = LengthHelper.MAX, message = "Title should be longer than 4 and less than 50")
    @NonNull
    private String title;

    @NotEmpty(message = "Context can't be empty")
    @Size(min = LengthHelper.MIN, max = LengthHelper.MAX, message = "Context should be longer than 4 and less than 50")
    @NonNull
    private String context;

    @NotEmpty(message = "ImageUrl can't be empty")
    @Size(min = LengthHelper.MIN, max = LengthHelper.MAX, message = "ImageUrl should be longer than 4 and less than 50")
    @URL(message = "ImageUrl should be Url")
    @NonNull
    private String imageUrl;

    @NotEmpty(message = "language can't be empty")
    @Size(min = LengthHelper.MIN, max = LengthHelper.MAX, message = "Language should be longer than 4 and less than 50")
    @NonNull
    private String language;
}
