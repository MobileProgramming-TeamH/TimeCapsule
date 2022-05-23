package com.gachon.timecapsule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class QuestionActivity extends AppCompatActivity {
    private final static String TAG = "Diary";
    private EditText edit_text;
    private Button save_button;
    private String answer="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();
        String Uid=intent.getExtras().getString("Uid");
        String date=intent.getExtras().getString("Date");


        edit_text=(EditText)findViewById(R.id.Edit);

        save_button=(Button)findViewById(R.id.button);//활성화 버튼
        save_button.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                answer=edit_text.getText().toString();
                FirebaseFirestore database = FirebaseFirestore.getInstance();
                database.collection("Users").document(Uid).collection("Date").document(date).update("Answer",answer);
                finish();
            }
        });

    }
}