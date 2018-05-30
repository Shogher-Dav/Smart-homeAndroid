package com.example.user.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LightPage extends AppCompatActivity implements View.OnClickListener {
    private ImageView kitchen, bedroom, living, bathroom;
    private Button rgbButton;
    private Switch curtainSwitch;
    private Dialog mydialog;
    private Map<Integer, Integer> lightsStates = new HashMap<>();
    private Drawable lightOff, lightOn;
    public static final int STATE_OFF = 0;
    public static final int STATE_ON = 1;
    private  ServerInterface serverInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_page);
        Retrofit retrofit = ApiFactory.getInstance().getRetrofit(HomePage.BASE_URL);
        serverInterface = retrofit.create(ServerInterface.class);

        initViews();
    }

    private void initViews() {
        kitchen = findViewById(R.id.kitchen_light);
        living = findViewById(R.id.living_light);
        bedroom = findViewById(R.id.bedroom_light);
        bathroom = findViewById(R.id.bathroom_light);
        curtainSwitch = findViewById(R.id.curtain_swich);

        kitchen.setOnClickListener(this);
        bedroom.setOnClickListener(this);
        bathroom.setOnClickListener(this);
        living.setOnClickListener(this);
        curtainSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Call<JsonObject> call = serverInterface.changeCurtainState(compoundButton.isChecked() ? 1 : 0, HomePage.getUserToken());
                Log.d("lalala", "request:  "  + call.request().url());
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("lalala", "response: "  + response.toString());
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("lalala", "fail: "  + t.getStackTrace());
                    }
                });
            }
        });
        rgbButton = findViewById(R.id.rgbButton);

        lightOff = this.getResources().getDrawable(R.drawable.lamp1);
        lightOn = this.getResources().getDrawable(R.drawable.yellowlamp);

        rgbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rgbIntent = new Intent(view.getContext(), Custom.class);
                startActivity(rgbIntent);
            }
        });

        for (int i=0; i<4; i++) {
            lightsStates.put(i, STATE_OFF);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.kitchen_light:
                changeStateAndSendData(v, 0, v.isSelected());
                break;
            case R.id.bedroom_light:
                changeStateAndSendData(v, 1, v.isSelected());
                break;
            case R.id.living_light:
                changeStateAndSendData(v, 2, v.isSelected());
                break;
            case R.id.bathroom_light:
               changeStateAndSendData(v, 3, v.isSelected());
        }
    }

    private void changeStateAndSendData(View view, int number, boolean isSelected) {
        int state = isSelected ? 1 : 0;
        view.setSelected(!isSelected);
        ((ImageView)view).setImageDrawable(isSelected ? lightOff : lightOn);

        Call<JsonObject> call = serverInterface.setLight(number, state, HomePage.getUserToken());
        Log.d("lalala", "request:  "  + call.request().url());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("lalala", "response: "  + response.toString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("lalala", "fail: "  + t.getStackTrace());
            }
        });
    }

    public void myCustomAlertdialog() {
        mydialog = new Dialog(LightPage.this);
        // mydialog.addContentView(R.layout.activity_custom);
        mydialog.setTitle("My custom dialog");

    }
}