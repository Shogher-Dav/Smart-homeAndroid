package com.example.user.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

public class HomePage extends AppCompatActivity implements View.OnClickListener {
    public static final String LOG_TAG = "home_tag";
    public static final String BASE_URL = "http://62.109.14.103:8383/";
    private Button light, camera, sensors, temperature;
    public static String userToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initViews();
        fetchUserToken();
    }

    /**
     * FireBase-ic vercnum enq User Token@
     */
    private void fetchUserToken() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                @Override
                public void onComplete(@NonNull Task<GetTokenResult> task) {
                    if (task.isSuccessful()) {
                        userToken = task.getResult().getToken();
                    } else {
                        Log.d(LOG_TAG, "Failed to get user token: " + task.getException());
                    }
                }
            });
        }
    }

    private void initViews() {
        light = findViewById(R.id.light);
        camera = findViewById(R.id.camera);
        sensors = findViewById(R.id.sensors);
        temperature = findViewById(R.id.temperature);
        light.setOnClickListener(this);
        camera.setOnClickListener(this);
        sensors.setOnClickListener(this);
        temperature.setOnClickListener(this);
    }

    public static String getUserToken() {
        return userToken;
    }

    public void back(View view) {
        Intent intent = new Intent(HomePage.this, MainActivity.class);
        intent.putExtra("check", "true");
        SharedPreferences.Editor editor = getSharedPreferences("sh1", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.light:
                Intent lightIntent = new Intent(HomePage.this, LightPage.class);//kapuma glxavor ejy erkordi het
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
                Intent temperatureIntent = new Intent(HomePage.this, TemperaturePage.class);//kapuma glxavor ejy erkordi het
                startActivity(temperatureIntent);
                break;
            default:
                break;
        }
    }
}
