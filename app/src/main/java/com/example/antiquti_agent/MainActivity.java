package com.example.antiquti_agent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

int n=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Handler handle = new Handler();
        handle.post(new Runnable() {
            @Override
            public void run() {
                ;
                n--;
                if(n==0){
                    Intent intent = new Intent(MainActivity.this,scanactivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    handle.postDelayed(this,1000);
                }
            }
        });
    }
}
