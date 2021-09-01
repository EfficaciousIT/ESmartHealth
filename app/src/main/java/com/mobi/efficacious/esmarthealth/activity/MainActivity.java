package com.mobi.efficacious.esmarthealth.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.mobi.efficacious.esmarthealth.Interface.DataService;
import com.mobi.efficacious.esmarthealth.R;

import com.mobi.efficacious.esmarthealth.entity.PersoanlDetail;
import com.mobi.efficacious.esmarthealth.entity.TblPersoanlDetailsPojo;
import com.mobi.efficacious.esmarthealth.fragment.Health_tips_fragment;
import com.mobi.efficacious.esmarthealth.fragment.PersonalDetails_fragment;
import com.mobi.efficacious.esmarthealth.tab.DoctorVisit_tab;
import com.mobi.efficacious.esmarthealth.tab.FamilyDr_Tab;
import com.mobi.efficacious.esmarthealth.webApi.RetrofitInstance;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String title;
    int k;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    public static FragmentManager fragmentManager;
String intResId,int_perId;
  public static   CircleImageView profile_Img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        settings =getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        try
        {
            intResId = settings.getString("TAG_ResId", "");
            int_perId=settings.getString("TAG_intPerId", "");
        }catch (Exception ex)
        {

        }

        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        profile_Img = (CircleImageView) findViewById(R.id.profile_img);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        getPersonal_Data();
        try
        {
            Health_tips_fragment health_tips_fragment = new Health_tips_fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, health_tips_fragment).commitAllowingStateLoss();
            getSupportActionBar().setTitle("Health Tips");
        }
        catch (Exception ex)
        {

        }

        profile_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = "Personal Detail";
                try {
                    PersonalDetails_fragment personalDetails_fragment = new PersonalDetails_fragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main, personalDetails_fragment).commitAllowingStateLoss();
                    getSupportActionBar().setTitle(title);
                } catch (Exception ex) {

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        k++;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
//            super.onBackPressed();
        }
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
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferences.Editor editor_delete = settings.edit();
            editor_delete.clear().commit();
            FirebaseAuth.getInstance().signOut();
            this.deleteDatabase("Notifications");
            Intent intent = new Intent(MainActivity.this, Login_activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_personalDetails) {
            title = "Personal Detail";
            try {
                PersonalDetails_fragment personalDetails_fragment = new PersonalDetails_fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, personalDetails_fragment).commitAllowingStateLoss();
            } catch (Exception ex) {

            }
        } else if (id == R.id.nav_family_doctor_details) {
            title = "Doctor Detail";
            try {
                FamilyDr_Tab familyDr_tab = new FamilyDr_Tab();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, familyDr_tab).commitAllowingStateLoss();
            } catch (Exception ex) {

            }
        } else if (id == R.id.nav_doctor_visit) {
            title = "Visit Detail";
            try {
                DoctorVisit_tab doctorVisit_tab = new DoctorVisit_tab();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, doctorVisit_tab).commitAllowingStateLoss();
            } catch (Exception ex) {

            }
        } else if (id == R.id.nav_healthTips) {
            title = "Health Tips";
            try {
                Health_tips_fragment health_tips_fragment = new Health_tips_fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, health_tips_fragment).commitAllowingStateLoss();
            } catch (Exception ex) {

            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        getSupportActionBar().setTitle(title);
        return true;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void getPersonal_Data() {
        try {

            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Call<TblPersoanlDetailsPojo> call = service.getPersonalImage("SelectProfile",intResId,int_perId);

            call.enqueue(new Callback<TblPersoanlDetailsPojo>() {
                @Override
                public void onResponse(Call<TblPersoanlDetailsPojo> call, Response<TblPersoanlDetailsPojo> response) {
                    String responsee = String.valueOf(response.code());
                    if (response.isSuccessful()) {

                        try {
                            generateEmployeeList((ArrayList<PersoanlDetail>) response.body().getPersoanlDetails());
                        } catch (Exception ex) {

                        }
                    } else {


                    }

                }

                @Override
                public void onFailure(Call<TblPersoanlDetailsPojo> call, Throwable t) {


                }
            });
        } catch (Exception ex) {

        }
    }

    public void generateEmployeeList(ArrayList<PersoanlDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                String vchProfile = taskListDataList.get(0).getVchProfile().toString();
                String url=RetrofitInstance.Image_URL+vchProfile;
                try
                {
                    Glide.with(MainActivity.this)
                            .load(url) // image url
                            .error(R.mipmap.profile)
                            .centerCrop()// any image in case of error
                            .into(profile_Img);
                }catch (Exception ex)
                {
                    ex.getMessage();
                }


            } else {

            }

        } catch (Exception ex) {
            ex.getMessage();
        }

    }
}
