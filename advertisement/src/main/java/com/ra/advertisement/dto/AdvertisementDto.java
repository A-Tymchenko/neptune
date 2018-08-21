package com.ra.advertisement.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

public class AdvertisementDto {
    private Long adId;
    @NotEmpty(message = "Title can't be empty")
    @Size(min = MIN_LENGTH, max = MAX_LENGTH, message = "Title should be longer than 4 and less than 50")
    private String title;

    @NotEmpty(message = "Context can't be empty")
    @Size(min = MIN_LENGTH, max = MAX_LENGTH, message = "Context should be longer than 4 and less than 50")
    private String context;

    @NotEmpty(message = "ImageUrl can't be empty")
    @Size(min = MIN_LENGTH, max = MAX_LENGTH, message = "ImageUrl should be longer than 4 and less than 50")
    @URL(message = "ImageUrl should be Url")
    private String imageUrl;

    @NotEmpty(message = "language can't be empty")
    @Size(min = MIN_LENGTH, max = MAX_LENGTH, message = "Language should be longer than 4 and less than 50")
    private String language;

    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 50;

    public AdvertisementDto() {
    }

    public AdvertisementDto(final Long adId, final String title, final String context, final String imageUrl,
                            final String language) {
        this.adId = adId;
        this.title = title;
        this.context = context;
        this.imageUrl = imageUrl;
        this.language = language;
    }

    public AdvertisementDto(final String title, final String context, final String imageUrl,
                            final String language) {
        this.title = title;
        this.context = context;
        this.imageUrl = imageUrl;
        this.language = language;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(final Long adId) {
        this.adId = adId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(final String context) {
        this.context = context;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Advertisement{"
                + "adId=" + adId
                + ", title='" + title + '\''
                + ", context='" + context + '\''
                + ", imageUrl='" + imageUrl + '\''
                + ", language='" + language + '\''
                + '}';
    }
}
