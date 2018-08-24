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
public class Provider {
    private Long provId;
    @NonNull
    private String name;
    @NonNull
    private String address;
    @NonNull
    private String telephone;
    @NonNull
    private String country;
}
