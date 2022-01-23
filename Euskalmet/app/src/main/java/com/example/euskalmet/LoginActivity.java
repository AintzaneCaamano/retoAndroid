package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

                if (isNumeric(contraseña.getText().toString()) == false) {

                    Toast contraseñaInt = Toast.makeText(getApplicationContext(), "la contraseña debe ser numérica", Toast.LENGTH_LONG);
                    contraseñaInt.show();

                } else {

                    // get the text message on the user and password
                    String messageSent = "1-" + usuario.getText().toString() + "-" + contraseña.getText().toString();
                    Envio messageResponse;
                    ClientThread clientThread = new ClientThread();
                    clientThread.setMessageSent(messageSent);
                    clientThread.setOpcion(1);
                    Thread thread = new Thread(clientThread);
                    try {
                        thread.start();
                        thread.join();
                    } catch (InterruptedException e) {

                    }

                    // The Answer
                    messageResponse = clientThread.getMessageResponse();

                    if (null == messageResponse) {
                        btnLogin.setText("no ha llegado");
                    } else if (messageResponse.getLogin()) {
                        Toast login = Toast.makeText(getApplicationContext(), "Se ha logueado correctamente", Toast.LENGTH_LONG);
                        login.show();
                        Intent goToOperaciones = new Intent(LoginActivity.this, ActivityOperaciones.class);
                        startActivity(goToOperaciones);
                    } else if (!messageResponse.getLogin()) {
                        Toast credencialesIncorrectas = Toast.makeText(getApplicationContext(), "credenciales incorrectas", Toast.LENGTH_LONG);
                        credencialesIncorrectas.show();
                    }
                }
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

    public boolean isNumeric(String numero){
        boolean error = false;

        try {
            Integer.parseInt(numero);
            error = true;
        }catch(Exception e){
            error = false;
        }
        return error;
    }
}