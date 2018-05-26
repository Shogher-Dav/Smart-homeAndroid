package com.example.user.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LightPage extends AppCompatActivity implements View.OnClickListener {
    ImageView lampOn, lampOff, lampOn1, lampOff1;
    Boolean c, b;
    Button rgbButton;
    Dialog mydialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_page);
        {
            c = false;
            b = false;
            lampOn = (ImageView) findViewById(R.id.lampOn);
            lampOff = (ImageView) findViewById(R.id.lampOff);
            lampOn1 = (ImageView) findViewById(R.id.lampOn1);
            lampOff1 = (ImageView) findViewById(R.id.lampOff1);
            lampOn.setOnClickListener(this);
            lampOff.setOnClickListener(this);
            lampOn1.setOnClickListener(this);
            lampOff1.setOnClickListener(this);
            rgbButton=(Button)findViewById(R.id.rgbButton);

            rgbButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent rgbIntent = new Intent(view.getContext(), Custom.class);
                    startActivity(rgbIntent);
                }
            });
        }



    }


    @Override
    public void onClick(View v) {
      if(c) {
           //miacvat e anjatvum e
            lampOff.setVisibility(View.VISIBLE);
            lampOn.setVisibility(View.INVISIBLE);
               c=false;
       }
          else {
           //anjatvat e mianum e
            lampOn.setVisibility(View.VISIBLE);
           lampOff.setVisibility(View.INVISIBLE);
              c=true;
         }
//        if(b) {
//            lampOff1.setVisibility(View.VISIBLE);
//            lampOn1.setVisibility(View.INVISIBLE);
//            b=false;
//            c=false;
//        }
//        else {
//            lampOn1.setVisibility(View.VISIBLE);
//            lampOff1.setVisibility(View.INVISIBLE);
//
//            b=true;
//            c=false;
//
//        }
        switch (v.getId()) {
            case R.id.lampOn:
                lampOn.setVisibility(View.INVISIBLE);
                lampOff.setVisibility(View.VISIBLE);

            case R.id.lampOff:
                lampOn.setVisibility(View.VISIBLE);
                lampOff.setVisibility(View.INVISIBLE);


        }
    }
    public void myCustomAlertdialog(){
        mydialog=new Dialog(LightPage.this);
       // mydialog.addContentView(R.layout.activity_custom);
        mydialog.setTitle("My custom dialog");

    }
}