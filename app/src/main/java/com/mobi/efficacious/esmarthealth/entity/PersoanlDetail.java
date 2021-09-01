package com.mobi.efficacious.esmarthealth.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersoanlDetail {


    @SerializedName("int_perId")
    @Expose
    private Integer intPerId;
    @SerializedName("intResId")
    @Expose
    private Integer intResId;
    @SerializedName("ft_height")
    @Expose
    private Double ftHeight;
    @SerializedName("ft_weight")
    @Expose
    private Double ftWeight;
    @SerializedName("vchGender")
    @Expose
    private String vchGender;
    @SerializedName("dtDOB")
    @Expose
    private String dtDOB;
    @SerializedName("vchBloodGroup")
    @Expose
    private String vchBloodGroup;
    @SerializedName("vchBodyType")
    @Expose
    private String vchBodyType;
    @SerializedName("vchAllergic")
    @Expose
    private String vchAllergic;
    @SerializedName("DisabilityStatus")
    @Expose
    private String disabilityStatus;
    @SerializedName("vchDisability")
    @Expose
    private String vchDisability;
    @SerializedName("vchProfile")
    @Expose
    private String vchProfile;
    @SerializedName("dtInsertedDate")
    @Expose
    private String dtInsertedDate;
    @SerializedName("vchAddress")
    @Expose
    private String vchAddress;

    public PersoanlDetail(Integer intResId, Double ftHeight, Double ftWeight, String vchGender, String dtDOB, String vchBloodGroup, String vchBodyType, String vchAllergic, String disabilityStatus, String vchDisability,String vchprofile,String vchAddress) {
        this.intResId = intResId;
        this.ftHeight = ftHeight;
        this.ftWeight = ftWeight;
        this.vchGender = vchGender;
        this.dtDOB = dtDOB;
        this.vchBloodGroup = vchBloodGroup;
        this.vchBodyType = vchBodyType;
        this.vchAllergic = vchAllergic;
        this.disabilityStatus = disabilityStatus;
        this.vchDisability = vchDisability;
        this.vchProfile=vchprofile;
        this.vchAddress = vchAddress;
    }
    public PersoanlDetail(Integer intPerId,Integer intResId, Double ftHeight, Double ftWeight, String vchGender, String dtDOB, String vchBloodGroup, String vchBodyType, String vchAllergic, String disabilityStatus, String vchDisability,String vchAddress) {
        this.intPerId = intPerId;
        this.intResId = intResId;
        this.ftHeight = ftHeight;
        this.ftWeight = ftWeight;
        this.vchGender = vchGender;
        this.dtDOB = dtDOB;
        this.vchBloodGroup = vchBloodGroup;
        this.vchBodyType = vchBodyType;
        this.vchAllergic = vchAllergic;
        this.disabilityStatus = disabilityStatus;
        this.vchDisability = vchDisability;
        this.vchAddress = vchAddress;
    }

    public PersoanlDetail(Integer intPerId,Integer intResId,String vchProfile) {
        this.intPerId = intPerId;
        this.intResId = intResId;
        this.vchProfile = vchProfile;
    }

    public Integer getIntPerId() {
        return intPerId;
    }

    public void setIntPerId(Integer intPerId) {
        this.intPerId = intPerId;
    }

    public Integer getIntResId() {
        return intResId;
    }

    public void setIntResId(Integer intResId) {
        this.intResId = intResId;
    }

    public Double getFtHeight() {
        return ftHeight;
    }

    public void setFtHeight(Double ftHeight) {
        this.ftHeight = ftHeight;
    }

    public Double getFtWeight() {
        return ftWeight;
    }

    public void setFtWeight(Double ftWeight) {
        this.ftWeight = ftWeight;
    }

    public String getVchGender() {
        return vchGender;
    }

    public void setVchGender(String vchGender) {
        this.vchGender = vchGender;
    }

    public String getDtDOB() {
        return dtDOB;
    }

    public void setDtDOB(String dtDOB) {
        this.dtDOB = dtDOB;
    }

    public String getVchBloodGroup() {
        return vchBloodGroup;
    }

    public void setVchBloodGroup(String vchBloodGroup) {
        this.vchBloodGroup = vchBloodGroup;
    }

    public String getVchBodyType() {
        return vchBodyType;
    }

    public void setVchBodyType(String vchBodyType) {
        this.vchBodyType = vchBodyType;
    }

    public String getVchAllergic() {
        return vchAllergic;
    }

    public void setVchAllergic(String vchAllergic) {
        this.vchAllergic = vchAllergic;
    }

    public String getDisabilityStatus() {
        return disabilityStatus;
    }

    public void setDisabilityStatus(String disabilityStatus) {
        this.disabilityStatus = disabilityStatus;
    }

    public String getVchDisability() {
        return vchDisability;
    }

    public void setVchDisability(String vchDisability) {
        this.vchDisability = vchDisability;
    }

    public String getVchProfile() {
        return vchProfile;
    }

    public void setVchProfile(String vchProfile) {
        this.vchProfile = vchProfile;
    }

    public String getDtInsertedDate() {
        return dtInsertedDate;
    }

    public void setDtInsertedDate(String dtInsertedDate) {
        this.dtInsertedDate = dtInsertedDate;
    }

    public String getVchAddress() {
        return vchAddress;
    }

    public void setVchAddress(String vchAddress) {
        this.vchAddress = vchAddress;
    }

}