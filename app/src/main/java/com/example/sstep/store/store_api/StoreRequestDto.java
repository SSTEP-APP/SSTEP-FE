package com.example.sstep.store.store_api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreRequestDto {
    private long id;
    private String name;
    private String address;
    private String latitude;
    private String longitude;
    private boolean scale;
    private boolean plan;
    private String payday;
    private long code;

    public StoreRequestDto(String name, String address, String latitude, String longitude, boolean scale, boolean plan, String payday, long code) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.scale = scale;
        this.plan = plan;
        this.payday = payday;
        this.code = code;
    }
}
