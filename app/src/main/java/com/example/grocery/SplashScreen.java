package com.example.grocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        firebaseAuth = FirebaseAuth.getInstance();
        SystemClock.sleep(3000);
        Intent loginIntent= new Intent(SplashScreen.this, RegisterActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser curentUser= firebaseAuth.getCurrentUser();
        if(curentUser == null){
            Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
            startActivity(i);
            finish();
        }
else {
            Intent ii = new Intent(getApplicationContext(),MainActivity.class);
           startActivity(ii);
            finish();
        }
    }
}