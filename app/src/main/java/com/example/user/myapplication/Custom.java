package com.example.user.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class Custom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

       // int width=dm.widthPixels;
        //int height=dm.heightPixels;
      //  layout.setMinimumWidth((int)(displayRectangle.width() * 0.9f));
      //  layout.setMinimumHeight((int)(displayRectangle.height() * 0.9f));
    //    getWindow().setLayout((int)width()*.8,(height)*.6);

       // int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
       // int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);

     //   alertDialog.getWindow().setLayout(width, height);
    }
}
