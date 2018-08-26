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
public class Device {
    private Long devId;
    @NonNull
    private String name;
    @NonNull
    private String model;
    @NonNull
    private String deviceType;
}
