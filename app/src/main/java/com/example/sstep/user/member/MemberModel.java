package com.example.sstep.user.member;

import com.google.gson.annotations.SerializedName;

public class MemberModel {
    @SerializedName("id")
    private int id;

    @SerializedName("memberId")
    private String memberId;

    @SerializedName("name")
    private String name;

    @SerializedName("phoneNum")
    private String phoneNum;

    @SerializedName("password")
    private String password;

    // 생성자, getter, setter 등 필요한 메서드들을 추가할 수 있습니다.

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
                "Member ID55: " + memberId + "\n" +
                "Name: " + name + "\n" +
                "Phone Number: " + phoneNum + "\n" +
                "Password: " + password;
    }
}