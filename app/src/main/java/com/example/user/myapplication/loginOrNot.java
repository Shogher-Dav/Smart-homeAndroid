package com.example.user.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class loginOrNot extends AppCompatActivity {
    SharedPreferences.Editor editor;
    String prefEmail = "", prefPassword = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_not);
        SharedPreferences settings = getSharedPreferences("sh1", 0);
        editor = settings.edit();
        prefEmail = settings.getString("preEmail", "");
        prefPassword = settings.getString("prePassword", "");
      //Toast.makeText(this,prefEmail+" "+prefPassword,Toast.LENGTH_SHORT).show();
        if(!prefEmail.isEmpty() && !prefPassword.isEmpty()){
            Intent i = new Intent(loginOrNot.this, HomePage.class);
            startActivity(i);
            finish();
        }else{
            Intent i = new Intent(loginOrNot.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            finish();
        }
    }

}
