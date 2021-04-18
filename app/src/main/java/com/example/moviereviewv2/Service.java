package com.example.moviereviewv2;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.moviereviewv2.activities.HomeScreen;
import static com.example.moviereviewv2.ForegroundNotification.CHANNEL_ID;

public class Service extends android.app.Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        String message = "Tap here to return to app";

        Intent notificationIntent = new Intent(this, HomeScreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_baseline_movie_24)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
        super.onCreate();
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    public void onDestroy() {
        super.onDestroy();
    }
}
