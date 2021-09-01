package com.mobi.efficacious.esmarthealth.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.mobi.efficacious.esmarthealth.webApi.RetrofitInstance;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Forget_Password_activity extends AppCompatActivity {
    EditText et_Cnfrm_password,et_password;
    Button bt_go;
    String Password,MobileNo,FCMToken="",Confrm_password;
    ProgressDialog progressDoalog;
    TextView tv_Mobile;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        settings = getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        tv_Mobile=(TextView) findViewById(R.id.et_mobile) ;
        et_password=(EditText)findViewById(R.id.et_password) ;
        et_Cnfrm_password=(EditText) findViewById(R.id.et_confrm_password);
        bt_go=(Button) findViewById(R.id.bt_go) ;
        try{
            Intent intent = getIntent();
            MobileNo = intent.getStringExtra("MobileNo");
            FCMToken=intent.getStringExtra("FCMToken");
            tv_Mobile.setText(MobileNo);
        }catch (Exception ex)
        {

        }
        bt_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(et_Cnfrm_password.getText().toString().isEmpty()||et_password.getText().toString().isEmpty()) {
                        if (et_Cnfrm_password.getText().toString().isEmpty()) {
                            et_Cnfrm_password.setError("Password  is required");
                            return;
                        }

                        if (et_password.getText().toString().isEmpty()) {
                            et_password.setError("Password  is required");
                            return;
                        }
                    }else
                    {
                        try{
                            MobileNo=tv_Mobile.getText().toString();
                            Password=et_password.getText().toString();
                            Confrm_password=et_Cnfrm_password.getText().toString();
                            if(Password.contentEquals(Confrm_password))
                            {
                                UpdatePassword();
                            }else
                            {
                                Toast.makeText(Forget_Password_activity.this, "Your password and confirmation password do not match", Toast.LENGTH_SHORT).show();
                            }

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
    public void UpdatePassword()
    {
        try{
            progressDoalog = new ProgressDialog(Forget_Password_activity.this);
            progressDoalog.setCancelable(false);
            progressDoalog.setCanceledOnTouchOutside(false);
            progressDoalog.setMessage("Processing...");
            progressDoalog.show();
            Registration insert = new Registration(MobileNo,FCMToken,Password);
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Call<ResponseBody> call = service.Update_password(insert);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        String responsee = String.valueOf(response.code());
                        if (response.isSuccessful()) {
                            progressDoalog.dismiss();
                            Toast.makeText(Forget_Password_activity.this, "Password Reset Successfully", Toast.LENGTH_SHORT).show();
                            try{

                                AlertDialog.Builder builder = new AlertDialog.Builder(Forget_Password_activity.this);
                                builder.setMessage("Password Reset Successfully "+"\n"+ "Please Login Again.").setPositiveButton("Ok", dialogClickListener).show();
                            }catch (Exception ex)
                            {

                            }

                        } else {
                            progressDoalog.dismiss();
                            Toast.makeText(Forget_Password_activity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        progressDoalog.dismiss();
                        Toast.makeText(Forget_Password_activity.this, "Something went wrong...Please try again!", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDoalog.dismiss();
                    Toast.makeText(Forget_Password_activity.this, "Something went wrong...Please try again!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception ex)
        {

        }

    }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    Intent intent=new Intent(Forget_Password_activity.this,Login_activity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
}
