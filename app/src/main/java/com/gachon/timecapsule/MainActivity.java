package com.gachon.timecapsule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

        Button goToQuestionBtn = findViewById(R.id.goToQuestionBtn);
        Button goToDiaryBtn = findViewById(R.id.goToDiaryBtn);

        Intent moveToQuestion = new Intent(this, QuestionActivity.class);
        Intent moveToDiary = new Intent(this, DiaryActivity.class);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "Selected Date");

                date = year + "년 " + (month + 1) + "월 " + dayOfMonth + "일";


            }
        });

        goToQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToQuestion.putExtra("Date", date);
                startActivity(moveToQuestion);
            }
        });

        goToDiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToDiary.putExtra("Date", date);
                startActivity(moveToDiary);
            }
        });


    }
}