package com.mobi.efficacious.esmarthealth.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobi.efficacious.esmarthealth.Interface.DataService;
import com.mobi.efficacious.esmarthealth.R;
import com.mobi.efficacious.esmarthealth.adapter.Dr_detail_adapter;
import com.mobi.efficacious.esmarthealth.entity.DoctorDetail;
import com.mobi.efficacious.esmarthealth.entity.TblDoctorDetailsPojo;
import com.mobi.efficacious.esmarthealth.webApi.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FamilyDr_detailList_fragment  extends Fragment {
    View myview;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String intResId;
    ProgressDialog progressDoalog;
    public static Dr_detail_adapter adapter;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview=inflater.inflate(R.layout.recyclerview_layout,null);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        intResId = settings.getString("TAG_ResId", "");
        recyclerView = (RecyclerView)myview.findViewById(R.id.recyclerView_tasklist);
        AllData();
        return myview;
    }
    public void AllData() {
        try {
            progressDoalog = new ProgressDialog(getActivity());
            progressDoalog.setCancelable(false);
            progressDoalog.setCanceledOnTouchOutside(false);
            progressDoalog.setMessage("Loading...");
            progressDoalog.show();
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Call<TblDoctorDetailsPojo> call = service.getDoctorDetails(intResId,"SelectDoctor");

            call.enqueue(new Callback<TblDoctorDetailsPojo>() {
                @Override
                public void onResponse(Call<TblDoctorDetailsPojo> call, Response<TblDoctorDetailsPojo> response) {
                    String responsee = String.valueOf(response.code());
                    if (response.isSuccessful()) {
                        progressDoalog.dismiss();
                        try {
                            generateEmployeeList((ArrayList<DoctorDetail>) response.body().getDoctorDetails());
                        } catch (Exception ex) {
                            ex.getMessage();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        progressDoalog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<TblDoctorDetailsPojo> call, Throwable t) {
                    Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();
                }
            });
        } catch (Exception ex) {

        }
    }
    public  void generateEmployeeList(ArrayList<DoctorDetail> drDataList) {
        if ((drDataList != null && !drDataList.isEmpty())) {
            adapter = new Dr_detail_adapter(drDataList,getActivity());

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(adapter);
        }


    }
}
