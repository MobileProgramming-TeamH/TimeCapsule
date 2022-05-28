package com.gachon.timecapsule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "Calendar";
    private final static String TAG_TOKEN = "TOKEN";
    private int check=0;
    private String Check_ques;
    private String Check_answ;
    private String Check_Diary;
    private String date = "";
    private String Temp_date = "";
    private String Uid;
    private String Question;
    private String Diary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = (CalendarView)findViewById(R.id.calendarView);
        Bundle bundle = null;

        Intent intent = getIntent();
        Uid = intent.getExtras().getString("Uid");

        Button goToQuestionBtn = findViewById(R.id.goToQuestionBtn);
        Button goToDiaryBtn = findViewById(R.id.goToDiaryBtn);
        Button goToManual=findViewById(R.id.manual);
        Button goToDiaryAnswer=findViewById(R.id.goToDiaryAnswerBtn);
        Intent moveToQuestion = new Intent(this, QuestionActivity.class);
        Intent moveToDiary = new Intent(this, DiaryActivity.class);
        Intent moveToAnswer = new Intent(this, AnswerActivity.class);
        Intent moveToManual = new Intent(this, ManualActivity.class);
        Intent moveToDiaryAnswer = new Intent(this, DiaryAnswerActivity.class);

        goToManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(moveToManual);
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {


                Log.d(TAG, "Selected Date");
                date = year + "년 " + (month + 1) + "월 " + dayOfMonth + "일";

                Calendar cal=Calendar.getInstance();
                cal.set(year,month,dayOfMonth);
                cal.add(Calendar.DAY_OF_MONTH,-10);
                Temp_date= cal.get(Calendar.YEAR) + "년 " + (cal.get(Calendar.MONTH) + 1) + "월 " + cal.get(Calendar.DAY_OF_MONTH) + "일";
                System.out.println("DATE : "+date+"TEMP : "+Temp_date);

                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                /////////////////////////////////////////////
                FirebaseFirestore database = FirebaseFirestore.getInstance();
                DocumentReference docRef = database.collection("Users").document(Uid).collection("Date").document(Temp_date);

                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                Check_ques =(String)document.get("Question");
                                Check_answ=(String)document.get("Answer");
                                Check_Diary=(String)document.get("Diary");
                                System.out.println("11Here NOT EXIST:::"+Check_ques+check);

                                if(Check_Diary==null&&Check_ques!=null) {
                                    moveToAnswer.putExtra("Date", Temp_date);
                                    moveToAnswer.putExtra("Question", Check_ques);
                                    moveToAnswer.putExtra("Answer", Check_answ);
                                    startActivity(moveToAnswer);
                                }
                                else if(Check_Diary!=null&&Check_ques!=null){
                                    moveToAnswer.putExtra("Date", Temp_date);
                                    moveToAnswer.putExtra("Question", Check_ques);
                                    moveToAnswer.putExtra("Answer", Check_answ);
                                    startActivity(moveToAnswer);
                                }


                            } else {

                                Log.d(TAG, "No such document");

                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());

                        }
                    }
                });

                /////////////////////////////////////////////
                if(user!=null){

                    Log.d(TAG_TOKEN,"YES");
                    saveToDatabase(user, date);
                    //////////////////////////

                }
                else{
                    Log.d(TAG_TOKEN,"NO");
                }
            }
        });

        goToDiaryAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToDiaryAnswer.putExtra("Date", date);
                moveToDiaryAnswer.putExtra("Diary", Diary);
                moveToDiaryAnswer.putExtra("Uid", Uid);
                startActivity(moveToDiaryAnswer);
            }
        });

        goToQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToQuestion.putExtra("Question", Question);
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

        FirebaseFirestore database = FirebaseFirestore.getInstance();


        DocumentReference docRef = database.collection("Users").document(Uid).collection("Date").document(date);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Diary=(String)document.get("Diary");
                        /*
                        moveToAnswer.putExtra("Ans", Answer);
                        moveToAnswer.putExtra("Que", Question);
                        startActivity(moveToAnswer);*/


                    } else {

                        Log.d(TAG, "No such document");
                        System.out.println("Here NOT EXIST:::");
                        HashMap<Object, String> info = new HashMap<>();
                        HashMap<Object, String> Read = new HashMap<>();
                        info.put("Diary", null);
                        info.put("Question", null);
                        info.put("Answer", null);


                        database.collection("Users").document(Uid).collection("Date").document(date).set(info);
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    System.out.println("Here NOT EXIST22:::");
                }
            }
        });
        DocumentReference docRef2 = database.collection("Users").document(Uid).collection("Date").document("Question_List");

        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Question=(String)document.get("Question_List");
                        /*
                        moveToAnswer.putExtra("Ans", Answer);
                        moveToAnswer.putExtra("Que", Question);
                        startActivity(moveToAnswer);*/
                        System.out.println("Here EXIST:::");

                    } else {

                        Log.d(TAG, "No such document");
                        System.out.println("Here NOT EXIST:::");

                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    System.out.println("Here NOT EXIST22:::");
                }
            }
        });

    }
}