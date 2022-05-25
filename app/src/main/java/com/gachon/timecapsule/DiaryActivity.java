package com.gachon.timecapsule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;

public class DiaryActivity extends AppCompatActivity {
    private final static String TAG = "Diary";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        Intent intent = getIntent();
        String date = intent.getExtras().getString("Date");
        String Uid = intent.getExtras().getString("Uid");

        Button saveDiaryBtn = findViewById(R.id.saveDiaryBtn);
        EditText et_todayDiaryContent = findViewById(R.id.et_todayDiaryContent);

        Log.d(TAG, "get date: " + date);

        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText(date);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        saveDiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String diaryContent = et_todayDiaryContent.getText().toString();
                Log.d(TAG, diaryContent);
                database.collection("Users").document(Uid).collection("Date")
                        .document(date).update("Diary", diaryContent)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                            }
                        });

                Toast.makeText(getApplicationContext(), "save.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}