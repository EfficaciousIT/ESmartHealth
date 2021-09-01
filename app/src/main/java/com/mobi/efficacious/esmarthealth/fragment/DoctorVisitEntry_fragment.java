package com.mobi.efficacious.esmarthealth.fragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mobi.efficacious.esmarthealth.Interface.DataService;
import com.mobi.efficacious.esmarthealth.MultiImages.activities.MainImages;
import com.mobi.efficacious.esmarthealth.R;
import com.mobi.efficacious.esmarthealth.Services.AlarmReceiver;
import com.mobi.efficacious.esmarthealth.activity.MainActivity;
import com.mobi.efficacious.esmarthealth.adapter.spinner_adapter;
import com.mobi.efficacious.esmarthealth.common.SpinnerError;

import com.mobi.efficacious.esmarthealth.entity.DoctorDetail;
import com.mobi.efficacious.esmarthealth.entity.TblDoctorDetailsPojo;
import com.mobi.efficacious.esmarthealth.entity.TblVisitDetailsPojo;
import com.mobi.efficacious.esmarthealth.entity.VisitDetail;
import com.mobi.efficacious.esmarthealth.tab.DoctorVisit_tab;
import com.mobi.efficacious.esmarthealth.webApi.RetrofitInstance;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DoctorVisitEntry_fragment extends Fragment {
    View myview;
    EditText et_medicineName1, et_medicineName2, et_medicineName3, et_medicineName4, et_medicineName5, et_Description;
    TextView tv_medicine1Time3, tv_medicine1Time1, tv_medicine1Time2, tv_visitdate, tv_nxtvisitdate;
    Switch Switch_Alert;
    public static ImageView iv_attachment1, iv_attachment2, iv_attachment3, iv_attachment4;
    Button submit;
    Spinner sp_DoctorName;
    ImageButton Attachment;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String intResId;
    SpinnerError spinnerError;
    ProgressDialog progressDoalog;
    private ArrayList<HashMap<Object, Object>> dataListDrName;
    HashMap<Object, Object> map;
    spinner_adapter adapter;
    String SelectedDrId,command;
    String Visit_date = "", Medicine1 = "", Medicine2 = "", Medicine3 = "", Medicine4 = "", Medicine5 = "", description = "", Attachment1 = "", Attachment2 = "", Attachment3 = "", Attachment4 = "", NxtVisit_date = "1900/01/01 00:00:00.000", AlertStatus = "";
    String Med1Time1 = "", Med1Time2 = "", Med1Time3 = "";
    private int mYear, mMonth, mDay;
    List<String> Alarmdata;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.doctor_visit_entry, null);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        intResId = settings.getString("TAG_ResId", "");
        spinnerError = new SpinnerError(getActivity());
        dataListDrName = new ArrayList<HashMap<Object, Object>>();

        tv_visitdate = (TextView) myview.findViewById(R.id.tv_visitdate);
        tv_medicine1Time1 = (TextView) myview.findViewById(R.id.tv_medicine1Time1);
        tv_medicine1Time2 = (TextView) myview.findViewById(R.id.tv_medicine1Time2);
        tv_medicine1Time3 = (TextView) myview.findViewById(R.id.tv_medicine1Time3);
        Switch_Alert = (Switch) myview.findViewById(R.id.Switch_Alert);
        tv_nxtvisitdate = (TextView) myview.findViewById(R.id.tv_nxtvisitdate);
        Attachment = (ImageButton) myview.findViewById(R.id.Attachment);

        et_medicineName1 = (EditText) myview.findViewById(R.id.et_medicineName1);
        et_medicineName2 = (EditText) myview.findViewById(R.id.et_medicineName2);
        et_medicineName3 = (EditText) myview.findViewById(R.id.et_medicineName3);
        et_medicineName4 = (EditText) myview.findViewById(R.id.et_medicineName4);
        et_medicineName5 = (EditText) myview.findViewById(R.id.et_medicineName5);

        iv_attachment1 = (ImageView) myview.findViewById(R.id.iv_attachment1);
        iv_attachment2 = (ImageView) myview.findViewById(R.id.iv_attachment2);
        iv_attachment3 = (ImageView) myview.findViewById(R.id.iv_attachment3);
        iv_attachment4 = (ImageView) myview.findViewById(R.id.iv_attachment4);
        et_Description = (EditText) myview.findViewById(R.id.et_Description);
        sp_DoctorName = (Spinner) myview.findViewById(R.id.sp_DoctorName);

        submit = (Button) myview.findViewById(R.id.submit);
        tv_medicine1Time1.setOnClickListener(new View.OnClickListener() {

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
                        tv_medicine1Time1.setText((f.format(selectedHour)) + ":" + (f.format(selectedMinute)));

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        tv_medicine1Time2.setOnClickListener(new View.OnClickListener() {

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
                        tv_medicine1Time2.setText((f.format(selectedHour)) + ":" + (f.format(selectedMinute)));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        tv_medicine1Time3.setOnClickListener(new View.OnClickListener() {

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
                        tv_medicine1Time3.setText((f.format(selectedHour)) + ":" + (f.format(selectedMinute)));

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        AlertStatus = "0";
        Switch_Alert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Boolean vchAlertStatus = isChecked;
                if (vchAlertStatus == true) {
                    AlertStatus = "1";
                    Snackbar.make(buttonView, "Notify me for the Medicine", Snackbar.LENGTH_LONG)
                            .setAction("ACTION", null).show();
                } else {
                    AlertStatus = "0";
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Medicine1 = et_medicineName1.getText().toString();
                    Medicine2 = et_medicineName2.getText().toString();
                    Medicine3 = et_medicineName3.getText().toString();
                    Medicine4 = et_medicineName4.getText().toString();
                    Medicine5 = et_medicineName5.getText().toString();
                    Med1Time1 = tv_medicine1Time1.getText().toString();
                    if (Med1Time1.contentEquals("") || Med1Time1.isEmpty()) {
                        Med1Time1 = "00:00";
                    }
                    Med1Time2 = tv_medicine1Time2.getText().toString();
                    if (Med1Time2.contentEquals("") || Med1Time2.isEmpty()) {
                        Med1Time2 = "00:00";
                    }
                    Med1Time3 = tv_medicine1Time3.getText().toString();
                    if (Med1Time3.contentEquals("") || Med1Time3.isEmpty()) {
                        Med1Time3 = "00:00";
                    }
                    if(NxtVisit_date.contentEquals("")){
                        NxtVisit_date="1900/01/01 00:00:00.000";
                    }
                    description = et_Description.getText().toString();
                    if (Visit_date.contentEquals("") || Visit_date.isEmpty() || description.isEmpty() || description.contentEquals("") || SelectedDrId.contentEquals("0")) {
                        if (SelectedDrId.contentEquals("0")) {
                            spinnerError.setSpinnerError(sp_DoctorName, "Invalid Dr Name");
                        }
                        if (TextUtils.isEmpty(et_Description.getText().toString())) {
                            et_Description.setError("Enter Valid Description ");
                        }
                        if (TextUtils.isEmpty(tv_visitdate.getText().toString())) {
                            tv_visitdate.setError("Enter Valid Visit Date");
                        }

                    }
                    else {
                        try {
                            int count = MainImages.FilePath.size();
                            if (count != 0) {
                                uploadFile();

                            } else {
                                InserData();
                            }
                        } catch (Exception ex) {

                        }
                    }
                } catch (Exception ex) {
                    ex.getMessage();
                }

            }
        });
        Attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MainImages.FilePath.clear();
                    MainImages.FileName.clear();
                    iv_attachment1.setVisibility(View.GONE);
                    iv_attachment2.setVisibility(View.GONE);
                    iv_attachment3.setVisibility(View.GONE);
                    iv_attachment4.setVisibility(View.GONE);
                    Intent intent = new Intent(getActivity(), MainImages.class);
                    startActivity(intent);
                } catch (Exception ex) {

                }

            }
        });
        sp_DoctorName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedDrId = String.valueOf(dataListDrName.get(position).get("intDocId"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tv_visitdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Locale.setDefault(Locale.ENGLISH);
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    NumberFormat f = new DecimalFormat("00");
                                    Visit_date = (year + "/" + (f.format(monthOfYear + 1)) + "/" + (f.format(dayOfMonth)));
                                    tv_visitdate.setText(((f.format(dayOfMonth)) + "/" + (f.format(monthOfYear + 1)) + "/" + year));
                                }
                            }, mYear, mMonth, mDay);

                    datePickerDialog.show();
                } catch (Exception ex) {

                }

            }
        });
        tv_nxtvisitdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Locale.setDefault(Locale.ENGLISH);
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    NumberFormat f = new DecimalFormat("00");
                                    NxtVisit_date = (year + "/" + (f.format(monthOfYear + 1)) + "/" + (f.format(dayOfMonth)));
                                    tv_nxtvisitdate.setText(((f.format(dayOfMonth)) + "/" + (f.format(monthOfYear + 1)) + "/" + year));
                                }
                            }, mYear, mMonth, mDay);

                    datePickerDialog.show();
                } catch (Exception ex) {

                }

            }
        });
        getDrName();
        return myview;
    }

    private void uploadFile() {

        try {
            for (int i = 0; i < MainImages.FilePath.size(); i++) {
                try {
                    String extension = "";
                    progressDoalog = new ProgressDialog(getActivity());
                    progressDoalog.setCancelable(false);
                    progressDoalog.setCanceledOnTouchOutside(false);
                    progressDoalog.setMessage("uploading...");
                    progressDoalog.show();
                    String fileName = MainImages.FileName.get(i);
                    String filename = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss", Locale.getDefault()).format(new Date());
                    extension = fileName.substring(fileName.lastIndexOf("."));
                    extension = filename + extension;
                    if (i == 0) {
                        Attachment1 = extension;
                    }
                    if (i == 1) {
                        Attachment2 = extension;
                    }
                    if (i == 2) {
                        Attachment3 = extension;
                    }
                    if (i == 3) {
                        Attachment4 = extension;
                    }
                    DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
                    String pathname = MainImages.FilePath.get(i);
                    File file = new File(pathname);
                    RequestBody requestFile =
                            RequestBody.create(
                                    MediaType.parse("image/*"),
                                    file
                            );

                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

                    Call<ResponseBody> call = service.upload(body, extension);
                    final int finalI = i;
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call,
                                               Response<ResponseBody> response) {
                            String responsee = String.valueOf(response.code());
                            if (response.isSuccessful()) {
                                progressDoalog.dismiss();
                                if (finalI == (MainImages.FileName.size() - 1)) {
                                    InserData();
                                }
                            } else {
                                progressDoalog.dismiss();
                                Toast.makeText(getActivity(), "Error while Uploading Attachment", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            progressDoalog.dismiss();
                            Toast.makeText(getActivity(), "Error while Uploading Attachment", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception ex) {

                }
            }
        } catch (Exception ex) {

        }


    }

    public void InserData() {
        try {
            progressDoalog = new ProgressDialog(getActivity());
            progressDoalog.setCancelable(false);
            progressDoalog.setCanceledOnTouchOutside(false);
            progressDoalog.setMessage("Processing...");
            progressDoalog.show();
//            VisitDetail insert = new VisitDetail(Integer.parseInt(SelectedDrId), Integer.parseInt(intResId), Visit_date, NxtVisit_date, Medicine1, Medicine2, Medicine3, Medicine4, Medicine5, description, AlertStatus, Attachment1, Attachment2, Attachment3, Attachment4, Med1Time1, Med1Time2, Med1Time3, Med2Time1, Med2Time2, Med2Time3, Med3Time1, Med3Time2, Med3Time3, Med4Time1, Med4Time2, Med4Time3, Med5Time1, Med5Time2, Med5Time3);
            VisitDetail insert = new VisitDetail(Integer.parseInt(SelectedDrId), Integer.parseInt(intResId), Visit_date, NxtVisit_date, Medicine1, Medicine2, Medicine3, Medicine4, Medicine5, description, AlertStatus, Attachment1, Attachment2, Attachment3, Attachment4, Med1Time1, Med1Time2, Med1Time3);
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Call<ResponseBody> call = service.Insert_VisitDetails(insert);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        String responsee = String.valueOf(response.code());
                        if (response.isSuccessful()) {
                            progressDoalog.dismiss();

                            try {
                                if(AlertStatus.contentEquals("1"))
                                {
                                    alarmset("SelectAlarmTime");
                                }else
                                {
                                    Toast.makeText(getActivity(), "Visit details submitted Succesfully", Toast.LENGTH_SHORT).show();
                                    DoctorVisit_tab doctorVisit_tab = new DoctorVisit_tab();
                                    MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, doctorVisit_tab).commitAllowingStateLoss();
                                }

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

    public void getDrName() {
        try {
            progressDoalog = new ProgressDialog(getActivity());
            progressDoalog.setCancelable(false);
            progressDoalog.setCanceledOnTouchOutside(false);
            progressDoalog.setMessage("Loading...");
            progressDoalog.show();
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Call<TblDoctorDetailsPojo> call = service.getDoctorDetails(intResId, "SelectDoctorName");

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

    public void generateEmployeeList(ArrayList<DoctorDetail> drDataList) {
        if ((drDataList != null && !drDataList.isEmpty())) {
            dataListDrName.clear();
            map = new HashMap<Object, Object>();
            map.put("intDocId", "0");
            map.put("vchDrName", "--Select Dr--");
            dataListDrName.add(map);
            for (int i = 0; i < drDataList.size(); i++) {
                map = new HashMap<Object, Object>();
                map.put("intDocId", drDataList.get(i).getIntDocId());
                map.put("vchDrName", drDataList.get(i).getVchName());
                dataListDrName.add(map);
            }

            try {
                adapter = new spinner_adapter(getActivity(), dataListDrName, "DoctorVisitEntry");
                sp_DoctorName.setAdapter(adapter);
            } catch (Exception ex) {

            }
        } else {
            dataListDrName.clear();
            map = new HashMap<Object, Object>();
            map.put("intDocId", "0");
            map.put("vchDrName", "--Select Dr--");
            dataListDrName.add(map);
            try {
                adapter = new spinner_adapter(getActivity(), dataListDrName, "DoctorVisitEntry");
                sp_DoctorName.setAdapter(adapter);
            } catch (Exception ex) {

            }
        }

    }

    public void alarmset(String command)
    {
        try
        {
                progressDoalog = new ProgressDialog(getActivity());
                progressDoalog.setCancelable(false);
                progressDoalog.setCanceledOnTouchOutside(false);
                progressDoalog.setMessage("Loading...");
                progressDoalog.show();
                DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
                Call<TblVisitDetailsPojo> call = service.getAlarmDetails(command,intResId);

                call.enqueue(new Callback<TblVisitDetailsPojo>() {
                    @Override
                    public void onResponse(Call<TblVisitDetailsPojo> call, Response<TblVisitDetailsPojo> response) {
                        String responsee= String.valueOf(response.code());
                        if (response.isSuccessful()) {
                            progressDoalog.dismiss();
                            try{
                                generateAlarmList((ArrayList<VisitDetail>) response.body().getVisitDetails());
                            }catch (Exception ex)
                            {

                            }
                        } else {
                            Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                            progressDoalog.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<TblVisitDetailsPojo> call, Throwable t) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        progressDoalog.dismiss();
                    }
                });



        }catch (Exception ex)
        {

        }

    }
    public  void generateAlarmList(ArrayList<VisitDetail> taskListDataList) {
        try {
            String alarmHour,alarmMinute;
            if((taskListDataList != null && !taskListDataList.isEmpty()))
            {
                String AlarmTime=taskListDataList.get(0).getMedTime().toString();
                String Dr_id=taskListDataList.get(0).getIntDocId().toString();
                String Dr_visit_id=taskListDataList.get(0).getIntDrVisitId().toString();
                Alarmdata = Arrays.asList(AlarmTime.split(":"));
                 alarmHour=Alarmdata.get(0);
                 alarmMinute=Alarmdata.get(1);
                Intent alarmIntent = new Intent(getActivity(), AlarmReceiver.class);
                alarmIntent.putExtra("intDrVisitId",Dr_visit_id);
                alarmIntent.putExtra("intDrId",Dr_id);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, 0);
                AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(alarmHour));
                calendar.set(Calendar.MINUTE, Integer.parseInt(alarmMinute));
                calendar.set(Calendar.SECOND, 0);
                manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);
                DoctorVisit_tab doctorVisit_tab = new DoctorVisit_tab();
                MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, doctorVisit_tab).commitAllowingStateLoss();
            }else
            {
                alarmset("SelectAlarmTimeStart");
            }

        }catch (Exception ex)
        {

        }

    }
}
