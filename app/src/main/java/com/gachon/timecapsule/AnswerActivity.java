package com.gachon.timecapsule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class AnswerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        Intent intent = getIntent();
        String date = intent.getExtras().getString("Date");
        String Question = intent.getExtras().getString("Question");
        String Answer = intent.getExtras().getString("Answer");

        TextView text1=(TextView) findViewById(R.id.Answer);
        text1.setText(date+"\n\n"+"Question :\n"+Question+"\n\n"+"Answer :\n"+Answer);



        Button btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}