package com.example.antiquti_agent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class homepage extends AppCompatActivity {
ImageView i1;
ImageView i2;
ImageView i3;
ImageView i4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);



    }
    public void click(View view){
        switch (view.getId()){
            case R.id.shop:
                Intent i=new Intent(this,shop.class);
                startActivity(i);
                break;
            case R.id.inventory:
                Intent i1=new Intent(this,inventory.class);
                startActivity(i1);
                break;
            case R.id.user:
                Intent i2=new Intent(this,user.class);
                startActivity(i2);
                break;
            case R.id.home:
                Intent i3=new Intent(this,homepage.class);
                startActivity(i3);
                break;

        }
    }
}