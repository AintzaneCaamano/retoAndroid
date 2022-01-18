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
    private EditText usuario;
    private EditText contraseña;
    private Button button;
    private String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        usuario  = findViewById(R.id.editText_usuario);
        contraseña = findViewById(R.id.editText_contraseña);
        //textField = (EditText) findViewById(R.id.editText_serverConection);
        //button = (Button) findViewById(R.id.btn_serverSend);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the text message on the user and password
                message = "1/" + usuario.getText().toString() + "/" +  contraseña.getText().toString();
                 ClientThread clientThread = new ClientThread(message);

                // start the Thread to connect to server
               /* Thread thread = new Thread(clientThread);
                // ClientThread  thread = new ClientThread(message);
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Envio response = clientThread.getResponse();


                if (null == response){
                    btnLogin.setText("no ha llegado");
                } else if (response.login) {
                    Intent goToOperaciones = new Intent(LoginActivity.this, ActivityOperaciones.class);
                    startActivity(goToOperaciones);
                } else {
                    btnLogin.setText("Error");
                }
*/
                new Thread(new ClientThread(message)).start();

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