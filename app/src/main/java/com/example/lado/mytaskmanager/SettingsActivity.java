package com.example.lado.mytaskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.TokenWatcher;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

public class SettingsActivity extends AppCompatActivity {

    DayNightSwitch dayNightSwitch;
    View background_view;
    View background_view_main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dayNightSwitch = (DayNightSwitch) findViewById(R.id.day_night_switch);
        background_view = (View) findViewById(R.id.backgroung_view);

        final LayoutInflater factory = getLayoutInflater();
        final View textEntryView = factory.inflate(R.layout.content_main, null);
        background_view_main = (View) textEntryView.findViewById(R.id.backgroung_view_main);

        //dayNightSwitch.setDuration(450);
        dayNightSwitch.setListener(new DayNightSwitchListener() {
            @Override
            public void onSwitch(boolean b) {
                if(b) {
                    Toast.makeText(SettingsActivity.this, "Night mode selected", Toast.LENGTH_SHORT).show();
                    background_view.setAlpha(1f);
                    background_view_main.setAlpha(1f);
                }
                else {
                    Toast.makeText(SettingsActivity.this, "Day mode selected", Toast.LENGTH_SHORT).show();
                    background_view.setAlpha(0f);
                    background_view_main.setAlpha(0f);
                }
            }
        });
    }
}
