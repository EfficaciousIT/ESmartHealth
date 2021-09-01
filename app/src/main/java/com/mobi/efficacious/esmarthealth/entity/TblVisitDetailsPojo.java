package com.mobi.efficacious.esmarthealth.entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblVisitDetailsPojo {

    @SerializedName("VisitDetails")
    @Expose
    private List<VisitDetail> visitDetails = null;

    public List<VisitDetail> getVisitDetails() {
        return visitDetails;
    }

    public void setVisitDetails(List<VisitDetail> visitDetails) {
        this.visitDetails = visitDetails;
    }

}