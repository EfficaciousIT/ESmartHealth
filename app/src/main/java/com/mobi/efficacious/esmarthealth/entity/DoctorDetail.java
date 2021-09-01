package com.mobi.efficacious.esmarthealth.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorDetail {

    @SerializedName("intDocId")
    @Expose
    private Integer intDocId;
    @SerializedName("int_ResId")
    @Expose
    private Integer intResId;
    @SerializedName("vchName")
    @Expose
    private String vchName;
    @SerializedName("vchGender")
    @Expose
    private String vchGender;
    @SerializedName("vchAddress")
    @Expose
    private String vchAddress;
    @SerializedName("vchMobileNo")
    @Expose
    private String vchMobileNo;
    @SerializedName("vchEmail_id")
    @Expose
    private String vchEmailId;
    @SerializedName("vchQualification")
    @Expose
    private String vchQualification;
    @SerializedName("dtDr_Fromtiming")
    @Expose
    private String dtDrFromtiming;
    @SerializedName("dtDr_Totiming")
    @Expose
    private String dtDrTotiming;

    public DoctorDetail(Integer intResId, String vchName, String vchGender, String vchAddress, String vchMobileNo, String vchEmailId, String vchQualification, String dtDrFromtiming, String dtDrTotiming) {
        this.intResId = intResId;
        this.vchName = vchName;
        this.vchGender = vchGender;
        this.vchAddress = vchAddress;
        this.vchMobileNo = vchMobileNo;
        this.vchEmailId = vchEmailId;
        this.vchQualification = vchQualification;
        this.dtDrFromtiming = dtDrFromtiming;
        this.dtDrTotiming = dtDrTotiming;
    }

    public DoctorDetail(Integer intDocId, Integer intResId, String vchName, String vchGender, String vchAddress, String vchMobileNo, String vchEmailId, String vchQualification, String dtDrFromtiming, String dtDrTotiming) {
        this.intDocId = intDocId;
        this.intResId = intResId;
        this.vchName = vchName;
        this.vchGender = vchGender;
        this.vchAddress = vchAddress;
        this.vchMobileNo = vchMobileNo;
        this.vchEmailId = vchEmailId;
        this.vchQualification = vchQualification;
        this.dtDrFromtiming = dtDrFromtiming;
        this.dtDrTotiming = dtDrTotiming;
    }

    public Integer getIntDocId() {
        return intDocId;
    }

    public void setIntDocId(Integer intDocId) {
        this.intDocId = intDocId;
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

    public String getVchGender() {
        return vchGender;
    }

    public void setVchGender(String vchGender) {
        this.vchGender = vchGender;
    }

    public String getVchAddress() {
        return vchAddress;
    }

    public void setVchAddress(String vchAddress) {
        this.vchAddress = vchAddress;
    }

    public String getVchMobileNo() {
        return vchMobileNo;
    }

    public void setVchMobileNo(String vchMobileNo) {
        this.vchMobileNo = vchMobileNo;
    }

    public String getVchEmailId() {
        return vchEmailId;
    }

    public void setVchEmailId(String vchEmailId) {
        this.vchEmailId = vchEmailId;
    }

    public String getVchQualification() {
        return vchQualification;
    }

    public void setVchQualification(String vchQualification) {
        this.vchQualification = vchQualification;
    }

    public String getDtDrFromtiming() {
        return dtDrFromtiming;
    }

    public void setDtDrFromtiming(String dtDrFromtiming) {
        this.dtDrFromtiming = dtDrFromtiming;
    }

    public String getDtDrTotiming() {
        return dtDrTotiming;
    }

    public void setDtDrTotiming(String dtDrTotiming) {
        this.dtDrTotiming = dtDrTotiming;
    }

}