package com.mobi.efficacious.esmarthealth.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.mobi.efficacious.esmarthealth.R;

public class Splash_activity extends Activity{
    static final String PREFRENCES_NAME = "myprefrences";
    static SharedPreferences settings;
    String MobileNo,Password,Name,ResId;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        settings = getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        try{
            MobileNo = settings.getString("TAG_Mobile", "");
            Password = settings.getString("TAG_password", "");
            Name = settings.getString("TAG_Name", "");
            ResId = settings.getString("TAG_ResId", "");
        }catch (Exception ex)
        {

        }

        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(2*1000);
                    if(MobileNo.contentEquals("")||Password.contentEquals("")||Name.contentEquals("")||ResId.contentEquals(""))
                    {
                        try{
                            Intent i=new Intent(getBaseContext(),Login_activity.class);
                            startActivity(i);
                            finish();
                        }catch (Exception ex)
                        {

                        }

                    }else
                    {
                        try{
                            Intent intent=new Intent(Splash_activity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }catch (Exception ex)
                        {

                        }


                    }


                    finish();
                } catch (Exception e) {
                }
            }
        };
        background.start();
    }
}
