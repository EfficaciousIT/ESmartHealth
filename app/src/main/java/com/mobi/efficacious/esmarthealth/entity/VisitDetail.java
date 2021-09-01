package com.mobi.efficacious.esmarthealth.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VisitDetail {

    @SerializedName("DrName")
    @Expose
    private String DrName;
    @SerializedName("MedTime")
    @Expose
    private String MedTime;
    @SerializedName("intDr_visitId")
    @Expose
    private Integer intDrVisitId;
    @SerializedName("intDoc_id")
    @Expose
    private Integer intDocId;
    @SerializedName("int_ResId")
    @Expose
    private Integer intResId;
    @SerializedName("dt_visitdate")
    @Expose
    private String dtVisitdate;
    @SerializedName("dt_nxt_visitdate")
    @Expose
    private String dtNxtVisitdate;
    @SerializedName("vchMedicine1")
    @Expose
    private String vchMedicine1;
    @SerializedName("vchMedicine2")
    @Expose
    private String vchMedicine2;
    @SerializedName("vchMedicine3")
    @Expose
    private String vchMedicine3;
    @SerializedName("vchMedicine4")
    @Expose
    private String vchMedicine4;
    @SerializedName("vchMedicine5")
    @Expose
    private String vchMedicine5;
    @SerializedName("vchDescription")
    @Expose
    private String vchDescription;
    @SerializedName("vchAlert")
    @Expose
    private String vchAlert;
    @SerializedName("intActiveflag")
    @Expose
    private Integer intActiveflag;
    @SerializedName("vchAttachment1")
    @Expose
    private String vchAttachment1;
    @SerializedName("vchAttachment2")
    @Expose
    private String vchAttachment2;
    @SerializedName("vchAttachment3")
    @Expose
    private String vchAttachment3;
    @SerializedName("vchAttachment4")
    @Expose
    private String vchAttachment4;
    @SerializedName("dtMed1Time1")
    @Expose
    private String dtMed1Time1;
    @SerializedName("dtMed1Time2")
    @Expose
    private String dtMed1Time2;
    @SerializedName("dtMed1Time3")
    @Expose
    private String dtMed1Time3;


    public VisitDetail(Integer intDocId, Integer intResId, String dtVisitdate, String dtNxtVisitdate, String vchMedicine1, String vchMedicine2, String vchMedicine3, String vchMedicine4, String vchMedicine5, String vchDescription, String vchAlert, String vchAttachment1, String vchAttachment2, String vchAttachment3, String vchAttachment4, String dtMed1Time1, String dtMed1Time2, String dtMed1Time3) {
        this.intDocId = intDocId;
        this.intResId = intResId;
        this.dtVisitdate = dtVisitdate;
        this.dtNxtVisitdate = dtNxtVisitdate;
        this.vchMedicine1 = vchMedicine1;
        this.vchMedicine2 = vchMedicine2;
        this.vchMedicine3 = vchMedicine3;
        this.vchMedicine4 = vchMedicine4;
        this.vchMedicine5 = vchMedicine5;
        this.vchDescription = vchDescription;
        this.vchAlert = vchAlert;
        this.vchAttachment1 = vchAttachment1;
        this.vchAttachment2 = vchAttachment2;
        this.vchAttachment3 = vchAttachment3;
        this.vchAttachment4 = vchAttachment4;
        this.dtMed1Time1 = dtMed1Time1;
        this.dtMed1Time2 = dtMed1Time2;
        this.dtMed1Time3 = dtMed1Time3;
    }
    public VisitDetail(Integer intDrVisit_Id,Integer intDocId, Integer intResId, String dtVisitdate, String dtNxtVisitdate, String vchMedicine1, String vchMedicine2, String vchMedicine3, String vchMedicine4, String vchMedicine5, String vchDescription, String vchAlert, String vchAttachment1, String vchAttachment2, String vchAttachment3, String vchAttachment4, String dtMed1Time1, String dtMed1Time2, String dtMed1Time3) {
        this.intDrVisitId=intDrVisit_Id;
        this.intDocId = intDocId;
        this.intResId = intResId;
        this.dtVisitdate = dtVisitdate;
        this.dtNxtVisitdate = dtNxtVisitdate;
        this.vchMedicine1 = vchMedicine1;
        this.vchMedicine2 = vchMedicine2;
        this.vchMedicine3 = vchMedicine3;
        this.vchMedicine4 = vchMedicine4;
        this.vchMedicine5 = vchMedicine5;
        this.vchDescription = vchDescription;
        this.vchAlert = vchAlert;
        this.vchAttachment1 = vchAttachment1;
        this.vchAttachment2 = vchAttachment2;
        this.vchAttachment3 = vchAttachment3;
        this.vchAttachment4 = vchAttachment4;
        this.dtMed1Time1 = dtMed1Time1;
        this.dtMed1Time2 = dtMed1Time2;
        this.dtMed1Time3 = dtMed1Time3;
    }
    public VisitDetail(Integer intDrVisitId, Integer intDocId, Integer intResId,String vchalert) {
        this.intDrVisitId = intDrVisitId;
        this.intDocId = intDocId;
        this.intResId = intResId;
        this.vchAlert=vchalert;
    }

    public Integer getIntDrVisitId() {
        return intDrVisitId;
    }

    public void setIntDrVisitId(Integer intDrVisitId) {
        this.intDrVisitId = intDrVisitId;
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

    public String getDtVisitdate() {
        return dtVisitdate;
    }

    public void setDtVisitdate(String dtVisitdate) {
        this.dtVisitdate = dtVisitdate;
    }

    public String getDtNxtVisitdate() {
        return dtNxtVisitdate;
    }

    public void setDtNxtVisitdate(String dtNxtVisitdate) {
        this.dtNxtVisitdate = dtNxtVisitdate;
    }

    public String getVchMedicine1() {
        return vchMedicine1;
    }

    public void setVchMedicine1(String vchMedicine1) {
        this.vchMedicine1 = vchMedicine1;
    }

    public String getVchMedicine2() {
        return vchMedicine2;
    }

    public void setVchMedicine2(String vchMedicine2) {
        this.vchMedicine2 = vchMedicine2;
    }

    public String getVchMedicine3() {
        return vchMedicine3;
    }

    public void setVchMedicine3(String vchMedicine3) {
        this.vchMedicine3 = vchMedicine3;
    }

    public String getVchMedicine4() {
        return vchMedicine4;
    }

    public void setVchMedicine4(String vchMedicine4) {
        this.vchMedicine4 = vchMedicine4;
    }

    public String getVchMedicine5() {
        return vchMedicine5;
    }

    public void setVchMedicine5(String vchMedicine5) {
        this.vchMedicine5 = vchMedicine5;
    }

    public String getVchDescription() {
        return vchDescription;
    }

    public void setVchDescription(String vchDescription) {
        this.vchDescription = vchDescription;
    }

    public String getVchAlert() {
        return vchAlert;
    }

    public void setVchAlert(String vchAlert) {
        this.vchAlert = vchAlert;
    }

    public Integer getIntActiveflag() {
        return intActiveflag;
    }

    public void setIntActiveflag(Integer intActiveflag) {
        this.intActiveflag = intActiveflag;
    }

    public String getVchAttachment1() {
        return vchAttachment1;
    }

    public void setVchAttachment1(String vchAttachment1) {
        this.vchAttachment1 = vchAttachment1;
    }

    public String getVchAttachment2() {
        return vchAttachment2;
    }

    public void setVchAttachment2(String vchAttachment2) {
        this.vchAttachment2 = vchAttachment2;
    }

    public String getVchAttachment3() {
        return vchAttachment3;
    }

    public void setVchAttachment3(String vchAttachment3) {
        this.vchAttachment3 = vchAttachment3;
    }

    public String getVchAttachment4() {
        return vchAttachment4;
    }

    public void setVchAttachment4(String vchAttachment4) {
        this.vchAttachment4 = vchAttachment4;
    }

    public String getDtMed1Time1() {
        return dtMed1Time1;
    }

    public void setDtMed1Time1(String dtMed1Time1) {
        this.dtMed1Time1 = dtMed1Time1;
    }

    public String getDtMed1Time2() {
        return dtMed1Time2;
    }

    public void setDtMed1Time2(String dtMed1Time2) {
        this.dtMed1Time2 = dtMed1Time2;
    }

    public String getDtMed1Time3() {
        return dtMed1Time3;
    }

    public void setDtMed1Time3(String dtMed1Time3) {
        this.dtMed1Time3 = dtMed1Time3;
    }


    public String getMedTime() {
        return MedTime;
    }

    public void setMedTime(String medTime) {
        MedTime = medTime;
    }

    public String getDrName() {
        return DrName;
    }

    public void setDrName(String drName) {
        DrName = drName;
    }
}