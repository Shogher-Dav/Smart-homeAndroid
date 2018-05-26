package com.example.user.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class TemperaturePage extends AppCompatActivity {
   static EditText startTime, endTime;
   Button getTime;
   static boolean c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_page);
        startTime = (EditText) findViewById(R.id.editText);
        endTime=(EditText)findViewById(R.id.editText2);
        getTime=(Button)findViewById(R.id.button);
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonTimePickerDialog(v);
                c=true;
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonTimePickerDialog(v);
                c=false;
            }
        });
        getTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String start = startTime.getText().toString();
                String end = endTime.getText().toString();
                Toast toast = Toast.makeText(getApplicationContext(),start+" "+ end,Toast.LENGTH_LONG);
                toast.show();

            }
        });


    }


    public void showTruitonTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            if(c) {
                startTime.setText(startTime.getText() + "" + hourOfDay + ":" + minute);
            }
            else {
                endTime.setText(endTime.getText() + "" + hourOfDay + ":" + minute);
            }
        }


    }
    }

