package com.mobi.efficacious.esmarthealth.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.efficacious.esmarthealth.Interface.DataService;
import com.mobi.efficacious.esmarthealth.R;
import com.mobi.efficacious.esmarthealth.entity.TblVisitDetailsPojo;
import com.mobi.efficacious.esmarthealth.entity.VisitDetail;
import com.mobi.efficacious.esmarthealth.webApi.RetrofitInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {
    String drVisitId,DrId,intResId;
    TextView tv_med1,tv_med2,tv_med3,tv_med4,tv_med5;
    ProgressDialog progressDoalog;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_alarm);
        settings = getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        intResId = settings.getString("TAG_ResId", "");
        Intent inteent = getIntent();
        drVisitId = inteent.getStringExtra("intDrVisitId");
        DrId = inteent.getStringExtra("intDrId");
        tv_med1=(TextView)findViewById(R.id.tv_med1);
        tv_med2=(TextView)findViewById(R.id.tv_med2);
        tv_med3=(TextView)findViewById(R.id.tv_med3);
        tv_med4=(TextView)findViewById(R.id.tv_med4);
        tv_med5=(TextView)findViewById(R.id.tv_med5);
        Medname();
    }
    public void Medname()
    {
        try
        {
            progressDoalog = new ProgressDialog(this);
            progressDoalog.setCancelable(false);
            progressDoalog.setCanceledOnTouchOutside(false);
            progressDoalog.setMessage("Loading...");
            progressDoalog.show();
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Call<TblVisitDetailsPojo> call = service.getdrVisitDetails("SelectvisitDetail",drVisitId,DrId,intResId);

            call.enqueue(new Callback<TblVisitDetailsPojo>() {
                @Override
                public void onResponse(Call<TblVisitDetailsPojo> call, Response<TblVisitDetailsPojo> response) {
                    String responsee= String.valueOf(response.code());
                    if (response.isSuccessful()) {
                        progressDoalog.dismiss();
                        try{
                            generateMedList((ArrayList<VisitDetail>) response.body().getVisitDetails());
                        }catch (Exception ex)
                        {

                        }
                    } else {
                        Toast.makeText(NotificationActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        progressDoalog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<TblVisitDetailsPojo> call, Throwable t) {
                    Toast.makeText(NotificationActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();
                }
            });



        }catch (Exception ex)
        {

        }

    }
    public  void generateMedList(ArrayList<VisitDetail> taskListDataList) {
        try {

            if((taskListDataList != null && !taskListDataList.isEmpty()))
            {
                String Med1=taskListDataList.get(0).getVchMedicine1().toString();
                String Med2=taskListDataList.get(0).getVchMedicine2().toString();
                String Med3=taskListDataList.get(0).getVchMedicine3().toString();
                String Med4=taskListDataList.get(0).getVchMedicine4().toString();
                String Med5=taskListDataList.get(0).getVchMedicine5().toString();
                if(!Med1.contentEquals(""))
                {
                    tv_med1.setVisibility(View.VISIBLE);
                    tv_med1.setText(Med1);
                }else
                {
                    tv_med1.setVisibility(View.GONE);
                }

                if(!Med2.contentEquals(""))
                {
                    tv_med2.setVisibility(View.VISIBLE);
                    tv_med2.setText(Med2);
                }else
                {
                    tv_med2.setVisibility(View.GONE);
                }

                if(!Med3.contentEquals(""))
                {
                    tv_med3.setVisibility(View.VISIBLE);
                    tv_med3.setText(Med3);
                }else
                {
                    tv_med3.setVisibility(View.GONE);
                }

                if(!Med4.contentEquals(""))
                {
                    tv_med4.setVisibility(View.VISIBLE);
                    tv_med4.setText(Med4);
                }else
                {
                    tv_med4.setVisibility(View.GONE);
                }

                if(!Med5.contentEquals(""))
                {
                    tv_med5.setVisibility(View.VISIBLE);
                    tv_med5.setText(Med5);
                }else
                {
                    tv_med5.setVisibility(View.GONE);
                }

            }

        }catch (Exception ex)
        {
ex.getMessage();
        }

    }
}
