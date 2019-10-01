package com.seatup.commonPojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Home on 02-Feb-18.
 */

public class LoginRequest {

    /**
     * userEmailId : userEmailId
     * subUserName : subUserName
     * subUserImeiNo : subUserImeiNo
     * subUserPhoneName : subUserPhoneName
     * isHidden : isHidden
     */

    @SerializedName("userEmailId")
    private String userEmailId;
    @SerializedName("subUserName")
    private String subUserName;
    @SerializedName("subUserImeiNo")
    private String subUserImeiNo;
    @SerializedName("subUserPhoneName")
    private String subUserPhoneName;
    @SerializedName("isHidden")
    private String isHidden;

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getSubUserName() {
        return subUserName;
    }

    public void setSubUserName(String subUserName) {
        this.subUserName = subUserName;
    }

    public String getSubUserImeiNo() {
        return subUserImeiNo;
    }

    public void setSubUserImeiNo(String subUserImeiNo) {
        this.subUserImeiNo = subUserImeiNo;
    }

    public String getSubUserPhoneName() {
        return subUserPhoneName;
    }

    public void setSubUserPhoneName(String subUserPhoneName) {
        this.subUserPhoneName = subUserPhoneName;
    }

    public String getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(String isHidden) {
        this.isHidden = isHidden;
    }
}
