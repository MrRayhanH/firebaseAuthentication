package com.example.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText email = findViewById(R.id.et_login_email);
        EditText password = findViewById(R.id.et_login_password);
        TextView signup = findViewById(R.id.tv_login_signUpText);
        TextView forgotPass = findViewById(R.id.tv_login_forgotPassword);
        Button login = findViewById(R.id.btn_login);

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(v -> {
            String Email = email.getText().toString().trim();
            String Password = password.getText().toString().trim();

            if (Email.isEmpty()) {
                Toast.makeText(MainActivity.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
            } else if (Password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
            } else if (Password.length() < 6) {
                Toast.makeText(MainActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.signInWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if(user != null && user.isEmailVerified()){
                                        updateUI(user);
                                    }
                                    else{
                                        Intent intent = new Intent(MainActivity.this, SendVerificationActivity.class);
                                        Toast.makeText(MainActivity.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    // Sign in failed
                                    Toast.makeText(MainActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
            }
        });

        signup.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        forgotPass.setOnClickListener(v -> {
           Intent intent = new Intent(MainActivity.this, ForgetPassActivity.class);
           startActivity(intent);

        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class); // Change to your desired activity
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(MainActivity.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
