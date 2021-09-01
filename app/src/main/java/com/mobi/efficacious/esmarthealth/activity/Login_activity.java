package com.mobi.efficacious.esmarthealth.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
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

public class Login_activity extends AppCompatActivity {
    FloatingActionButton registerfab;
    EditText et_mobile,et_password;
    Button bt_go;
    String Password,MobileNo;
    ProgressDialog progressDoalog;
    TextView tv_forgetpassword;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    int k;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        settings = getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        registerfab=(FloatingActionButton)findViewById(R.id.fab);
        et_mobile=(EditText)findViewById(R.id.et_mobile) ;
        et_password=(EditText)findViewById(R.id.et_password) ;
        tv_forgetpassword=(TextView)findViewById(R.id.tv_forgetpassword);
        bt_go=(Button) findViewById(R.id.bt_go) ;
        registerfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent=new Intent(Login_activity.this,Firebase_OTP.class);
                    intent.putExtra("PageName","Login");
                    startActivity(intent);
                    finish();
                }catch (Exception ex)
                {

                }

            }
        });
        tv_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent=new Intent(Login_activity.this,Firebase_OTP.class);
                    intent.putExtra("PageName","ForgetPassword");
                    startActivity(intent);
                    finish();
                }catch (Exception ex)
                {

                }
            }
        });
        bt_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(et_mobile.getText().toString().isEmpty()||et_password.getText().toString().isEmpty()) {
                        if (et_mobile.getText().toString().isEmpty()) {
                            et_mobile.setError("Mobile No is required");
                            return;
                        }
                        if (et_password.getText().toString().isEmpty()) {
                            et_password.setError("Password  is required");
                            return;
                        }
                    }else
                    {
                        MobileNo=et_mobile.getText().toString();
                        Password=et_password.getText().toString();
                        try{
                            Login();
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
    public void Login()
    {
        try{
            progressDoalog = new ProgressDialog(Login_activity.this);
            progressDoalog.setCancelable(false);
            progressDoalog.setCanceledOnTouchOutside(false);
            progressDoalog.setMessage("Login...");
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
                        Toast.makeText(Login_activity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        progressDoalog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<TblRegistrationPojo> call, Throwable t) {
                    Toast.makeText(Login_activity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
                    Intent intent=new Intent(Login_activity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception ex)
                {

                }

            }else
            {
                Toast.makeText(Login_activity.this, "Incorrect Usernmae Or Password", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception ex)
        {

        }

    }
    @Override
    public void onBackPressed() {
        k++;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                k = 0;
            }
        }, 1000);
        if (k == 1) {
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(Login_activity.this);
            builder.setMessage("Are you sure want to Exit?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }

    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    finish();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:

                    break;
            }
        }
    };
}
