package com.gachon.timecapsule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "Calendar";
    private final static String TAG_TOKEN = "TOKEN";

    private String date = "";
    private String Uid;

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
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Log.d(TAG_TOKEN,"YES");
                    saveToDatabase(user, date);
                }
                else{
                    Log.d(TAG_TOKEN,"NO");
                }
            }
        });

        goToQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToQuestion.putExtra("Date", date);
                moveToQuestion.putExtra("Uid", Uid);
                startActivity(moveToQuestion);
            }
        });

        goToDiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToDiary.putExtra("Date", date);
                moveToDiary.putExtra("Uid", Uid);
                startActivity(moveToDiary);
            }
        });
    }

    public void saveToDatabase(FirebaseUser user, String date){
        for(UserInfo profile : user.getProviderData()){

            Uid=profile.getUid();

            Log.d("TOKEN",Uid);
            String UId=Uid;

            HashMap<Object, String> info = new HashMap<>();

            info.put("Diary", null);
            info.put("Question", null);
            info.put("Answer", null);

            FirebaseFirestore database = FirebaseFirestore.getInstance();
            database.collection("Users").document(Uid).collection("Date").document(date).set(info);
        }
    }
}