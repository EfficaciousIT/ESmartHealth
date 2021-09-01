package com.mobi.efficacious.esmarthealth.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
   public static String drVisitId,DrId;
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        drVisitId = intent.getStringExtra("intDrVisitId");
        DrId = intent.getStringExtra("intDrId");
        Intent intent1 = new Intent(context, NewIntentService.class);
        context.startService(intent1);
    }
}
