package com.mobi.efficacious.esmarthealth.Services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.mobi.efficacious.esmarthealth.Interface.DataService;
import com.mobi.efficacious.esmarthealth.R;
import com.mobi.efficacious.esmarthealth.activity.NotificationActivity;
import com.mobi.efficacious.esmarthealth.entity.TblVisitDetailsPojo;
import com.mobi.efficacious.esmarthealth.entity.VisitDetail;
import com.mobi.efficacious.esmarthealth.webApi.RetrofitInstance;
;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class NewIntentService extends IntentService {
    private static final int NOTIFICATION_ID = 3;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String intResId;
    List<String> Alarmdata;
    MediaPlayer mPlayer2;
    int Status=0;
    public NewIntentService() {
        super("NewIntentService");
    }

    @Override
    protected void onHandleIntent(Intent inteent) {
        if(Status==0)
        {
            Status=1;
            Intent intent = new Intent(this,NotificationActivity.class);
            intent.putExtra("intDrVisitId",AlarmReceiver.drVisitId);
            intent.putExtra("intDrId",AlarmReceiver.DrId);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int notificationId = 1;
            String channelId = "channel-01";
            String channelName = "Channel Name";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);

                assert notificationManager != null;
                notificationManager.createNotificationChannel(mChannel);
            }
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);


//            mPlayer2= MediaPlayer.create(this, R.raw.kalimba);
//            mPlayer2.start();
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.mipmap.e_smart_health)
                    .setContentTitle("Medicine Time")
                    .setContentText("Medicine Time")
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                    .setContentIntent(pendingIntent);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntent(intent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            mBuilder.setContentIntent(resultPendingIntent);

            notificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
            alarmset("SelectAlarmTime");
        }

    }
    public void alarmset(String command)
    {
        try
        {
            settings = getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
            intResId = settings.getString("TAG_ResId", "");
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Call<TblVisitDetailsPojo> call = service.getAlarmDetails(command,intResId);

            call.enqueue(new Callback<TblVisitDetailsPojo>() {
                @Override
                public void onResponse(Call<TblVisitDetailsPojo> call, Response<TblVisitDetailsPojo> response) {
                    String responsee= String.valueOf(response.code());
                    if (response.isSuccessful()) {

                        try{
                            generateAlarmList((ArrayList<VisitDetail>) response.body().getVisitDetails());
                        }catch (Exception ex)
                        {

                        }
                    } else {


                    }

                }

                @Override
                public void onFailure(Call<TblVisitDetailsPojo> call, Throwable t) {


                }
            });



        }catch (Exception ex)
        {

        }

    }
    public  void generateAlarmList(ArrayList<VisitDetail> taskListDataList) {
        try {
            String alarmstatu="0";
            int alarmHour,alarmMinute;
            if((taskListDataList != null && !taskListDataList.isEmpty()))
            {
                String AlarmTime=taskListDataList.get(0).getMedTime().toString();
                String Dr_id=taskListDataList.get(0).getIntDocId().toString();
                String Dr_visit_id=taskListDataList.get(0).getIntDrVisitId().toString();
                Alarmdata = Arrays.asList(AlarmTime.split(":"));
                alarmHour= Integer.parseInt(Alarmdata.get(0));
                alarmMinute= Integer.parseInt(Alarmdata.get(1));
                if(alarmstatu.contentEquals("1"))
                {
                    Intent alarmIntent = new Intent(this, AlarmReceiver.class);
                    alarmstatu="1";
                    alarmIntent.putExtra("intDrVisitId",Dr_visit_id);
                    alarmIntent.putExtra("intDrId",Dr_id);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
                    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, alarmHour);
                    calendar.set(Calendar.MINUTE, alarmMinute);
                    calendar.set(Calendar.SECOND, 0);
                    manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY, pendingIntent);
                }

               // mPlayer2.stop();
            }else
            {
                alarmset("SelectAlarmTimeStart");
            }

        }catch (Exception ex)
        {

        }

    }

}