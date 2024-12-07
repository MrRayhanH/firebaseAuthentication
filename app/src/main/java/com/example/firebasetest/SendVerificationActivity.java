package com.example.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SendVerificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_verification);

        Button resendCode = findViewById(R.id.btn_resend_code);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            if(user.isEmailVerified()) {Intent intent = new Intent(SendVerificationActivity.this, MainActivity.class);}
            else {
                resendCode.setOnClickListener(v -> {
                    if(user.isEmailVerified()) {Intent intent = new Intent(SendVerificationActivity.this, MainActivity.class);}
                    user.sendEmailVerification();
                });
            }
        }





    }
}