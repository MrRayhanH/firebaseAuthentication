package com.example.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText username = findViewById(R.id.et_signup_username);
        EditText emailPhone = findViewById(R.id.et_SU_emailPhone);
        EditText password = findViewById(R.id.et_SU_password);
        EditText confirmPassword = findViewById(R.id.et_SU_confirmPassword);
        auth = FirebaseAuth.getInstance();
        TextView Login = findViewById(R.id.tv_SU_login);
        Button signup = findViewById(R.id.btn_SU_Signup);

        signup.setOnClickListener(v -> {
            String Username = username.getText().toString().trim();
            String EmailPhone = emailPhone.getText().toString().trim();
            String Password = password.getText().toString().trim();
            String ConfirmPassword = confirmPassword.getText().toString().trim();

            if (Username.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Username is empty", Toast.LENGTH_SHORT).show();
            } else if (EmailPhone.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Email is empty", Toast.LENGTH_SHORT).show();
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(EmailPhone).matches()) {
                Toast.makeText(SignupActivity.this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
            } else if (Password.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();
            } else if (!Password.equals(ConfirmPassword)) {
                Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                auth.createUserWithEmailAndPassword(EmailPhone, Password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.sendEmailVerification();
                                Toast.makeText(SignupActivity.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignupActivity.this, SendVerificationActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignupActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        Login.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
