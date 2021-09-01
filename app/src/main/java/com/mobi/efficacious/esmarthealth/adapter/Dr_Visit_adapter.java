package com.mobi.efficacious.esmarthealth.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobi.efficacious.esmarthealth.Interface.DataService;
import com.mobi.efficacious.esmarthealth.R;
import com.mobi.efficacious.esmarthealth.activity.MainActivity;
import com.mobi.efficacious.esmarthealth.entity.VisitDetail;
import com.mobi.efficacious.esmarthealth.fragment.DoctorVisitUpdate_fragment;
import com.mobi.efficacious.esmarthealth.fragment.Zoom_fragment;
import com.mobi.efficacious.esmarthealth.tab.DoctorVisit_tab;
import com.mobi.efficacious.esmarthealth.webApi.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dr_Visit_adapter extends RecyclerView.Adapter<Dr_Visit_adapter.DoctorViewHolder> {

    private ArrayList<VisitDetail> dataList;
    Context mcontext;
    String intDr_visitId,intDocId,int_ResId,vchAlert;
    ProgressDialog progressDoalog;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    public Dr_Visit_adapter(ArrayList<VisitDetail> dataList, Context context) {
        this.dataList = dataList;
        this.mcontext = context;
    }

    @Override
    public DoctorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.dr_visit_list_adapter, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DoctorViewHolder holder, final int position) {
        if(dataList.get(position).getDtNxtVisitdate().contentEquals("01/01/1900"))
        {
            holder.tv_nxtdate.setVisibility(View.GONE);
        }else
        {
            holder.tv_nxtdate.setVisibility(View.VISIBLE);
            holder.tv_nxtdate.setText("Next Date : "+dataList.get(position).getDtNxtVisitdate());
        }

        holder.tv_visitdate.setText("Visit Date : "+dataList.get(position).getDtVisitdate());

        if(dataList.get(position).getVchMedicine1().contentEquals(""))
        {
            holder.Med1.setVisibility(View.GONE);
        }else
        {
            holder.Med1.setVisibility(View.VISIBLE);
            holder.Med1.setText("Medicine : "+dataList.get(position).getVchMedicine1());
        }

        if(dataList.get(position).getVchMedicine2().contentEquals(""))
        {
            holder.Med2.setVisibility(View.GONE);
        }else
        {
            holder.Med2.setVisibility(View.VISIBLE);
            holder.Med2.setText("Medicine : "+dataList.get(position).getVchMedicine2());
        }

        if(dataList.get(position).getVchMedicine3().contentEquals(""))
        {
            holder.Med3.setVisibility(View.GONE);
        }else
        {
            holder.Med3.setVisibility(View.VISIBLE);
            holder.Med3.setText("Medicine : "+dataList.get(position).getVchMedicine3());
        }

        if(dataList.get(position).getVchMedicine4().contentEquals(""))
        {
            holder.Med4.setVisibility(View.GONE);
        }else
        {
            holder.Med4.setVisibility(View.VISIBLE);
            holder.Med4.setText("Medicine : "+dataList.get(position).getVchMedicine4());
        }

        if(dataList.get(position).getVchMedicine5().contentEquals(""))
        {
            holder.Med5.setVisibility(View.GONE);
        }else
        {
            holder.Med5.setVisibility(View.VISIBLE);
            holder.Med5.setText("Medicine : "+dataList.get(position).getVchMedicine5());
        }

        if(dataList.get(position).getVchAttachment1().contentEquals(""))
        {
            holder.attachmnt1.setVisibility(View.GONE);
        }else
        {
            holder.attachmnt1.setVisibility(View.VISIBLE);
            String url= RetrofitInstance.Image_URL+dataList.get(position).getVchAttachment1()+"";
            Glide.with(mcontext)
                    .load(url) // image url
                    .error(R.mipmap.profile)
                    .centerCrop()// any image in case of error
                    .into(holder.attachmnt1);
        }

        if(dataList.get(position).getVchAttachment2().contentEquals(""))
        {
            holder.attachmnt2.setVisibility(View.GONE);
        }else
        {
            holder.attachmnt2.setVisibility(View.VISIBLE);
            String url= RetrofitInstance.Image_URL+dataList.get(position).getVchAttachment2()+"";
            Glide.with(mcontext)
                    .load(url) // image url
                    .error(R.mipmap.profile)
                    .centerCrop()// any image in case of error
                    .into(holder.attachmnt2);
        }
        if(dataList.get(position).getVchAttachment3().contentEquals(""))
        {
            holder.attachmnt3.setVisibility(View.GONE);
        }else
        {
            holder.attachmnt3.setVisibility(View.VISIBLE);
            String url= RetrofitInstance.Image_URL+dataList.get(position).getVchAttachment3()+"";
            Glide.with(mcontext)
                    .load(url) // image url
                    .error(R.mipmap.profile)
                    .centerCrop()
                    .into(holder.attachmnt3);
        }
        if(dataList.get(position).getVchAttachment4().contentEquals(""))
        {
            holder.attachmnt4.setVisibility(View.GONE);
        }else
        {
            holder.attachmnt4.setVisibility(View.VISIBLE);
            String url= RetrofitInstance.Image_URL+dataList.get(position).getVchAttachment4()+"";
            Glide.with(mcontext)
                    .load(url) // image url
                    .error(R.mipmap.profile)
                    .centerCrop()
                    .into(holder.attachmnt4);
        }
        if(!dataList.get(position).getDtMed1Time1().contentEquals("00:00")||!dataList.get(position).getDtMed1Time2().contentEquals("00:00")||!dataList.get(position).getDtMed1Time3().contentEquals("00:00"))
        {
            if(dataList.get(position).getVchAlert().contentEquals("1") )
            {
                holder.alarm_btn.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF,Color.GREEN));
            }else
            {
                holder.alarm_btn.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF,Color.RED));
            }
        }else
        {
            holder.alarm_btn.setVisibility(View.GONE);
        }

        holder.alarm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    settings =mcontext.getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
                    int_ResId = settings.getString("TAG_ResId", "");
                    if(dataList.get(position).getVchAlert().contentEquals("0"))
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                        builder.setMessage("Are you sure want to set Alarm ?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();
                    }else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                        builder.setMessage("Are you sure want to Cancel Alarm ?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();
                    }
                }catch (Exception ex)
                {

                }


            }
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try
                    {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                if(dataList.get(position).getVchAlert().contentEquals("0"))
                                {
                                    vchAlert="1";
                                    intDocId= String.valueOf(dataList.get(position).getIntDocId());
                                    intDr_visitId= String.valueOf(dataList.get(position).getIntDrVisitId());
                                    UpdateAlarmstatus(intDr_visitId,intDocId,int_ResId,vchAlert);
                                    holder.alarm_btn.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF,Color.GREEN));
                                }else
                                {
                                    vchAlert="0";
                                    intDocId= String.valueOf(dataList.get(position).getIntDocId());
                                    intDr_visitId= String.valueOf(dataList.get(position).getIntDrVisitId());
                                    UpdateAlarmstatus(intDr_visitId,intDocId,int_ResId,vchAlert);
                                    holder.alarm_btn.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF,Color.RED));
                                }
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }catch (Exception ex)
                    {

                    }

                }
            };
        });
        holder.attachmnt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String url=RetrofitInstance.Image_URL+String.valueOf(dataList.get(position).getVchAttachment1());
                try
                {
                    Intent intent = new Intent(mcontext, Zoom_fragment.class);
                    intent.putExtra("path", String.valueOf(dataList.get(position).getVchAttachment1()));
                    mcontext.startActivity(intent);
                }catch (Exception ex)
                {

                }
            }
        });
        holder.attachmnt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String url=RetrofitInstance.Image_URL+String.valueOf(dataList.get(position).getVchAttachment1());
                try
                {
                    Intent intent = new Intent(mcontext, Zoom_fragment.class);
                    intent.putExtra("path", String.valueOf(dataList.get(position).getVchAttachment2()));
                    mcontext.startActivity(intent);
                }catch (Exception ex)
                {

                }
            }
        });
        holder.attachmnt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String url=RetrofitInstance.Image_URL+String.valueOf(dataList.get(position).getVchAttachment1());
                try
                {
                    Intent intent = new Intent(mcontext, Zoom_fragment.class);
                    intent.putExtra("path", String.valueOf(dataList.get(position).getVchAttachment3()));
                    mcontext.startActivity(intent);
                }catch (Exception ex)
                {

                }
            }
        });
        holder.attachmnt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String url=RetrofitInstance.Image_URL+String.valueOf(dataList.get(position).getVchAttachment1());
                try
                {
                    Intent intent = new Intent(mcontext, Zoom_fragment.class);
                    intent.putExtra("path", String.valueOf(dataList.get(position).getVchAttachment4()));
                    mcontext.startActivity(intent);
                }catch (Exception ex)
                {

                }
            }
        });
        holder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    DoctorVisitUpdate_fragment doctorVisitUpdate_fragment = new DoctorVisitUpdate_fragment();
                    Bundle args = new Bundle();
                    args.putString("intDr_visitId",String.valueOf(dataList.get(position).getIntDrVisitId()));
                    args.putString("Medicine1",String.valueOf(dataList.get(position).getVchMedicine1()));
                    args.putString("Medicine2",String.valueOf(dataList.get(position).getVchMedicine2()));
                    args.putString("Medicine3",String.valueOf(dataList.get(position).getVchMedicine3()));
                    args.putString("Medicine4",String.valueOf(dataList.get(position).getVchMedicine4()));
                    args.putString("Medicine5",String.valueOf(dataList.get(position).getVchMedicine5()));
                    args.putString("Dr_Id",String.valueOf(dataList.get(position).getIntDocId()));
                    args.putString("vchDrName",String.valueOf(dataList.get(position).getDrName()));
                    args.putString("Visit_date",String.valueOf(dataList.get(position).getDtVisitdate()));
                    args.putString("AlertStatus",String.valueOf(dataList.get(position).getVchAlert()));
                    args.putString("NxtVisit_date",String.valueOf(dataList.get(position).getDtNxtVisitdate()));
                    args.putString("description",String.valueOf(dataList.get(position).getVchDescription()));
                    args.putString("Med1Time1",String.valueOf(dataList.get(position).getDtMed1Time1()));
                    args.putString("Med1Time2",String.valueOf(dataList.get(position).getDtMed1Time2()));
                    args.putString("Med1Time3",String.valueOf(dataList.get(position).getDtMed1Time3()));
                    args.putString("Attachment1",String.valueOf(dataList.get(position).getVchAttachment1()));
                    args.putString("Attachment2",String.valueOf(dataList.get(position).getVchAttachment2()));
                    args.putString("Attachment3",String.valueOf(dataList.get(position).getVchAttachment3()));
                    args.putString("Attachment4",String.valueOf(dataList.get(position).getVchAttachment4()));
                    doctorVisitUpdate_fragment.setArguments(args);
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, doctorVisitUpdate_fragment).commitAllowingStateLoss();
                }catch (Exception ex)
                {

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class DoctorViewHolder extends RecyclerView.ViewHolder {

        TextView DRName, tv_visitdate, tv_nxtdate, Med1, Med2, Med3, Med4, Med5;
        CircleImageView attachmnt1, attachmnt2, attachmnt3, attachmnt4;
        ImageButton edit_btn,alarm_btn;
        DoctorViewHolder(View itemView) {
            super(itemView);
            DRName = (TextView) itemView.findViewById(R.id.DRName);
            tv_visitdate = (TextView) itemView.findViewById(R.id.tv_visitdate);
            tv_nxtdate = (TextView) itemView.findViewById(R.id.tv_nxtdate);
            Med1 = (TextView) itemView.findViewById(R.id.Med1);
            Med2 = (TextView) itemView.findViewById(R.id.Med2);
            Med3 = (TextView) itemView.findViewById(R.id.Med3);
            Med4 = (TextView) itemView.findViewById(R.id.Med4);
            Med5 = (TextView) itemView.findViewById(R.id.Med5);
            attachmnt1 = (CircleImageView) itemView.findViewById(R.id.attachmnt1);
            attachmnt2 = (CircleImageView) itemView.findViewById(R.id.attachmnt2);
            attachmnt3 = (CircleImageView) itemView.findViewById(R.id.attachmnt3);
            attachmnt4 = (CircleImageView) itemView.findViewById(R.id.attachmnt4);
            edit_btn=(ImageButton)itemView.findViewById(R.id.edit_btn);
            alarm_btn=(ImageButton)itemView.findViewById(R.id.alarm_btn);
        }


    }
    public void UpdateAlarmstatus(String IntDr_visitId,String IntDocId,String Int_ResId,String vchalert) {
        try {
            progressDoalog = new ProgressDialog(mcontext);
            progressDoalog.setCancelable(false);
            progressDoalog.setCanceledOnTouchOutside(false);
            progressDoalog.setMessage("Processing...");
            progressDoalog.show();
            VisitDetail update = new VisitDetail(Integer.parseInt(IntDr_visitId),Integer.parseInt(IntDocId),Integer.parseInt(Int_ResId),vchalert);
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Call<ResponseBody> call = service.Update_Doctorvisit("UpdateAlarmStatus",update);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        String responsee = String.valueOf(response.code());
                        if (response.isSuccessful()) {
                            progressDoalog.dismiss();
                            Toast.makeText(mcontext, "Alarm set successfully", Toast.LENGTH_SHORT).show();
                            try
                            {
                                DoctorVisit_tab doctorVisit_tab = new DoctorVisit_tab();
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, doctorVisit_tab).commitAllowingStateLoss();
                            }catch (Exception ex)
                            {

                            }
                        } else {
                            progressDoalog.dismiss();
                            Toast.makeText(mcontext, "Something went wrong...Please try again!", Toast.LENGTH_SHORT).show();
                            try
                            {
                                DoctorVisit_tab doctorVisit_tab = new DoctorVisit_tab();
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, doctorVisit_tab).commitAllowingStateLoss();
                            }catch (Exception ex)
                            {

                            }
                        }
                    } catch (Exception ex) {
                        progressDoalog.dismiss();
                        Toast.makeText(mcontext, "Something went wrong...Please try again!", Toast.LENGTH_SHORT).show();
                        try
                        {
                            DoctorVisit_tab doctorVisit_tab = new DoctorVisit_tab();
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, doctorVisit_tab).commitAllowingStateLoss();
                        }catch (Exception e)
                        {

                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDoalog.dismiss();
                    Toast.makeText(mcontext, "Something went wrong...Please try again!", Toast.LENGTH_SHORT).show();
                    try
                    {
                        DoctorVisit_tab doctorVisit_tab = new DoctorVisit_tab();
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, doctorVisit_tab).commitAllowingStateLoss();
                    }catch (Exception e)
                    {

                    }
                }
            });
        } catch (Exception ex) {

        }
    }
}
