package com.ra.advertisement.entity;

public class Advertisement {
    private Long adId;
    private String title;
    private String context;
    private String imageUrl;
    private String language;
    private Long provId;

    public Advertisement() {
    }

    public Advertisement(final Long adId, final String title, final String context, final String imageUrl,
                         final String language, final Long provId) {
        this.adId = adId;
        this.title = title;
        this.context = context;
        this.imageUrl = imageUrl;
        this.language = language;
        this.provId = provId;
    }

    public Advertisement(final String title, final String context, final String imageUrl,
                         final String language, final Long provId) {
        this.title = title;
        this.context = context;
        this.imageUrl = imageUrl;
        this.language = language;
        this.provId = provId;
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

    public Long getProvId() {
        return provId;
    }

    public void setProvId(final Long provId) {
        this.provId = provId;
    }

    @Override
    public String toString() {
        return "Advertisement{"
                + "adId=" + adId
                + ", title='" + title + '\''
                + ", context='" + context + '\''
                + ", imageUrl='" + imageUrl + '\''
                + ", language='" + language + '\''
                + ", provId=" + provId
                + '}';
    }
}
