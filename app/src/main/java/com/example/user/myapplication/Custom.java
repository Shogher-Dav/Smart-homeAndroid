package com.example.user.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Custom extends AppCompatActivity implements View.OnClickListener{
    private Button green, yellow, blue, orange, aqua, red, violet, pink, random;
    private Map<Integer, Integer> colors = new HashMap<>();
    private ServerInterface serverInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        Retrofit retrofit = ApiFactory.getInstance().getRetrofit(HomePage.BASE_URL);
        serverInterface = retrofit.create(ServerInterface.class);
        initViews();
    }

    private void initViews() {
        green = findViewById(R.id.button4);
        colors.put(R.id.button4, 4);
        yellow = findViewById(R.id.button6);
        colors.put(R.id.button6, 6);
        blue = findViewById(R.id.button7);
        colors.put(R.id.button7, 7);
        orange = findViewById(R.id.button8);
        colors.put(R.id.button8, 8);
        aqua = findViewById(R.id.button9);
        colors.put(R.id.button9, 9);
        red = findViewById(R.id.button10);
        colors.put(R.id.button10, 10);
        violet = findViewById(R.id.button11);
        colors.put(R.id.button11, 11);
        pink = findViewById(R.id.button12);
        colors.put(R.id.button12, 12);
        random = findViewById(R.id.modifyBtn);

        green.setOnClickListener(this);
        yellow.setOnClickListener(this);
        blue.setOnClickListener(this);
        orange.setOnClickListener(this);
        aqua.setOnClickListener(this);
        red.setOnClickListener(this);
        violet.setOnClickListener(this);
        pink.setOnClickListener(this);
        random.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.modifyBtn:
                sendData(0, true);
                break;
                default:
                    sendData(colors.get(view.getId()), view.isSelected());
        }
    }

    private void sendData(int number, boolean isSelected) {
        int state = isSelected ? 1 : 0;
        Call<JsonObject> call;
        if (number == 0) {
            call = serverInterface.changeRandomLed(HomePage.getUserToken());
        } else {
            call = serverInterface.setLedColor(number, state, HomePage.getUserToken());
        }

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
}
