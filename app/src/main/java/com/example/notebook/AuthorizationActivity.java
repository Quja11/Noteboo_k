package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class AuthorizationActivity extends AppCompatActivity {

    private MaterialButton loginBtn;
    private EditText loginView;
    private EditText passwordView;

    String login;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        loginBtn = findViewById(R.id.loginBtn);
        loginView = findViewById(R.id.loginView);
        passwordView = findViewById(R.id.passwordView);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login = loginView.getText().toString();
                password = passwordView.getText().toString();

                if (login.equals("admin") && password.equals("admin")) {
                    Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AuthorizationActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }


            }
        });





    }
}