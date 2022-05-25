package com.gachon.timecapsule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class DiaryActivity extends AppCompatActivity {
    private final static String TAG = "Diary";
    HashMap<Object, String> info = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        Intent intent = getIntent();
        final String date = intent.getExtras().getString("Date");

        Button saveDiaryBtn = findViewById(R.id.saveDiaryBtn);
        EditText et_todayDiaryContent = findViewById(R.id.et_todayDiaryContent);

        Log.d(TAG, "Intent");

        Log.d(TAG, "get date" + date);

        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText(date);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            Log.d("TOKEN","YES");
            saveToDatabase(user, date);
        }

        else{
            Log.d("N_TOKEN","NO");
        }

        saveDiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String diaryContent = et_todayDiaryContent.getText().toString();
                Log.d(TAG, diaryContent);
                info.put("Diary", diaryContent);
                saveToDatabase(user, date);
                Toast.makeText(getApplicationContext(), "save.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void saveToDatabase(FirebaseUser user, String documentName){
        for(UserInfo profile : user.getProviderData()){

            String Uid=profile.getUid();

            Log.d("TOKEN",Uid);
            String UId=Uid;

            FirebaseFirestore database = FirebaseFirestore.getInstance();
            database.collection("Users").document(Uid).collection("Date").document(documentName).set(info);
        }
    }
}