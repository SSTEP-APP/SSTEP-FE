package com.example.sstep.store.store_api;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreResponseDto {
    private long id; //사업장 고유번호
    private String name; //사업장 이름
    private String address; //사업장 주소
    private String latitude; //사업장 위도 좌표
    private String longitude; //사업장 경도 좌표
    private boolean scale; //사업장 규모(5인이상: T, 이하: F)
    private boolean plan; //사업장 유료플랜 여부
    private long code; //사업장 코드번호 => 인앱 사업장 검색시 사용
    private int count; //사업장 구성원 수


    public StoreResponseDto(long id, String name, String address, String latitude, String longitude, boolean scale, boolean plan, long code, int count) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.scale = scale;
        this.plan = plan;
        this.code = code;
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean isScale() {
        return scale;
    }

    public void setScale(boolean scale) {
        this.scale = scale;
    }

    public boolean isPlan() {
        return plan;
    }

    public void setPlan(boolean plan) {
        this.plan = plan;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
