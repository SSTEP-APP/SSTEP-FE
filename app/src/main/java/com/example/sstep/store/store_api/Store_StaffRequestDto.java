package com.example.sstep.store.store_api;

import com.example.sstep.user.staff_api.StaffRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Store_StaffRequestDto {
    private StoreRequestDto storeRequestDto;
    private StaffRequestDto staffRequestDto;

    public Store_StaffRequestDto(StoreRequestDto storeRequestDto, StaffRequestDto staffRequestDto) {
        this.storeRequestDto = storeRequestDto;
        this.staffRequestDto = staffRequestDto;
    }
}
