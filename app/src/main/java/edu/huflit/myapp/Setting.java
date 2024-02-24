package edu.huflit.myapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class Setting extends AppCompatActivity {

    SeekBar sbarSize, sbarBright;
    Switch aSwitch;
    boolean nightMode;
    TextView tvTT;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sbarBright = (SeekBar) findViewById(R.id.brightnessSeekBar);
        sbarSize = (SeekBar) findViewById(R.id.fontSizeSeekBar);
        aSwitch = (Switch) findViewById(R.id.nightModeSwitch);

        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("night", false);

        if (nightMode)
        {
            aSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nightMode)
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", false);
                }
                else
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", true);
                }
                editor.apply();
            }
        });

        sbarSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float textSize = progress + 10;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }
        });


        //Setting độ sáng
        // chưa xonh nha
//        sbarBright.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//                WindowManager.LayoutParams attributes = getWindow().getAttributes();
//
//                attributes.screenBrightness = (float) progress / 100f;
//
//                getWindow().setAttributes(attributes);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                // Do nothing
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                // Do nothing
//            }
//        });
    }
}