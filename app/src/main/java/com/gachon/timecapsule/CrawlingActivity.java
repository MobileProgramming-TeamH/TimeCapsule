package com.gachon.timecapsule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CrawlingActivity extends AppCompatActivity {

    private String URL = "https://steemit.com/kr/@centering/1010";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Bundle bundle=new Bundle();

        Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                Bundle bundle =msg.getData();
            }
        };

        new Thread(){
            @Override
            public void run(){
                try{
                    Document doc= Jsoup.connect(URL).get();
                    Elements sentence=doc.select("ol");

                    String[] questions=sentence.text().split("\\?");
                    //  질문 저장할 때 물음표 기준으로 저장.

                  /*  for(int i=0; i<questions.length; i++) {
                        Log.d("Qtion", "Q:" + questions[i]+" ? ");
                    }*/

                    int j=(int)(Math.random()*questions.length);
                    bundle.putString("Question",questions[j]);
                    Message msg=handler.obtainMessage();
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                    //질문 리스트 중에서 하나만 메인쓰레드로 번들을 통해서 보냄



                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }.start();
    }

}