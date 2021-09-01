package com.mobi.efficacious.esmarthealth.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.efficacious.esmarthealth.Interface.DataService;
import com.mobi.efficacious.esmarthealth.R;
import com.mobi.efficacious.esmarthealth.entity.Registration;
import com.mobi.efficacious.esmarthealth.entity.TblRegistrationPojo;
import com.mobi.efficacious.esmarthealth.webApi.RetrofitInstance;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration_activity extends AppCompatActivity {
    FloatingActionButton loginfab;
    EditText et_username,et_email_id,et_password;
    TextView et_Mobileno;
    Button btn_go;
    String MobileNo,FCMToken,Name,Address,EmailId,Password;
    ProgressDialog progressDoalog;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        settings = getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        loginfab=(FloatingActionButton)findViewById(R.id.fab);
        et_username=(EditText)findViewById(R.id.et_username);
        et_Mobileno=(TextView)findViewById(R.id.et_Mobileno) ;
        et_email_id=(EditText)findViewById(R.id.et_email_id) ;
        et_password=(EditText)findViewById(R.id.et_password) ;
        btn_go=(Button) findViewById(R.id.btn_go) ;
        try{
            Intent intent = getIntent();
            MobileNo = intent.getStringExtra("MobileNo");
            FCMToken=intent.getStringExtra("FCMToken");
            et_Mobileno.setText(MobileNo);
        }catch (Exception ex)
        {

        }

        loginfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent=new Intent(Registration_activity.this,Login_activity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception ex)
                {

                }

            }
        });
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(et_username.getText().toString().isEmpty()||et_password.getText().toString().isEmpty()) {
                        if (et_username.getText().toString().isEmpty()) {
                            et_username.setError("Name is required");
                            return;
                        }

                        if (et_password.getText().toString().isEmpty()) {
                            et_password.setError("Password  is required");
                            return;
                        }
                    }else
                    {
                        try{
                            Name=et_username.getText().toString();

                            EmailId=et_email_id.getText().toString();
                            Password=et_password.getText().toString();
                            Resgister();
                        }catch (Exception ex)
                        {

                        }

                    }
                }catch (Exception ex)
                {

                }

            }
        });
    }
    public void Resgister()
    {
        try{
            progressDoalog = new ProgressDialog(Registration_activity.this);
            progressDoalog.setCancelable(false);
            progressDoalog.setCanceledOnTouchOutside(false);
            progressDoalog.setMessage("Processing...");
            progressDoalog.show();
            Registration insert = new Registration(MobileNo,Name,EmailId,FCMToken,Password);
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Call<ResponseBody> call = service.Insert_new_Regisration(insert);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        String responsee = String.valueOf(response.code());
                        if (response.isSuccessful()) {
                            progressDoalog.dismiss();
                            Toast.makeText(Registration_activity.this, "Register Succesfully", Toast.LENGTH_SHORT).show();
                            try{
                                Userdata();
//                                Intent intent=new Intent(Registration_activity.this,MainActivity.class);
//                                startActivity(intent);
//                                finish();
                            }catch (Exception ex)
                            {

                            }

                        } else {
                            progressDoalog.dismiss();
                            Toast.makeText(Registration_activity.this, "Registeration Failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        progressDoalog.dismiss();
                        String msg = ex.getMessage();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDoalog.dismiss();
                    Toast.makeText(Registration_activity.this, "Something went wrong...Please try again!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception ex)
        {

        }

    }
    public void Userdata()
    {
        try{
            progressDoalog = new ProgressDialog(Registration_activity.this);
            progressDoalog.setCancelable(false);
            progressDoalog.setCanceledOnTouchOutside(false);
            progressDoalog.setMessage("Loading...");
            progressDoalog.show();
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Call<TblRegistrationPojo> call = service.LoginDetails("Select",MobileNo,Password);

            call.enqueue(new Callback<TblRegistrationPojo>() {
                @Override
                public void onResponse(Call<TblRegistrationPojo> call, Response<TblRegistrationPojo> response) {
                    String responsee= String.valueOf(response.code());
                    if (response.isSuccessful()) {
                        progressDoalog.dismiss();
                        try{
                            generateEmployeeList((ArrayList<Registration>) response.body().getRegistration());
                        }catch (Exception ex)
                        {

                        }
                    } else {
                        Toast.makeText(Registration_activity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        progressDoalog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<TblRegistrationPojo> call, Throwable t) {
                    Toast.makeText(Registration_activity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();
                }
            });
        }catch (Exception ex)
        {

        }
    }

    public  void generateEmployeeList(ArrayList<Registration> taskListDataList) {
        try {
            if((taskListDataList != null && !taskListDataList.isEmpty()))
            {
                String ResId=taskListDataList.get(0).getIntResId().toString();
                String Name=taskListDataList.get(0).getVchName().toString();
                String Mobile=taskListDataList.get(0).getVchMobileNo().toString();
                String password=taskListDataList.get(0).getVchPassword().toString();
                String fcmtoken=taskListDataList.get(0).getVchFCMToken().toString();
                settings.edit().putString("TAG_ResId", ResId).commit();
                settings.edit().putString("TAG_Name", Name).commit();
                settings.edit().putString("TAG_Mobile", Mobile).commit();
                settings.edit().putString("TAG_password", password).commit();
                settings.edit().putString("TAG_fcmtoken", fcmtoken).commit();
                try{
                    Intent intent=new Intent(Registration_activity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception ex)
                {

                }

            }else
            {
                Toast.makeText(Registration_activity.this, "Registeration Failed", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception ex)
        {

        }

    }
}
