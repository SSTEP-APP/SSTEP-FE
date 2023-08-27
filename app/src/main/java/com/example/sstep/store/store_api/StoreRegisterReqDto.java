package com.example.sstep.store.store_api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreRegisterReqDto {
    private String memberUsername; //회원 고유번호
    private String storeName; //사업장명
    private String storeAddress; //사업장 주소
    private String latitude; //사업장 위도 좌표
    private String longitude; //사업장 경도 좌표
    private boolean scale; //사업장 규모 5인 이상 여부
    private boolean plan; //사업장 유료 플랜 여부
    private long code; //사업장 초대 코드 번호

    public String getMemberUsername() {
        return memberUsername;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public boolean isScale() {
        return scale;
    }

    public boolean isPlan() {
        return plan;
    }

    public long getCode() {
        return code;
    }

    public StoreRegisterReqDto(String username, String storeName, String storeAddress, String latitude, String longitude, boolean scale, boolean plan, long code) {
        this.memberUsername = username;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.scale = scale;
        this.plan = plan;
        this.code = code;
    }
}
