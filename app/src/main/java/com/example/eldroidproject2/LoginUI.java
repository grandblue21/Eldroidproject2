package com.example.eldroidproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginUI extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView signupNow;
    EditText inputEmail, inputPassword;
    Button login;
    ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_ui);

        progressBar = findViewById(R.id.progressBar);
        signupNow = findViewById(R.id.textViewSignup);
        inputEmail = findViewById(R.id.editTextEmail);
        inputPassword = findViewById(R.id.editTextPassword);

        signupNow.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegisterUi.class);
            startActivity(intent);
            finish();
        });

        login = findViewById(R.id.buttonLogin);
        login.setOnClickListener(view -> {
            String email, password;

            email = inputEmail.getText().toString();
            password = inputPassword.getText().toString();

            if (!email.contains("@")) {

                Toast.makeText(LoginUI.this, "Email is Invalid", Toast.LENGTH_SHORT).show();

            } else if (password.length() < 8) {

                Toast.makeText(LoginUI.this, "Password is Invalid", Toast.LENGTH_SHORT).show();

            }
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(LoginUI.this, "Login Accepted!",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(LoginUI.this, "Initialization of Account Failed.",
                            Toast.LENGTH_SHORT).show();
                }
            });

        });
    }

}