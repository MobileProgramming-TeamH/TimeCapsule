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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    MainHandler handler;
    TextView question_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();
        String Uid=intent.getExtras().getString("Uid");
        String date=intent.getExtras().getString("Date");

        edit_text=(EditText)findViewById(R.id.Edit);
        question_text_view=findViewById(R.id.questionView);

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
    class CrawlingThread extends Thread{
        private String URL = "https://steemit.com/kr/@centering/1010";
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(URL).get();
                    Elements sentence = doc.select("ol");

                    String[] questions = sentence.text().split("\\?");
                    //  질문 저장할 때 물음표 기준으로 저장.

                   /* for(int i=0; i<questions.length; i++) {
                        Log.d("Qtion", "Q:" + questions[i]+" ? ");
                    }*/

                    int j = (int) (Math.random() * questions.length);

                    Message msg = handler.obtainMessage();
                    Bundle bundle=new Bundle();
                    bundle.putString("Question", questions[j]);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                    //질문 리스트 중에서 하나만 메인쓰레드로 핸들러를 이용하여 보냄

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

}
    class MainHandler extends Handler{
    @Override
        public void handleMessage(@NonNull Message msg){
        super.handleMessage(msg);

        Bundle bundle=msg.getData();
        String question=bundle.getString("Question");

        question_text_view.setText(question);

        }
    }
}

