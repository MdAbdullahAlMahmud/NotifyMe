package com.mkrlabs.notify;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotifyMe {


    private NotificationCompat.Builder builder;
    private Context context;
    private String channelName="DEFAULT_CHANNEL_NAME";
    private String channelDescription="DEFAULT_CHANNEL_DESC";
    private String channelId="DEFAULT_ID";

    private String DEFAULT_TITLE ="This is Title";
    private String DEFAULT_DESCRIPTION ="This is Description";

    private String title=DEFAULT_TITLE;
    private String content=DEFAULT_DESCRIPTION;
    private boolean vibrate = false;
    private boolean autoCancel;
    private int smallIcon;
    private Intent intent;
    private int id;
    private int color=-1;

    public enum NotifyMePriority { MIN, LOW, HIGH, MAX }

    private int notification_importance,notification_importance_over_oreo;
    public interface CHANNEL_INFO {
        String ID = "notify_channel_id", NAME = "notify_channel_name", DESCRIPTION = "notify_channel_description";
    }
    private NotifyMe(Context context){
        this.context = context;

        ApplicationInfo applicationInfo = this.context.getApplicationInfo();

        this.id = (int) System.currentTimeMillis();
        this.setSmallIcon(applicationInfo.icon);

        setDefaultPriority();

        channelInfo(CHANNEL_INFO.NAME,CHANNEL_INFO.ID,CHANNEL_INFO.DESCRIPTION);
        builder = new NotificationCompat.Builder(context,channelId);


    }


    public NotifyMe setTitle(@NonNull String title){

        if (!title.isEmpty()){
            this.title =title;
        }
        return this;
    }

    public NotifyMe setContent(@NonNull String content){

        if (!content.isEmpty()){
            this.content = content;
        }
        return this;
    }

     public NotifyMe setChannelId(@NonNull String channelId){

            if (!channelId.isEmpty()){
                this.channelId = channelId;
            }
            return this;
        }

    public NotifyMe setChannelName(@NonNull String channelName) {
        if (!channelName.isEmpty())
            this.channelName = channelName;
        return this;
    }


    public NotifyMe setChannelDescription(@NonNull String channelDescription) {
        if (!channelDescription.isEmpty())
            this.channelDescription = channelDescription;
        return this;
    }


    public NotifyMe setImportance(NotifyMePriority priority){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            switch (priority) {
                case MIN:
                    this.notification_importance = NotificationCompat.PRIORITY_MIN;
                    this.notification_importance_over_oreo = NotificationManager.IMPORTANCE_MIN;
                    break;

                case LOW:
                    this.notification_importance = NotificationCompat.PRIORITY_LOW;
                    this.notification_importance_over_oreo = NotificationManager.IMPORTANCE_LOW;
                    break;

                case HIGH:
                    this.notification_importance = NotificationCompat.PRIORITY_HIGH;
                    this.notification_importance_over_oreo = NotificationManager.IMPORTANCE_HIGH;
                    break;

                case MAX:
                    this.notification_importance = NotificationCompat.PRIORITY_MAX;
                    this.notification_importance_over_oreo = NotificationManager.IMPORTANCE_MAX;
                    break;



            }
        }


        return this;
    }

    private void setDefaultPriority(){

        this.notification_importance = NotificationCompat.PRIORITY_DEFAULT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.notification_importance_over_oreo = NotificationManager.IMPORTANCE_DEFAULT;
        }
    }


    public NotifyMe setVibration(boolean vibrate){

        this.vibrate= vibrate;
        return this;
    }
      public NotifyMe setAutoCancel(boolean autoCancel){

        this.autoCancel= autoCancel;
        return this;
    }

    public NotifyMe setSmallIcon(@DrawableRes int smallIcon){
        this.smallIcon = smallIcon;
        return this;
    }


    public NotifyMe setTargetActivity(@NonNull Intent intent){
        this.intent = intent;
        return  this;

    }

    public NotifyMe setNotifyMeId(int id){

        this.id= id;
        return this;
    }

    public static NotifyMe with(@NonNull Context context){
        return new NotifyMe(context);
    }


    public   NotifyMe channelInfo(@NonNull String channelName , String channelId , String channelDescription){
        if (this.channelId.isEmpty()){
            this.channelId =channelId;
        }
        if (channelName.isEmpty()){
            this.channelName =channelName;
        }
        if (this.channelDescription.isEmpty()){
            this.channelDescription =channelDescription;
        }
        return this;
    }

    private NotificationCompat.Builder getBuilder(){
        return builder;
    }


    public void showNotify(){

        if (context==null)return;
        NotificationManager notificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager==null) return;

        int notificationLightColor;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) notificationLightColor = color == -1 ? Color.BLACK : context.getResources().getColor(color, null);
        else notificationLightColor = color == -1 ? Color.BLACK : context.getResources().getColor(color);
        builder.setColor(notificationLightColor);

        getBuilder().setContentTitle(title)
                .setSmallIcon(smallIcon)
                .setContentText(content)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(autoCancel)
                .setWhen(System.currentTimeMillis())
                .setColor(notificationLightColor);


        // for version > O (oreo)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channelId,channelName, notification_importance_over_oreo);

            channel.setDescription(this.channelDescription);
            channel.enableLights(true);
            channel.enableVibration(this.vibrate);
            channel.setLightColor(notificationLightColor);

        }else {
            getBuilder().setPriority(this.notification_importance);
        }

        // target launch intent

        if (this.intent !=null){
            PendingIntent pendingIntent = PendingIntent.getActivity(context,id,this.intent,PendingIntent.FLAG_CANCEL_CURRENT);
            getBuilder().setContentIntent(pendingIntent);
        }

        notificationManager.notify(id,getBuilder().build());

    }


}

