package com.gachon.timecapsule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "Calendar";

    private String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = (CalendarView)findViewById(R.id.calendarView);
        Bundle bundle = null;
        Intent moveToDiary = new Intent(this, DiaryActivity.class);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "Selected Date");

                date = year + "년 " + (month + 1) + "월 " + dayOfMonth + "일";

                moveToDiary.putExtra("Date", date);

                startActivity(moveToDiary);

            }
        });
    }
}