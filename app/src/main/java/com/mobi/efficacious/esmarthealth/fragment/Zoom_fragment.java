package com.mobi.efficacious.esmarthealth.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mobi.efficacious.esmarthealth.R;
import com.mobi.efficacious.esmarthealth.webApi.RetrofitInstance;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by EFF-4 on 3/29/2018.
 */

public class Zoom_fragment extends Activity {
    ImageView imageView;
    String Path;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.zoom_fragment);

        try {
            Intent intent = getIntent();
            if (null != intent) {
                Path = intent.getStringExtra("path");
            }
        } catch (Exception ex) {

        }

        imageView = (ImageView) findViewById(R.id.imageView6);


        try {
            String url= RetrofitInstance.Image_URL+Path;
            Glide.with(Zoom_fragment.this)
                    .load(url) // image url
                    .error(R.mipmap.profile)
                    .into(imageView);
        } catch (Exception ex) {

        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String url= RetrofitInstance.Image_URL+Path;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                } catch (Exception ex) {

                }

            }
        });

    }

    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}
