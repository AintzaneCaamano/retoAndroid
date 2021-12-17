package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister2 = findViewById(R.id.btn_register2);

        btnRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(goToLogin);
            }
        });
    }
}