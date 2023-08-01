package com.example.sstep.store.store_api;

import com.google.gson.annotations.SerializedName;

public class StoreModel {
    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("scale")
    private boolean scale;

    @SerializedName("plan")
    private boolean plan;

    @SerializedName("payday")
    private String payday;

    @SerializedName("code")
    private long code;


    // 생성자, getter, setter 등 필요한 메서드들을 추가할 수 있습니다.


}
/*~~
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //사업장 고유번호
    @Column(nullable = false)
    private String name; //사업장 이름
    @Column(nullable = false)
    private String address; //사업장 주소
    @Column(nullable = false)
    private String latitude; //사업장 위도 좌표
    @Column(nullable = false)
    private String longitude; //사업장 경도 좌표
    @Column(nullable = false)
    private boolean scale; //사업장 규모(5인이상: T, 이하: F)
    @Column(nullable = false)
    private boolean plan; //사업장 유료플랜 여부
    private String payday; //사업장 급여날
    @Column(nullable = false, unique = true)
    private long code; //사업장 코드번호 => 인앱 사업장 검색시 사용
 */