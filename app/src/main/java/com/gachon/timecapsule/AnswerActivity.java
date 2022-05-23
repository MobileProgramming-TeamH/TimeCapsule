package com.gachon.timecapsule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class AnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        Intent intent = getIntent();
        String answer=intent.getExtras().getString("Ans");
        String question=intent.getExtras().getString("Que");

        TextView Question = (TextView) findViewById(R.id.Question);
        TextView Answer = (TextView) findViewById(R.id.Answer);
        Button back_button=(Button)findViewById(R.id.button);

        Question.setText(question);
        Answer.setText(answer);

        back_button=(Button)findViewById(R.id.button);
        back_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                finish();
            }
        });


    }
}