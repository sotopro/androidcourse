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

public class ActivityLogin extends AppCompatActivity {

    private TextInputEditText usernameText;
    private TextInputEditText passwordText;
    private TextView registerTv;
    private Button loginButton;
    private ProgressBar loadingBl;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameText = findViewById(R.id.tiet_login_username);
        passwordText = findViewById(R.id.tiet_login_password);
        loginButton = findViewById(R.id.login_button);
        loadingBl = findViewById(R.id.progress_bar_loading);
        registerTv = findViewById(R.id.link_register_text);
        mAuth = FirebaseAuth.getInstance();

        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityLogin.this, ActivityRegistration.class);
                startActivity(i);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingBl.setVisibility(View.VISIBLE);
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();
                if(TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
                    Toast.makeText(ActivityLogin.this, "Please enter your credentials", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(ActivityLogin.this, "Login Successfully", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(ActivityLogin.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                loadingBl.setVisibility(View.GONE);
                                Toast.makeText(ActivityLogin.this, "Login Failure", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }
}