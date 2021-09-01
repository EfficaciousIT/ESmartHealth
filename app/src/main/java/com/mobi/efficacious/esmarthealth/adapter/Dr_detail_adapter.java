package com.mobi.efficacious.esmarthealth.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.efficacious.esmarthealth.R;
import com.mobi.efficacious.esmarthealth.activity.MainActivity;
import com.mobi.efficacious.esmarthealth.entity.DoctorDetail;
import com.mobi.efficacious.esmarthealth.fragment.FamilyDr_detailUpdate_fragment;

import java.util.ArrayList;

public class Dr_detail_adapter extends RecyclerView.Adapter<Dr_detail_adapter.DoctorViewHolder> {

        private ArrayList<DoctorDetail> dataList;
        Context mcontext;
        public Dr_detail_adapter(ArrayList<DoctorDetail> dataList, Context context) {
            this.dataList = dataList;
            this.mcontext=context;
        }

        @Override
        public DoctorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.dr_detali_list, parent, false);
            return new DoctorViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DoctorViewHolder holder, final int position) {
            holder.tv_drName.setText("DR Name : "+dataList.get(position).getVchName());
            holder.tv_dr_gender.setText("Gender : "+dataList.get(position).getVchGender());
            holder.tv_MobileNo.setText("Mobile No : "+dataList.get(position).getVchMobileNo());
            holder.tv_dr_qualification.setText("Qualification : "+dataList.get(position).getVchQualification());
            holder.tv_dr_Timing.setText("Timing : "+dataList.get(position).getDtDrFromtiming()+" - "+dataList.get(position).getDtDrTotiming());
            holder.callimage.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    try {
                        if (dataList.get(position).getVchMobileNo().contentEquals("") || dataList.get(position).getVchMobileNo().isEmpty()) {
                            Toast.makeText(mcontext, "Mobile No. not available", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dataList.get(position).getVchMobileNo().toString()));
                           mcontext.startActivity(intent);
                        }
                    } catch (Exception ex) {

                    }


                }
            });
            holder.edit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String IntDOCId=String.valueOf(dataList.get(position).getIntDocId());
                    FamilyDr_detailUpdate_fragment familyDr_detailUpdate_fragment = new FamilyDr_detailUpdate_fragment();
                    Bundle args = new Bundle();
                    args.putString("intDocId",IntDOCId);
                    args.putString("vchName",dataList.get(position).getVchName());
                    args.putString("vchGender",dataList.get(position).getVchGender());
                    args.putString("vchAddress",dataList.get(position).getVchAddress());
                    args.putString("vchMobileNo",dataList.get(position).getVchMobileNo());
                    args.putString("vchEmailId",dataList.get(position).getVchEmailId());
                    args.putString("vchQualification",dataList.get(position).getVchQualification());
                    args.putString("dtDrFromtiming",dataList.get(position).getDtDrFromtiming());
                    args.putString("dtDrTotiming",dataList.get(position).getDtDrTotiming());
                    familyDr_detailUpdate_fragment.setArguments(args);
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, familyDr_detailUpdate_fragment).commitAllowingStateLoss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        class DoctorViewHolder extends RecyclerView.ViewHolder {

            TextView tv_drName,tv_dr_gender, tv_MobileNo, tv_dr_qualification,tv_dr_Timing;
            ImageButton callimage;
            ImageButton edit_btn;
            DoctorViewHolder(View itemView) {
                super(itemView);
                tv_drName = (TextView) itemView.findViewById(R.id.tv_drName);
                tv_dr_gender = (TextView) itemView.findViewById(R.id.tv_dr_gender);
                tv_MobileNo = (TextView) itemView.findViewById(R.id.tv_MobileNo);
                tv_dr_qualification = (TextView) itemView.findViewById(R.id.tv_dr_qualification);
                tv_dr_Timing = (TextView) itemView.findViewById(R.id.tv_dr_Timing);
                callimage=(ImageButton)itemView.findViewById(R.id.callimage);
                edit_btn=(ImageButton) itemView.findViewById(R.id.edit_btn);
            }
        }




}
