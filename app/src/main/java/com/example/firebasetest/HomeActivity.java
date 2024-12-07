package com.example.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TextView username = findViewById(R.id.tv_userName);
        TextView userEmail = findViewById(R.id.tv_userEmail);
        Button signOut = findViewById(R.id.bt_SignOut);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){

            userEmail.setText(user.getEmail());
            if (user.getDisplayName() != null && !user.getDisplayName().isEmpty()) {
                username.setText(user.getDisplayName());
            } else {
                username.setText("No Display Name");
            }
        }

        signOut.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            finish();
        });

    }
}