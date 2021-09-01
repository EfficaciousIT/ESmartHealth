package com.mobi.efficacious.esmarthealth.MultiImages.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobi.efficacious.esmarthealth.Interface.DataService;
import com.mobi.efficacious.esmarthealth.MultiImages.helpers.Constants;
import com.mobi.efficacious.esmarthealth.MultiImages.models.Image;
import com.mobi.efficacious.esmarthealth.R;
import com.mobi.efficacious.esmarthealth.activity.Firebase_OTP;
import com.mobi.efficacious.esmarthealth.activity.MainActivity;
import com.mobi.efficacious.esmarthealth.entity.PersoanlDetail;
import com.mobi.efficacious.esmarthealth.entity.Registration;
import com.mobi.efficacious.esmarthealth.entity.TblPersoanlDetailsPojo;
import com.mobi.efficacious.esmarthealth.entity.TblRegistrationPojo;
import com.mobi.efficacious.esmarthealth.fragment.PersonalDetails_fragment;
import com.mobi.efficacious.esmarthealth.webApi.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Single_image extends AppCompatActivity {
    public static ImageView img1, img2, img3;


    String path, intResId;
    ArrayList<String> FilePath = new ArrayList<String>();
    String pathname, filename,intPer_Id;
    Button Open_Gallery, Submit;
    ProgressDialog progressDoalog;
    public static String extension = "";
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_image);
        try {
            settings = getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
            intResId = settings.getString("TAG_ResId", "");
            intPer_Id=settings.getString("TAG_intPerId", "");
            img1 = (ImageView) findViewById(R.id.img1);
            Open_Gallery = (Button) findViewById(R.id.btn_select);
            Submit = (Button) findViewById(R.id.btnSubmit);
            Submit.setVisibility(View.GONE);
        } catch (Exception ex) {

        }

        Open_Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Single_image.this, AlbumSelectActivity.class);
                    intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 1);
                    startActivityForResult(intent, Constants.REQUEST_CODE);
                } catch (Exception ex) {

                }

            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (FilePath.size() > 0) {
                        uploadFile();
                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(Single_image.this);
                        alert.setMessage("Please Select Images");
                        alert.setPositiveButton("OK", null);
                        alert.show();
                    }
                } catch (Exception ex) {

                }


            }
        });
        try {
            Intent intent = new Intent(Single_image.this, AlbumSelectActivity.class);
            intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 1);
            startActivityForResult(intent, Constants.REQUEST_CODE);
        } catch (Exception ex) {

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
            StringBuffer stringBuffer = new StringBuffer();
            try {
                if (images.size() < 2) {
                    if (images.size() == 0) {
                        Submit.setVisibility(View.GONE);
                    } else {
                        Submit.setVisibility(View.VISIBLE);
                        for (int i = 0, l = images.size(); i < l; i++) {
                            stringBuffer.append(images.get(i).path + "\n");

                            if (i == 0) {
                                img1.setImageBitmap(BitmapFactory.decodeFile(images.get(i).path));
                            }
                            pathname = images.get(i).path;
                            filename = images.get(i).name;
                            FilePath.add(pathname);
                        }
                    }

                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(Single_image.this);
                    alert.setMessage("Cannot Share more than 1 files");
                    alert.setPositiveButton("OK", null);
                    alert.show();
                }
            } catch (Exception ex) {

            }


        }
    }

    private void uploadFile() {
        try {
            extension = "";
            progressDoalog = new ProgressDialog(Single_image.this);
            progressDoalog.setCancelable(false);
            progressDoalog.setCanceledOnTouchOutside(false);
            progressDoalog.setMessage("uploading...");
            progressDoalog.show();
            String fileName = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss", Locale.getDefault()).format(new Date());
            extension = filename.substring(filename.lastIndexOf("."));
            extension = fileName + extension;
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            File file = new File(pathname);
            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse("image/*"),
                            file
                    );

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

            Call<ResponseBody> call = service.upload(body, extension);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {
                    String responsee = String.valueOf(response.code());
                    if (response.isSuccessful()) {
                        progressDoalog.dismiss();
                        PersonalDetails_fragment.profileImg.setImageBitmap(BitmapFactory.decodeFile(pathname));

                        if(intPer_Id.isEmpty()||intPer_Id.contentEquals(""))
                        {
                            finish();
                        }else
                        {
                            UpdateDAta();
                        }


                    } else {
                        progressDoalog.dismiss();
                        Toast.makeText(Single_image.this, "Error while Uploading Profile Image", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDoalog.dismiss();
                    Toast.makeText(Single_image.this, "Error while Uploading Profile Image", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {

        }

    }
    public void UpdateDAta()
    {
        try{
            progressDoalog = new ProgressDialog(Single_image.this);
            progressDoalog.setCancelable(false);
            progressDoalog.setCanceledOnTouchOutside(false);
            progressDoalog.setMessage("Processing...");
            progressDoalog.show();
            PersoanlDetail update = new PersoanlDetail(Integer.parseInt(intPer_Id),Integer.parseInt(intResId),extension);
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Call<ResponseBody> call = service.Update_Personal_details("UpdateProfileImg",update);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        String responsee = String.valueOf(response.code());
                        if (response.isSuccessful()) {
                            progressDoalog.dismiss();
                            Toast.makeText(Single_image.this, "Profile Image Uploaded", Toast.LENGTH_SHORT).show();
                            try{
                                try
                                {
                                    String url=RetrofitInstance.Image_URL+extension;
                                    Picasso.with(Single_image.this).load(url)
                                            .fit()
                                            .placeholder(R.mipmap.profile)
                                            .error(R.mipmap.profile)
                                            .into(MainActivity.profile_Img);
                                }catch (Exception ex)
                                {
                                    ex.getMessage();
                                }
                                finish();
                            }catch (Exception ex)
                            {

                            }

                        } else {
                            progressDoalog.dismiss();
                            Toast.makeText(Single_image.this, "Something went wrong...Please try again!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        progressDoalog.dismiss();
                        String msg = ex.getMessage();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDoalog.dismiss();
                    Toast.makeText(Single_image.this, "Something went wrong...Please try again!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception ex)
        {

        }
    }

}
