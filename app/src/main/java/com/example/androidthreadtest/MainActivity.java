package com.example.androidthreadtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.BatchUpdateException;

//异步消息处理机制
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int UPDATE_TEXT = 1;

    private TextView text;

    private Handler handler = new Handler(){
        //重写hanldeMessage
        public void handleMessage(Message message){
            switch (message.what){
                case UPDATE_TEXT:
                    //进行UI操作
                    text.setText("Nice to meet you");
                    break;
                    default:
                        break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView)findViewById(R.id.text);
        Button changeText = (Button)findViewById(R.id.change_text);
        changeText.setOnClickListener(this);
    }

    //Android是不可以在子线程中更新UI的
//    public void onClick(View v){
//        switch (v.getId()){
//            case R.id.change_text:
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        text.setText("Nice to meet you");
//                    }
//                }).start();
//                break;
//            default:
//                break;
//        }
//    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.change_text:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = UPDATE_TEXT;
                        handler.sendMessage(message);//将message对象发送出去y
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
