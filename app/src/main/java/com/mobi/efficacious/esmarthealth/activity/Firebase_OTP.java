package com.mobi.efficacious.esmarthealth.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mobi.efficacious.esmarthealth.Interface.DataService;
import com.mobi.efficacious.esmarthealth.R;
import com.mobi.efficacious.esmarthealth.entity.Registration;
import com.mobi.efficacious.esmarthealth.entity.TblRegistrationPojo;
import com.mobi.efficacious.esmarthealth.webApi.RetrofitInstance;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Firebase_OTP extends AppCompatActivity {
    FloatingActionButton loginfab;
    EditText et_Mobileno,et_OTP;
    Button btn_send,btn_verification,btn_resend;
    FirebaseAuth mAuth;
    String codeSent,PageName;
    String Firebasetoken="";
    ProgressDialog progressDoalog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase_otp);
        mAuth = FirebaseAuth.getInstance();
        try{
            loginfab=(FloatingActionButton)findViewById(R.id.fab);
            et_Mobileno=(EditText)findViewById(R.id.et_Mobileno);
            et_OTP=(EditText)findViewById(R.id.et_OTP);
            btn_send=(Button) findViewById(R.id.btn_send);
            btn_resend=(Button) findViewById(R.id.btn_resend);
            btn_verification=(Button) findViewById(R.id.btn_verification);
            btn_verification.setVisibility(View.GONE);
            et_OTP.setVisibility(View.GONE);
            btn_resend.setVisibility(View.GONE);
            Intent intent = getIntent();
            PageName = intent.getStringExtra("PageName");
        }catch (Exception ex)
        {

        }

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String Mobile=et_Mobileno.getText().toString();;
                    btn_verification.setVisibility(View.VISIBLE);
                    et_OTP.setVisibility(View.VISIBLE);
                    btn_send.setVisibility(View.GONE);
                    btn_resend.setVisibility(View.VISIBLE);
                    et_Mobileno.setKeyListener(null);
                    et_Mobileno.setText(Mobile.toString());

                        Verification();


                }catch (Exception ex)
                {

                }

            }
        });
        btn_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    progressDoalog = new ProgressDialog(Firebase_OTP.this);
                    progressDoalog.setCancelable(false);
                    progressDoalog.setCanceledOnTouchOutside(false);
                    progressDoalog.setMessage("Processing...");
                    progressDoalog.show();
                    verifySignInCode();

                }catch (Exception ex)
                {

                }


            }
        });
        loginfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent=new Intent(Firebase_OTP.this,Login_activity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception ex)
                {

                }

            }
        });
        btn_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String buttontext=btn_resend.getText().toString();
                    if(buttontext.contentEquals("Resend"))
                    {

                            Verification();


                    }else
                    {

                    }
                }catch (Exception ex)
                {

                }

            }
        });
    }
    private void verifySignInCode(){
        try{
            String code = et_OTP.getText().toString();
            if(code.isEmpty())
            {
                progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(),
                        "Incorrect Verification Code ", Toast.LENGTH_LONG).show();
            }else
            {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
                signInWithPhoneAuthCredential(credential);
            }
        }catch (Exception ex)
        {
            progressDoalog.dismiss();
            Toast.makeText(getApplicationContext(),
                    "Something went wrong...Please try again!", Toast.LENGTH_LONG).show();

        }


    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try{
                            if (task.isSuccessful()) {
                                //here you can open new activity
                                progressDoalog.dismiss();
                                Toast.makeText(getApplicationContext(),
                                        "Verified Successfully", Toast.LENGTH_LONG).show();
                                Firebasetoken = FirebaseInstanceId.getInstance().getToken();
                                if(PageName.contentEquals("Login"))
                                {
                                    Intent intent=new Intent(Firebase_OTP.this,Registration_activity.class);
                                    intent.putExtra("MobileNo",et_Mobileno.getText().toString());
                                    intent.putExtra("FCMToken",Firebasetoken);
                                    startActivity(intent);
                                    finish();
                                }else
                                {
                                    Intent intent=new Intent(Firebase_OTP.this,Forget_Password_activity.class);
                                    intent.putExtra("MobileNo",et_Mobileno.getText().toString());
                                    intent.putExtra("FCMToken",Firebasetoken);
                                    startActivity(intent);
                                    finish();
                                }

                            } else {
                                progressDoalog.dismiss();
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(getApplicationContext(),
                                            "Incorrect Verification Code ", Toast.LENGTH_LONG).show();
                                }
                            }
                        }catch (Exception ex)
                        {

                            progressDoalog.dismiss();
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong...Please try again!", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }
    private void sendVerificationCode(){
        try{
            String phone = et_Mobileno.getText().toString();

            if(phone.isEmpty()){
                KeyListener variable;
                variable = et_Mobileno.getKeyListener();
                et_Mobileno.setError("Phone number is required");
                et_Mobileno.requestFocus();
                btn_verification.setVisibility(View.GONE);
                et_OTP.setVisibility(View.GONE);
                btn_resend.setVisibility(View.GONE);
                btn_send.setVisibility(View.VISIBLE);
                et_Mobileno.setKeyListener(variable);
                return;
            }

            if(phone.length() < 10 ){
                et_Mobileno.setError("Please enter a valid phone");
                et_Mobileno.requestFocus();
                KeyListener variable;
                variable = et_Mobileno.getKeyListener();
                btn_verification.setVisibility(View.GONE);
                et_OTP.setVisibility(View.GONE);
                btn_resend.setVisibility(View.GONE);
                btn_send.setVisibility(View.VISIBLE);
                et_Mobileno.setKeyListener(variable);
                return;
            }


            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phone,        // Phone number to verify
                    2,                 // Timeout duration
                    TimeUnit.MINUTES,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks);
        }catch (Exception ex)
        {

        }
            // OnVerificationStateChangedCallbacks
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
           // Toast.makeText(Firebase_OTP.this, "VerificationCompleted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
          //  Toast.makeText(Firebase_OTP.this, "VerificationFailed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            try{
                codeSent = s;
            }catch (Exception ex)
            {

            }

        }
    };

    public void Verification()
    {
        try{
            new CountDownTimer(180000, 1000) {

                public void onTick(long millisUntilFinished) {
                    try{
                        btn_resend.setText(" seconds remaining " + millisUntilFinished / 1000);
                    }catch (Exception ex)
                    {

                    }

                    //here you can have your logic to set text to edittext
                }

                public void onFinish() {
                    btn_resend.setText("Resend");
                }

            }.start();
            String phone = et_Mobileno.getText().toString();
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Call<TblRegistrationPojo> call = service.verification("Verification",phone);


            call.enqueue(new Callback<TblRegistrationPojo>() {
                @Override
                public void onResponse(Call<TblRegistrationPojo> call, Response<TblRegistrationPojo> response) {
                    String responsee= String.valueOf(response.code());
                    if (response.isSuccessful()) {
                        try{
                            generateEmployeeList((ArrayList<Registration>) response.body().getRegistration());
                        }catch (Exception ex)
                        {

                        }

                    } else {
                        Toast.makeText(Firebase_OTP.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<TblRegistrationPojo> call, Throwable t) {
                    Toast.makeText(Firebase_OTP.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
                String count=taskListDataList.get(0).getCount().toString();
                if(PageName.contentEquals("Login"))
                {
                    if(!count.contentEquals("0"))
                    {
                        Toast.makeText(Firebase_OTP.this, "Already Register", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        try{
                            sendVerificationCode();
                        }catch (Exception ex)
                        {

                        }

                    }
                }else
                {
                    if(!count.contentEquals("0"))
                    {
                        try{
                            sendVerificationCode();
                        }catch (Exception ex)
                        {

                        }
                    }else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Firebase_OTP.this);
                        builder.setMessage("This Mobile No is not Registered").setPositiveButton("Ok", dialogClickListener).show();

                    }
                }

            }

        }catch (Exception ex)
        {

        }

    }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    Intent intent=new Intent(Firebase_OTP.this,Login_activity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
}
