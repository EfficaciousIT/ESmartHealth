package com.mobi.efficacious.esmarthealth.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Registration {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("vchMobileNo")
    @Expose
    private String VchMobileNo;

    @SerializedName("int_ResId")
    @Expose
    private Integer intResId;
    @SerializedName("vchName")
    @Expose
    private String vchName;
    @SerializedName("vchEmail_id")
    @Expose
    private String vchEmailId;
    @SerializedName("intActiveFlag")
    @Expose
    private Integer intActiveFlag;
    @SerializedName("vchFCMToken")
    @Expose
    private String vchFCMToken;
    @SerializedName("dtInsertedDate")
    @Expose
    private String dtInsertedDate;
    @SerializedName("vchPassword")
    @Expose
    private String vchPassword;

    public Registration(String vchMobileNo, String vchName, String vchEmailId, String vchFCMToken, String vchPassword) {
        VchMobileNo = vchMobileNo;
        this.vchName = vchName;
        this.vchEmailId = vchEmailId;
        this.vchFCMToken = vchFCMToken;
        this.vchPassword = vchPassword;
    }

    public Registration(String vchMobileNo, String vchFCMToken, String vchPassword) {
        VchMobileNo = vchMobileNo;
        this.vchFCMToken = vchFCMToken;
        this.vchPassword = vchPassword;
    }

    public Integer getIntResId() {
        return intResId;
    }

    public void setIntResId(Integer intResId) {
        this.intResId = intResId;
    }

    public String getVchName() {
        return vchName;
    }

    public void setVchName(String vchName) {
        this.vchName = vchName;
    }


    public String getVchEmailId() {
        return vchEmailId;
    }

    public void setVchEmailId(String vchEmailId) {
        this.vchEmailId = vchEmailId;
    }



    public Integer getIntActiveFlag() {
        return intActiveFlag;
    }

    public void setIntActiveFlag(Integer intActiveFlag) {
        this.intActiveFlag = intActiveFlag;
    }

    public String getVchFCMToken() {
        return vchFCMToken;
    }

    public void setVchFCMToken(String vchFCMToken) {
        this.vchFCMToken = vchFCMToken;
    }

    public String getDtInsertedDate() {
        return dtInsertedDate;
    }

    public void setDtInsertedDate(String dtInsertedDate) {
        this.dtInsertedDate = dtInsertedDate;
    }

    public String getVchPassword() {
        return vchPassword;
    }

    public void setVchPassword(String vchPassword) {
        this.vchPassword = vchPassword;
    }
    public String getVchMobileNo() {
        return VchMobileNo;
    }

    public void setVchMobileNo(String vchMobileNo) {
        VchMobileNo = vchMobileNo;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
