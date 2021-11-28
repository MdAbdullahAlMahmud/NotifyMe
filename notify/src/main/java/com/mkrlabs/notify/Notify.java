package com.mkrlabs.notify;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class Notify {

    private Notify notify;
    private Context context;

    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private String channel_name;
    private String channel_description;
    public Notify(Context context){
        this.context = context;
    }

    public  Notify init(String channel_name,String channel_description) {
        notify = new Notify(context);
        this.channel_name =channel_name;
        this.channel_description = channel_description;
        setNotificationManage();

        return notify;
    }

    private void setNotificationManage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", channel_name, importance);
            channel.setDescription(channel_description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



    public void showNotify(){
       int id = (int) System.currentTimeMillis();

        notificationManager.notify(id,builder.build());

    }


    public Notify notifyBuilder(String channelID) {
        builder = new NotificationCompat.Builder(context, channelID);
        return notify;
    }


    public Notify setTitle(String title) {
        builder.setContentTitle(title);
        return notify;
    }

     public Notify setMessage(String message) {
        builder.setContentTitle(message);
        return notify;
    }



    public Notify setSmallIcon(int icon) {
        builder.setSmallIcon(icon);
        return notify;
    }



}
