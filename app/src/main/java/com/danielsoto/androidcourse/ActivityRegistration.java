package com.danielsoto.androidcourse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityRegistration extends AppCompatActivity {
    private TextInputEditText usernameText;
    private TextInputEditText passwordText;
    private TextInputEditText confirmPasswordText;
    private ProgressBar loadingBl;
    private TextView loginTV;
    private Button registerButton;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        usernameText = findViewById(R.id.tiet_register_username);
        passwordText = findViewById(R.id.tiet_register_password);
        confirmPasswordText = findViewById(R.id.tiet_register_confirm_password);
        registerButton = findViewById(R.id.register_button);
        loginTV = findViewById(R.id.link_login_text);
        loadingBl = findViewById(R.id.progress_bar_loading);
        mAuth = FirebaseAuth.getInstance();

        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityRegistration.this, ActivityLogin.class);
                startActivity(i);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingBl.setVisibility(View.VISIBLE);
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();
                String confirmPassword = confirmPasswordText.getText().toString();
                if(!password.equals(confirmPassword)) {
                    Toast.makeText(ActivityRegistration.this, "Please enter the same password, check both password field", Toast.LENGTH_LONG).show();
                } else if(TextUtils.isEmpty(username) && TextUtils.isEmpty(password) && TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(ActivityRegistration.this, "Please add your credentials", Toast.LENGTH_LONG).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                loadingBl.setVisibility(View.GONE);
                                Toast.makeText(ActivityRegistration.this, "User Register successfully", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(ActivityRegistration.this, ActivityLogin.class);
                                startActivity(i);
                                finish();
                            } else {
                                loadingBl.setVisibility(View.GONE);
                                Toast.makeText(ActivityRegistration.this, "Register failure, please try again", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}