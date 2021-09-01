package com.mobi.efficacious.esmarthealth.entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblDoctorDetailsPojo {

    @SerializedName("DoctorDetail")
    @Expose
    private List<DoctorDetail> doctorDetails = null;

    public List<DoctorDetail> getDoctorDetails() {
        return doctorDetails;
    }

    public void setDoctorDetails(List<DoctorDetail> doctorDetails) {
        this.doctorDetails = doctorDetails;
    }

}