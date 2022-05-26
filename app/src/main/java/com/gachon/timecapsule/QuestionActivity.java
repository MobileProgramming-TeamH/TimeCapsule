package com.gachon.timecapsule;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;


public class QuestionActivity extends AppCompatActivity {
    private final static String TAG = "Diary";
    private EditText edit_text;
    private Button save_button;
    private String answer="";
    private String question="";
    TextView question_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        final Bundle bundle = new Bundle();

        new Thread() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://steemit.com/kr/@centering/1010").get();
                    Elements sentence = doc.select("ol");

                    String[] questions = sentence.text().split("\\?");
                    //  질문 저장할 때 물음표 기준으로 저장.


                    int j = (int) (Math.random() * questions.length);
                    //Log.d("Qtion", "Q:" + questions[j] + " ? ");

                    bundle.putString("Question", questions[j]+" ? ");
                    Message msg = handler.obtainMessage();
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        Intent intent = getIntent();
        String Uid=intent.getExtras().getString("Uid");
        String date=intent.getExtras().getString("Date");

        edit_text=(EditText)findViewById(R.id.Edit);
        question_text_view=findViewById(R.id.questionView);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

        save_button=(Button)findViewById(R.id.button);//활성화 버튼
        save_button.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                answer=edit_text.getText().toString();
                String question = question_text_view.getText().toString();
                FirebaseFirestore database = FirebaseFirestore.getInstance();
                database.collection("Users").document(Uid).collection("Date").document(date).update("Answer",answer);
                database.collection("Users").document(Uid).collection("Date").document(date).update("Question",question);

                Toast.makeText(getApplicationContext(), "save.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            question_text_view.setText(bundle.getString("Question"));
        }
    };

}