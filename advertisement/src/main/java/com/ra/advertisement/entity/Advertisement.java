package com.ra.advertisement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Advertisement {
    private Long adId;
    @NonNull
    private String title;
    @NonNull
    private String context;
    @NonNull
    private String imageUrl;
    @NonNull
    private String language;
}
