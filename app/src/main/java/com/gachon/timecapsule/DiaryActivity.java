package com.gachon.timecapsule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DiaryActivity extends AppCompatActivity {
    private final static String TAG = "Diary";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        Intent intent = getIntent();
        final String date = intent.getExtras().getString("Date");

        Log.d(TAG, "Intent");

        Log.d(TAG, "get date" + date);

        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText(date);

    }
}