package com.example.user.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends MainActivity implements View.OnClickListener {
    Button light,camera,sensors,temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        light=(Button)findViewById(R.id.light);
        camera=(Button)findViewById(R.id.camera);
        sensors=(Button)findViewById(R.id.sensors);
        temperature=(Button)findViewById(R.id.temperature);
        light.setOnClickListener(this);
        camera.setOnClickListener(this);
        sensors.setOnClickListener(this);
        temperature.setOnClickListener(this);

    }
    public void back(View view) {
        prefEmail="";
        prefPassword="";
        Intent intent = new Intent(HomePage.this,MainActivity.class);
        intent.putExtra("check","true");
        SharedPreferences.Editor editor = getSharedPreferences("sh1", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.light:
                Intent lightIntent = new Intent(this, LightPage.class);//kapuma glxavor ejy erkordi het
                startActivity(lightIntent);
                break;
            case R.id.camera:
                Intent cameraIntent = new Intent(this, CameraPage.class);//kapuma glxavor ejy erkordi het
                startActivity(cameraIntent);
                break;
            case R.id.sensors:
                Intent sensorsIntent = new Intent(this, SensorsPage.class);//kapuma glxavor ejy erkordi het
                startActivity(sensorsIntent);
                break;
            case R.id.temperature:
                Intent temperatureIntent = new Intent(this, TemperaturePage.class);//kapuma glxavor ejy erkordi het
                startActivity(temperatureIntent);
                break;
            default:
                break;
        }

    }
    @Override
    public void onBackPressed() {
    }

}
