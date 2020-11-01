package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        firebaseAuth = FirebaseAuth.getInstance();
        //SystemClock.sleep(3000);

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


            FirebaseFirestore.getInstance().collection("USERS").document(curentUser.getUid()).update("Last Seen", FieldValue.serverTimestamp())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent ii = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(ii);

                                finish();
                            }else {
                                String error = task.getException().getMessage();
                                Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                            }
                        }
                    });



        }
    }
}