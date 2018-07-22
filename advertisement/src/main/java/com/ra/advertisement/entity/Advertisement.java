package com.ra.advertisement.entity;

import java.io.Serializable;

public class Advertisement {
    private Long adId;
    private String title;
    private String context;
    private String imageUrl;
    private String language;

    public Advertisement() {
    }

    public Advertisement(final Long adId, final String title, final String context, final String imageUrl,
                         final String language) {
        this.adId = adId;
        this.title = title;
        this.context = context;
        this.imageUrl = imageUrl;
        this.language = language;
    }

    public Advertisement(final String title, final String context, final String imageUrl,
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
