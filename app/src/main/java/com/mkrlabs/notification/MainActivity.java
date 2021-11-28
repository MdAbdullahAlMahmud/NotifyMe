package com.mkrlabs.notification;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mkrlabs.notify.Notify;
import com.mkrlabs.notify.NotifyMe;

public class MainActivity extends AppCompatActivity {


    Button notifyButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notifyButton = findViewById(R.id.notifyButton);


        notifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                NotifyMe.with(MainActivity.this)
                        .channelInfo("CHANNEL_NAME","CHANNEL_ID","CHANNEL_DESCRIPTION")
                        .setTitle("Test")
                        .setContent("This is the body of notification")
                        .setAutoCancel(true)
                        .setImportance(NotifyMe.NotifyMePriority.HIGH)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .showNotify();
                Toast.makeText(MainActivity.this, "Toast", Toast.LENGTH_SHORT).show();

            }
        });




    }
}