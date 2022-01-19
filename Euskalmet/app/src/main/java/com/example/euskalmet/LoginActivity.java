package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
    private EditText usuario;
    private EditText contraseña;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        usuario  = findViewById(R.id.editText_usuario);
        contraseña = findViewById(R.id.editText_contraseña);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the text message on the user and password
                String messageSent = "1/" + usuario.getText().toString() + "/" +  contraseña.getText().toString();
                Envio messageResponse;
                ClientThread clientThread = new ClientThread();
                clientThread.setMessageSent(messageSent);

                Thread thread = new Thread( clientThread );
                try {
                    thread.start();
                    thread.join();
                } catch (InterruptedException e) {

                }

                // The Answer
                messageResponse = clientThread.getMessageResponse();

               // messageResponse = messageResponse;
            }
        });



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(goToRegister);
            }
        });
    }


}