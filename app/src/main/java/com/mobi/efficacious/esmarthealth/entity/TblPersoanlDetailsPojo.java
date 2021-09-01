package com.mobi.efficacious.esmarthealth.entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPersoanlDetailsPojo {

    @SerializedName("PersoanlDetails")
    @Expose
    private List<PersoanlDetail> persoanlDetails = null;

    public List<PersoanlDetail> getPersoanlDetails() {
        return persoanlDetails;
    }

    public void setPersoanlDetails(List<PersoanlDetail> persoanlDetails) {
        this.persoanlDetails = persoanlDetails;
    }

}