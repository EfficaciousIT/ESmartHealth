package com.mobi.efficacious.esmarthealth.MultiImages.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.mobi.efficacious.esmarthealth.Interface.DataService;
import com.mobi.efficacious.esmarthealth.MultiImages.helpers.Constants;
import com.mobi.efficacious.esmarthealth.MultiImages.models.Image;
import com.mobi.efficacious.esmarthealth.R;
import com.mobi.efficacious.esmarthealth.fragment.DoctorVisitEntry_fragment;
import com.mobi.efficacious.esmarthealth.webApi.RetrofitInstance;

import java.io.ByteArrayOutputStream;
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


public class MainImages extends AppCompatActivity {
    public static ImageView img1, img2, img3, img4;
  //  public static ArrayList<String> ImageName = new ArrayList<String>();
    String path;
    public static ArrayList<String> FilePath = new ArrayList<String>();
    public static ArrayList<String> FileName = new ArrayList<String>();
    String pathname;
    Button Open_Gallery, Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_images);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        Open_Gallery = (Button) findViewById(R.id.btn_select);
        Submit = (Button) findViewById(R.id.btnSubmit);
        Submit.setVisibility(View.GONE);
        Open_Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainImages.this, AlbumSelectActivity.class);
                    intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 4);
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
                        for(int i=0;i<FilePath.size();i++)
                        {
                            if(i==0)
                            {
                                DoctorVisitEntry_fragment.iv_attachment1.setImageBitmap(BitmapFactory.decodeFile(FilePath.get(i)));
                                DoctorVisitEntry_fragment.iv_attachment1.setVisibility(View.VISIBLE);
                            }
                            if(i==1)
                            {
                                DoctorVisitEntry_fragment.iv_attachment2.setImageBitmap(BitmapFactory.decodeFile(FilePath.get(i)));
                                DoctorVisitEntry_fragment.iv_attachment2.setVisibility(View.VISIBLE);
                            }
                            if(i==2)
                            {
                                DoctorVisitEntry_fragment.iv_attachment3.setImageBitmap(BitmapFactory.decodeFile(FilePath.get(i)));
                                DoctorVisitEntry_fragment.iv_attachment3.setVisibility(View.VISIBLE);
                            }
                            if(i==3)
                            {
                                DoctorVisitEntry_fragment.iv_attachment4.setImageBitmap(BitmapFactory.decodeFile(FilePath.get(i)));
                                DoctorVisitEntry_fragment.iv_attachment4.setVisibility(View.VISIBLE);
                            }

                        }

                      finish();
                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainImages.this);
                        alert.setMessage("Please Select Images");
                        alert.setPositiveButton("OK", null);
                        alert.show();
                    }
                } catch (Exception ex) {
                    ex.getMessage();
                }


            }
        });
        try {
            Intent intent = new Intent(MainImages.this, AlbumSelectActivity.class);
            intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 4);
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
                if (images.size() < 5) {
                    if (images.size() == 0) {
                        Submit.setVisibility(View.GONE);
                    } else {
                        Submit.setVisibility(View.VISIBLE);
                        for (int i = 0, l = images.size(); i < l; i++) {
                            stringBuffer.append(images.get(i).path + "\n");

                            if (i == 0) {
                                img1.setImageBitmap(BitmapFactory.decodeFile(images.get(i).path));

                            } else if (i == 1) {
                                img2.setImageBitmap(BitmapFactory.decodeFile(images.get(i).path));

                            } else if (i == 2) {
                                img3.setImageBitmap(BitmapFactory.decodeFile(images.get(i).path));
                            } else {
                                img4.setImageBitmap(BitmapFactory.decodeFile(images.get(i).path));
                            }
                            pathname = images.get(i).path;
                            FilePath.add(pathname);
                            FileName.add(images.get(i).name);
                        }
                    }

                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainImages.this);
                    alert.setMessage("You Cannot Share more than 4 files");
                    alert.setPositiveButton("OK", null);
                    alert.show();
                }
            } catch (Exception ex) {

            }


        }
    }

}
