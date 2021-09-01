package com.mobi.efficacious.esmarthealth.fragment;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mobi.efficacious.esmarthealth.Interface.DataService;
import com.mobi.efficacious.esmarthealth.R;
import com.mobi.efficacious.esmarthealth.activity.MainActivity;
import com.mobi.efficacious.esmarthealth.entity.DoctorDetail;
import com.mobi.efficacious.esmarthealth.tab.FamilyDr_Tab;
import com.mobi.efficacious.esmarthealth.webApi.RetrofitInstance;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FamilyDr_detailEntry_fragment  extends Fragment {
    View myview;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String intResId;
    EditText et_DrName,address,et_DrMobileNo,et_Dr_emailId,et_Dr_Qualification;
    RadioGroup radioGroup_gender;
    TextView FromTiming,ToTiming;
    RadioButton radiobuttonMale,radioButtonFemale;
    Button submit;
    String Gender_Selection,DRName,DRAddress,DRMobileNo,DREmailId,DRQualification,DrFromTime,DrToTime;
    ProgressDialog progressDoalog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview=inflater.inflate(R.layout.family_dr_detail_entry,null);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        intResId = settings.getString("TAG_ResId", "");
        et_DrName=(EditText)myview.findViewById(R.id.et_DrName);
        address=(EditText)myview.findViewById(R.id.address);
        et_DrMobileNo=(EditText)myview.findViewById(R.id.et_DrMobileNo);
        et_Dr_emailId=(EditText)myview.findViewById(R.id.et_Dr_emailId);
        et_Dr_Qualification=(EditText)myview.findViewById(R.id.et_Dr_Qualification);
        radioGroup_gender=(RadioGroup) myview.findViewById(R.id.radioGroup_gender);
        radiobuttonMale=(RadioButton) myview.findViewById(R.id.radiobuttonMale);
        radioButtonFemale=(RadioButton) myview.findViewById(R.id.radioButtonFemale);
        submit=(Button) myview.findViewById(R.id.submit);
        FromTiming=(TextView) myview.findViewById(R.id.from_timing);
        ToTiming=(TextView) myview.findViewById(R.id.to_timing);
        Gender_Selection = "Male";
        radioGroup_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                Gender_Selection = rb.getText().toString();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DRName=et_DrName.getText().toString();
                DRAddress=address.getText().toString();
                DRMobileNo=et_DrMobileNo.getText().toString();
                DREmailId=et_Dr_emailId.getText().toString();
                DRQualification=et_Dr_Qualification.getText().toString();
                DrFromTime=FromTiming.getText().toString();
                DrToTime=ToTiming.getText().toString();
                if(DRName.contentEquals("")||DRName.isEmpty()||DRMobileNo.contentEquals("")||DRMobileNo.isEmpty()||DRQualification.contentEquals("")||DRQualification.isEmpty()||DrFromTime.contentEquals("")||DrFromTime.isEmpty()||DrToTime.contentEquals("")||DrToTime.isEmpty())
                {
                    if (TextUtils.isEmpty(et_DrName.getText().toString())) {
                        et_DrName.setError("Enter Valid Name ");
                    }
                    if (TextUtils.isEmpty(et_DrMobileNo.getText().toString())) {
                        et_DrMobileNo.setError("Enter Valid Mobile No.");
                    }
                    if (TextUtils.isEmpty(et_Dr_Qualification.getText().toString())) {
                        et_Dr_Qualification.setError("Enter Valid Qualification ");
                    }
                    if (TextUtils.isEmpty(FromTiming.getText().toString())) {
                        FromTiming.setError("Enter Valid Dr Available From time");
                    }
                    if (TextUtils.isEmpty(ToTiming.getText().toString())) {
                        ToTiming.setError("Enter Valid Dr Available To time");
                    }
                }else
                {
                    try
                    {
                        InsertDAta();
                    }catch (Exception ex)
                    {

                    }
                }
            }
        });
        FromTiming.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        NumberFormat f = new DecimalFormat("00");
                        FromTiming.setText( (f.format(selectedHour)) + ":" +(f.format( selectedMinute)));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select  Dr Available From Time");
                mTimePicker.show();

            }
        });
        ToTiming.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        NumberFormat f = new DecimalFormat("00");
                        ToTiming.setText( (f.format(selectedHour)) + ":" +(f.format( selectedMinute)));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Dr Available To Time");
                mTimePicker.show();

            }
        });
        return myview;
    }
    public void InsertDAta() {
        try {
            progressDoalog = new ProgressDialog(getActivity());
            progressDoalog.setCancelable(false);
            progressDoalog.setCanceledOnTouchOutside(false);
            progressDoalog.setMessage("Processing...");
            progressDoalog.show();
            DoctorDetail insert = new DoctorDetail(Integer.parseInt(intResId),DRName,Gender_Selection,DRAddress,DRMobileNo,DREmailId,DRQualification,DrFromTime,DrToTime);
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Call<ResponseBody> call = service.Insert_DoctorDetails(insert);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        String responsee = String.valueOf(response.code());
                        if (response.isSuccessful()) {
                            progressDoalog.dismiss();
                            Toast.makeText(getActivity(), "Doctor details submitted Succesfully", Toast.LENGTH_SHORT).show();
                            try {
                                FamilyDr_Tab familyDr_tab = new FamilyDr_Tab ();
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, familyDr_tab).commitAllowingStateLoss();
                            } catch (Exception ex) {

                            }

                        } else {
                            progressDoalog.dismiss();
                            Toast.makeText(getActivity(), "Something went wrong...Please try again!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        progressDoalog.dismiss();
                        String msg = ex.getMessage();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDoalog.dismiss();
                    Toast.makeText(getActivity(), "Something went wrong...Please try again!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {

        }
    }
}
