package com.example.firebasetest;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassActivity extends AppCompatActivity {


    private EditText emailEditText;
    private Button resetPasswordButton;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);


        emailEditText = findViewById(R.id.et_email);
        resetPasswordButton = findViewById(R.id.btn_reset_password);
        mAuth = FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(ForgetPassActivity.this, "Enter your email address", Toast.LENGTH_SHORT).show();
            } else {
                sendPasswordResetEmail(email);
            }
        });
    }

    private void sendPasswordResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgetPassActivity.this, "Password reset email sent. Check your inbox.", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity or redirect as needed
                    } else {
                        Toast.makeText(ForgetPassActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}