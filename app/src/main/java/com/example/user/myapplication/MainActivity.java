package com.example.user.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends BaseActivity {
    EditText e, p;
    FirebaseDatabase database;
    DatabaseReference myRef;
    UserInformation info;
    private FirebaseAuth mAuth;
    SharedPreferences.Editor editor;
    String check1, check2;
    Button login;
    protected String prefEmail = "", prefPassword = "";
    private static final String TAG = "EmailPassword";
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Smart Home");

        if (getIntent() != null && getIntent().getBooleanExtra("check", false)) {
            prefEmail = "";
            prefPassword = "";
        }
        e = (EditText) findViewById(R.id.email);
        p = (EditText) findViewById(R.id.password);

        database = FirebaseDatabase.getInstance();
        SharedPreferences settings = getSharedPreferences("sh1", 0);
        editor = settings.edit();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                updateUI(user);
            }
        };
    }
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }
        showProgressDialog();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, "Incorrect email or password",
                                    Toast.LENGTH_SHORT).show();
                        }else if(task.isSuccessful()){
                            prefEmail = e.getText().toString();
                            prefPassword = p.getText().toString();
                            editor.putString("preEmail", prefEmail);
                            editor.putString("prePassword", prefPassword);
                            editor.putInt("value",5);
                            // Commit the edits!
                            editor.commit();
                            Intent i = new Intent(MainActivity.this,HomePage.class);
                            startActivity(i);
                            finish();
                        }
                        hideProgressDialog();
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = e.getText().toString();
        if (TextUtils.isEmpty(email)) {
            e.setError("Required.");
            valid = false;
        } else {
            e.setError(null);
        }

        String password = p.getText().toString();
        if (TextUtils.isEmpty(password)) {
            p.setError("Required.");
            valid = false;
        } else {
            p.setError(null);
        }

        return valid;
    }

    @SuppressLint("StringFormatInvalid")
    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            Toast.makeText(MainActivity.this,"LOGIN",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this,"Sign out",Toast.LENGTH_LONG).show();
        }
    }

    public void onClick(View view) {
       // hashvel.setBackground(getResources().getDrawable(R.drawable.v));
        signIn(e.getText().toString(),p.getText().toString());
    }

    public void help(View view) {
      //  Intent intent = new Intent(MainActivity.this,dialog.class);
       // startActivity(intent);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("preEmail", prefEmail);
        outState.putString("prePassword", prefPassword);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        prefEmail = savedInstanceState.getString("preEmail");
        prefPassword = savedInstanceState.getString("prePassword");
    }
}
