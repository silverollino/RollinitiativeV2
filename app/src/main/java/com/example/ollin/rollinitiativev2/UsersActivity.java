package com.example.ollin.rollinitiativev2;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UsersActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewName;
    private Button rollInitiativebtn;

    private final String CHANNEL_ID = "personal_notifications";
    private final int NOTIFICATION_ID = 001;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        textViewName = (TextView) findViewById(R.id.text1);
        rollInitiativebtn = (Button)findViewById(R.id.RollInitiativeBTN);
        rollInitiativebtn.setOnClickListener(this);
        String nameFromIntent = getIntent().getStringExtra("EMAIL");
        textViewName.setText(getString(R.string.welcome) + nameFromIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.RollInitiativeBTN:
                notificationShow(v);
                Intent rollIntent = new Intent(getApplicationContext(), EncounterListActivity.class);
                startActivity(rollIntent);
                break;
        }
    }

    public void notificationShow(View view){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle(getString(R.string.notification_title))
        .setContentText(getString(R.string.notification_contents_text))
        .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }
}
