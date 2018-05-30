package com.example.user.myapplication;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.gson.JsonObject;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class TemperaturePage extends AppCompatActivity {
    private TextView startTime, endTime;
    private EditText temperature, rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_page);
        initViews();
    }

    private void initViews() {
        Button getTime;

        startTime = findViewById(R.id.start_time);
        endTime = findViewById(R.id.end_time);
        getTime = findViewById(R.id.send);
        temperature = findViewById(R.id.temperature);
        rooms = findViewById(R.id.rooms_count);
        startTime.setOnClickListener(clickListener);
        endTime.setOnClickListener(clickListener);
        getTime.setOnClickListener(clickListener);
    }

    /**
     * Retrofit gradarani mijocov uxarkum enq Request serverin hamapatasxan tvyalnerov
     */
    private void sendData() {
        String userToken = HomePage.getUserToken();

        Retrofit retrofit = ApiFactory.getInstance().getRetrofit(HomePage.BASE_URL);
        ServerInterface serverInterface = retrofit.create(ServerInterface.class);

        int temp = temperature.getText().length() == 0 ? 0 : Integer.valueOf(temperature.getText().toString());
        String start = startTime.getText().toString();
        String end = endTime.getText().toString();

        Call<JsonObject> call = serverInterface.scheduleClimate(temp, start, end, userToken);

        Log.d("lalala", "request: " + call.request().url());
        Toast.makeText(this, "Sending data to server", Toast.LENGTH_SHORT).show();

        //Asynchron uxarkum enq tvyalner@
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("lalala", "response: " + response.message());
                Toast.makeText(TemperaturePage.this, "Server response: " + response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("lalala", "failed: " + t);
            }
        });
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.start_time:
                case R.id.end_time:
                    //start_time ev end_time buttonneri vra sexmeluc bacum enq jam @ntrelu Dialog
                    getTime(view);
                    break;
                case R.id.send:
                    //Ok buttoni sexmeluc uxarkum enq hamapatasxan hraman@ serverin
                    sendData();
                    break;
            }
        }
    };

    /**
     * Cuyc enq talis jam@ @ntrelu Dialog
     */
    private void getTime(final View view) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(TemperaturePage.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                ((TextView) view).setText(new StringBuilder(i + ":" + i1));
            }
        }, hour, minute,
                DateFormat.is24HourFormat(TemperaturePage.this));
        timePicker.show();
    }
}

