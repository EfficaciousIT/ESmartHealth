package com.mobi.efficacious.esmarthealth.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.mobi.efficacious.esmarthealth.Interface.DataService;
import com.mobi.efficacious.esmarthealth.MultiImages.activities.MainImages;
import com.mobi.efficacious.esmarthealth.MultiImages.activities.Single_image;
import com.mobi.efficacious.esmarthealth.R;
import com.mobi.efficacious.esmarthealth.activity.MainActivity;
import com.mobi.efficacious.esmarthealth.activity.Registration_activity;
import com.mobi.efficacious.esmarthealth.common.SpinnerError;
import com.mobi.efficacious.esmarthealth.entity.PersoanlDetail;
import com.mobi.efficacious.esmarthealth.entity.Registration;
import com.mobi.efficacious.esmarthealth.entity.TblPersoanlDetailsPojo;
import com.mobi.efficacious.esmarthealth.webApi.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.app.Activity.RESULT_OK;

public class PersonalDetails_fragment extends Fragment {
    View myview;
    public static CircleImageView profileImg;
    EditText  et_Per_height, et_Per_weight, et_Allergic, et_disabilityDetail, address;
    Spinner sp_bloodGroup, sp_BodyType;
    RadioButton radiobuttonMale, radioButtonFemale, PD_yes, PD_no;
    TextView et_Per_DOB,et_perName;
    RadioGroup Gander_RadioGroup, PD_RadioGroup;
    Button submit;
    String[] Sp_BloodGroup, Sp_BodyType;
    String Name,D_OB, Height, weight, Allergic, Disability_details, Address, BloodGroup, BodyType, DOB, Selected_Date, Profile_img = "";
    SpinnerError spinnerError;
    String Gender_Selection, PD_selected, intResId, intPer_Id;
    private int mYear, mMonth, mDay;
    ProgressDialog progressDoalog;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    ArrayAdapter<String> ad;
    List<String> DAte_list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.personal_detail_entry, null);
        spinnerError = new SpinnerError(getActivity());
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        intResId = settings.getString("TAG_ResId", "");
        Name = settings.getString("TAG_Name", "");
        et_perName = (TextView) myview.findViewById(R.id.et_perName);
        et_Per_height = (EditText) myview.findViewById(R.id.et_Per_height);
        et_Per_weight = (EditText) myview.findViewById(R.id.et_Per_weight);
        et_Allergic = (EditText) myview.findViewById(R.id.et_Allergic);
        et_disabilityDetail = (EditText) myview.findViewById(R.id.et_disabilityDetail);
        address = (EditText) myview.findViewById(R.id.address);
        et_Per_DOB = (TextView) myview.findViewById(R.id.et_Per_DOB);
        sp_bloodGroup = (Spinner) myview.findViewById(R.id.sp_bloodGroup);
        sp_BodyType = (Spinner) myview.findViewById(R.id.sp_BodyType);
        profileImg = (CircleImageView) myview.findViewById(R.id.imageView1);
        Gander_RadioGroup = (RadioGroup) myview.findViewById(R.id.radioGroup_gender);
        PD_RadioGroup = (RadioGroup) myview.findViewById(R.id.radioGroup_disability);
        submit = (Button) myview.findViewById(R.id.submit);
        radiobuttonMale = (RadioButton) myview.findViewById(R.id.radiobuttonMale);
        radioButtonFemale = (RadioButton) myview.findViewById(R.id.radioButtonFemale);

        PD_yes = (RadioButton) myview.findViewById(R.id.radiobuttonyes);
        PD_no = (RadioButton) myview.findViewById(R.id.radioButtonno);
        et_perName.setText(Name);
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
                    Intent intent = new Intent(getActivity(), Single_image.class);
                    startActivity(intent);
                } catch (Exception ex) {

                }

            }
        });
        Sp_BloodGroup = getResources().getStringArray(R.array.BloodGroup);
        ad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Sp_BloodGroup);
        sp_bloodGroup.setAdapter(ad);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_bloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BloodGroup = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Sp_BodyType = getResources().getStringArray(R.array.body_type);
        ArrayAdapter<String> ad1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Sp_BodyType);
        sp_BodyType.setAdapter(ad1);
        ad1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_BodyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BodyType = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        et_Per_DOB.setOnClickListener(new View.OnClickListener() {
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
                                    Selected_Date = ((f.format(dayOfMonth)) + "/" + (f.format(monthOfYear + 1)) + "/" + year);
                                      D_OB = (year + "/" + (f.format(monthOfYear + 1)) + "/" + (f.format(dayOfMonth)));
                                    et_Per_DOB.setText(Selected_Date);
                                }
                            }, mYear, mMonth, mDay);

                    datePickerDialog.show();
                } catch (Exception ex) {

                }

            }
        });
        Gender_Selection = "Male";
        Gander_RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                Gender_Selection = rb.getText().toString();
            }
        });
        PD_selected = "NO";
        et_disabilityDetail.setVisibility(View.GONE);
        PD_RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                PD_selected = rb.getText().toString();
                if (PD_selected.contentEquals("NO")) {
                    et_disabilityDetail.setVisibility(View.GONE);
                } else {
                    et_disabilityDetail.setVisibility(View.VISIBLE);
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = et_perName.getText().toString();
                DOB = et_Per_DOB.getText().toString();
                Height = et_Per_height.getText().toString();
                weight = et_Per_weight.getText().toString();
                Allergic = et_Allergic.getText().toString();
                Disability_details = et_disabilityDetail.getText().toString();
                Address = address.getText().toString();
                if (Name.contentEquals("") || Name.isEmpty() || DOB.isEmpty() || DOB.contentEquals("") || Height.isEmpty() || Height.contentEquals("") || weight.isEmpty() || weight.contentEquals("") || BodyType.contentEquals("--Select Body Type--") || Address.contentEquals("") || Address.isEmpty()) {
                    if (BodyType.contentEquals("--Select Body Type--")) {
                        spinnerError.setSpinnerError(sp_BodyType, "Invalid Body Type");
                    }
                    if (TextUtils.isEmpty(et_perName.getText().toString())) {
                        et_perName.setError("Enter Valid Name ");
                    }
                    if (TextUtils.isEmpty(et_Per_DOB.getText().toString())) {
                        et_Per_DOB.setError("Enter Valid Date Of Birth");
                    }
                    if (TextUtils.isEmpty(et_Per_height.getText().toString())) {
                        et_Per_height.setError("Enter Valid Height  ");
                    }
                    if (TextUtils.isEmpty(et_Per_weight.getText().toString())) {
                        et_Per_weight.setError("Enter Valid Weight");
                    }
                    if (TextUtils.isEmpty(address.getText().toString())) {
                        address.setError("Enter Valid Address");
                    }
                } else {

                    try {
                        intPer_Id = settings.getString("TAG_intPerId", "");
                        if (intPer_Id.contentEquals("") || intPer_Id.isEmpty()) {
                            Profile_img = Single_image.extension;
                            if (Profile_img.isEmpty() || Profile_img.contentEquals("")) {
                                Profile_img = "0";
                            }
                            InsertDAta();
                        } else {
                            UpdateDAta();
                        }

                    } catch (Exception ex) {

                    }


                }
            }
        });
        getPersonal_Data();
        return myview;
    }

    public void InsertDAta() {
        try {
            progressDoalog = new ProgressDialog(getActivity());
            progressDoalog.setCancelable(false);
            progressDoalog.setCanceledOnTouchOutside(false);
            progressDoalog.setMessage("Processing...");
            progressDoalog.show();
            PersoanlDetail insert = new PersoanlDetail(Integer.parseInt(intResId), Double.parseDouble(Height), Double.parseDouble(weight), Gender_Selection, D_OB, BloodGroup, BodyType, Allergic, PD_selected, Disability_details, Profile_img, Address);
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Call<ResponseBody> call = service.Insert_Personal_details(insert);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        String responsee = String.valueOf(response.code());
                        if (response.isSuccessful()) {
                            progressDoalog.dismiss();
                            Toast.makeText(getActivity(), "Personal details submitted Succesfully", Toast.LENGTH_SHORT).show();
                            try {

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

    public void UpdateDAta() {
        try {
            DOB=et_Per_DOB.getText().toString();
            DAte_list= Arrays.asList(DOB.split("/"));
           D_OB=DAte_list.get(2)+"/"+ DAte_list.get(1)+"/"+ DAte_list.get(0);
            progressDoalog = new ProgressDialog(getActivity());
            progressDoalog.setCancelable(false);
            progressDoalog.setCanceledOnTouchOutside(false);
            progressDoalog.setMessage("Processing...");
            progressDoalog.show();
            PersoanlDetail update = new PersoanlDetail(Integer.parseInt(intPer_Id), Integer.parseInt(intResId), Double.parseDouble(Height), Double.parseDouble(weight), Gender_Selection, D_OB, BloodGroup, BodyType, Allergic, PD_selected, Disability_details, Address);
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Call<ResponseBody> call = service.Update_Personal_details("Update", update);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        String responsee = String.valueOf(response.code());
                        if (response.isSuccessful()) {
                            progressDoalog.dismiss();
                            Toast.makeText(getActivity(), "Personal details submitted Succesfully", Toast.LENGTH_SHORT).show();
                            try {

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

    public void getPersonal_Data() {
        try {
            progressDoalog = new ProgressDialog(getActivity());
            progressDoalog.setCancelable(false);
            progressDoalog.setCanceledOnTouchOutside(false);
            progressDoalog.setMessage("Loading...");
            progressDoalog.show();
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Call<TblPersoanlDetailsPojo> call = service.getPersonalData("Select", intResId);

            call.enqueue(new Callback<TblPersoanlDetailsPojo>() {
                @Override
                public void onResponse(Call<TblPersoanlDetailsPojo> call, Response<TblPersoanlDetailsPojo> response) {
                    String responsee = String.valueOf(response.code());
                    if (response.isSuccessful()) {
                        progressDoalog.dismiss();
                        try {
                            generateEmployeeList((ArrayList<PersoanlDetail>) response.body().getPersoanlDetails());
                        } catch (Exception ex) {

                        }
                    } else {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        progressDoalog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<TblPersoanlDetailsPojo> call, Throwable t) {
                    Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();
                }
            });
        } catch (Exception ex) {

        }
    }

    public void generateEmployeeList(ArrayList<PersoanlDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                String intPerId = taskListDataList.get(0).getIntPerId().toString();
                String ftHeight = taskListDataList.get(0).getFtHeight().toString();
                String ftWeight = taskListDataList.get(0).getFtWeight().toString();
                Gender_Selection = taskListDataList.get(0).getVchGender().toString();
                String vchAddress = taskListDataList.get(0).getVchAddress().toString();
                String dtDOB = taskListDataList.get(0).getDtDOB().toString();
                BloodGroup = taskListDataList.get(0).getVchBloodGroup().toString();
                 BodyType = taskListDataList.get(0).getVchBodyType().toString();
                String vchAllergic = taskListDataList.get(0).getVchAllergic().toString();
                PD_selected = taskListDataList.get(0).getDisabilityStatus().toString();
                String vchDisability = taskListDataList.get(0).getVchDisability().toString();
                String vchProfile = taskListDataList.get(0).getVchProfile().toString();

                settings.edit().putString("TAG_intPerId", intPerId).commit();

                String url=RetrofitInstance.Image_URL+vchProfile;
                try
                {
                    Glide.with(getActivity())
                            .load(url) // image url
                            .error(R.mipmap.profile)
                            .into(profileImg);
                }catch (Exception ex)
                {
                    ex.getMessage();
                }


                et_Per_DOB.setText(dtDOB);
                et_Per_height.setText(ftHeight);
                et_Per_weight.setText(ftWeight);
                et_Allergic.setText(vchAllergic);
                et_disabilityDetail.setText(vchDisability);
                address.setText(vchAddress);
                if (Gender_Selection.contentEquals("Male")) {
                    radiobuttonMale.setChecked(true);
                    radioButtonFemale.setChecked(false);
                } else {
                    radiobuttonMale.setChecked(false);
                    radioButtonFemale.setChecked(true);
                }
                if (PD_selected.contentEquals("Yes")) {
                    PD_yes.setChecked(true);
                    PD_no.setChecked(false);
                    et_disabilityDetail.setVisibility(View.VISIBLE);

                } else {
                    et_disabilityDetail.setVisibility(View.GONE);
                    PD_yes.setChecked(false);
                    PD_no.setChecked(true);
                }
                sp_bloodGroup.setSelection(getIndex(sp_bloodGroup, BloodGroup));
                sp_BodyType.setSelection(getIndex(sp_BodyType, BodyType));
            } else {

            }

        } catch (Exception ex) {
            ex.getMessage();
        }

    }


    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }
}
