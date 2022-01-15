package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.PrintWriter;
import java.net.Socket;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnRegister;

    private EditText textField;
    private Button button;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        textField = (EditText) findViewById(R.id.editText_serverConection);
        button = (Button) findViewById(R.id.btn_serverSend);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToOperaciones = new Intent(LoginActivity.this, ActivityOperaciones.class);
                startActivity(goToOperaciones);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(goToRegister);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // get the text message on the text field
                message = textField.getText().toString();

                // start the Thread to connect to server
                new Thread(new ClientThread(message)).start();

            }
        });
    }
}