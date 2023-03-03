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


public class RegisterUi extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView haveAccount;
    Button register;
    ProgressBar progressBar;
    private EditText inputUsername, inputEmail, inputPassword, inputConfirmPassword;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_ui);
        mAuth = FirebaseAuth.getInstance();

        haveAccount = findViewById(R.id.textViewHaveAccount);
        inputUsername = findViewById(R.id.inputPassword);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.editTextPassword);
        inputConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        register = findViewById(R.id.buttonRegister);
        progressBar = findViewById(R.id.progressBar);

        haveAccount.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginUI.class);
            startActivity(intent);
            finish();
        });

        register.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            String username, email, password, confirmPassword;

            username = inputUsername.getText().toString();
            email = inputEmail.getText().toString();
            password = inputPassword.getText().toString();
            confirmPassword = inputConfirmPassword.getText().toString();

            if(username.isEmpty() || username.length() < 10)
            {

                Toast.makeText(RegisterUi.this, "Username is Invalid!", Toast.LENGTH_SHORT).show();

            }
            else if(!email.contains("@"))
            {

                Toast.makeText(RegisterUi.this, "Email is Invalid", Toast.LENGTH_SHORT).show();

            }

            else if (password.length() < 8)
            {

                Toast.makeText(RegisterUi.this, "Password is Invalid", Toast.LENGTH_SHORT).show();

            }

            else if (!confirmPassword.equals(password))
            {

                Toast.makeText(RegisterUi.this, "The Password does not match", Toast.LENGTH_SHORT).show();

            }
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterUi.this, "Account Successfully Created!",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginUI.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterUi.this, "Initialization of Account Failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });


        haveAccount.setOnClickListener(view -> startActivity(new Intent(RegisterUi.this, LoginUI.class)));
    }



}