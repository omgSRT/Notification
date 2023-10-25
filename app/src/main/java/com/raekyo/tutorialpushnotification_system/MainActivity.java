package com.raekyo.tutorialpushnotification_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnPushNotification;

    NotificationChannel notificationChannel;
    String CHANNEL_ID = "my_channel_01";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnPushNotification = (Button) findViewById(R.id.btnPushNotification);

        createNotificationChannel();
        btnPushNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushNotification();
            }
        });

    }

    private void pushNotification() {
        Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.landscape);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("We have a gift for all of you !!!!")
                .setContentText("Go to destination")
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(picture)
                        .bigLargeIcon(picture)
                        .setBigContentTitle("This is expandable"));

        Intent notificationIntent = new Intent(this, ResponseNotification.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,  PendingIntent.FLAG_MUTABLE);

        Intent notificationIntent2 = new Intent(this, ResponseNotification2.class);
        PendingIntent contentIntent2 = PendingIntent.getActivity(this, 0, notificationIntent2,  PendingIntent.FLAG_MUTABLE);

        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(true);

        builder.addAction(R.drawable.cave_man, "DETAIL", contentIntent);
        builder.addAction(R.drawable.cave_man, "DENY", contentIntent2);

        Notification notification = builder.build();

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(0, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}