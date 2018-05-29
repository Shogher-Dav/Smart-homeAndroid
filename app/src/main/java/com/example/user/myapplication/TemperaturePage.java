package com.example.user.myapplication;

import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TemperaturePage extends AppCompatActivity {
    private TextView startTime, endTime;
    EditText temperature, rooms;
    public static final String BASE_URL = "http://62.109.14.103:8383/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_page);
        initViews();

        getSupportActionBar().setTitle("ThermoController");
    }

    private void initViews() {
        Button getTime;

        startTime = findViewById(R.id.start_time);
        endTime =  findViewById(R.id.end_time);
        getTime = findViewById(R.id.send);
        temperature = findViewById(R.id.temperature);
        rooms = findViewById(R.id.rooms_count);
        startTime.setOnClickListener(clickListener);
        endTime.setOnClickListener(clickListener);
        getTime.setOnClickListener(clickListener);
    }

    private void sendData() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userToken = auth.getCurrentUser().getUid();

        Retrofit retrofit = ApiFactory.getInstance().getRetrofit(BASE_URL);

        ServerInterface serverInterface = retrofit.create(ServerInterface.class);
        Log.d("lalala", "token: " + userToken);
        Call<JsonObject> call = serverInterface.setTemperature(15, userToken);
        Log.d("lalala", "request: " + call.request().url());

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("lalala", "response: "  + response.message());
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
                    getTime(view);
                    break;
                case R.id.send:
                    sendData();
                    break;
            }
        }
    };

    private void sendAnotherData() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // Create URL
                URL githubEndpoint = null;
                try {
                    String url = BASE_URL + "post/climate/15";
                    githubEndpoint = new URL(url);
                    HttpURLConnection myConnection =
                            (HttpURLConnection) githubEndpoint.openConnection();
                    myConnection.setRequestMethod("POST");

                    if (myConnection.getResponseCode() == 200) {
                        Log.d("lalala", "response: ");
                        // Success
                        // Further processing here
                    } else {
                        Log.d("lalala", "failed: " + myConnection.getResponseCode() + " " + myConnection.getResponseMessage());
                        // Error handling code goes here
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getTime(final View view) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(TemperaturePage.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                ((TextView)view).setText(i + ":" + i1);
            }
        }, hour, minute,
                DateFormat.is24HourFormat(TemperaturePage.this));
        timePicker.show();
    }




    /*public static OkHttpClient createClient(int readTimeout, TimeUnit readTimeoutUnit, int connectTimeout, TimeUnit connectTimeoutUnit)
    {
        URL url = null;
        SSLSocketFactory noSSLv3Factory = null;
        try {
            url = new URL(MainActivity.BASE_URL);
            noSSLv3Factory = new NoSSLv3SocketFactory(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//        OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                .readTimeout(readTimeout, readTimeoutUnit)
//                .connectTimeout(connectTimeout, connectTimeoutUnit)
//                .sslSocketFactory(noSSLv3Factory);
//        final OkHttpClient okHttpClient = new OkHttpClient(builder.build());
//
        OkHttpClient client = null;
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2)
                    .cipherSuites(
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                    .build();

            client = new OkHttpClient.Builder()
                    .sslSocketFactory(noSSLv3Factory, trustManager)
                    .connectionSpecs(Collections.singletonList(spec))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return client;

    }*/
}

