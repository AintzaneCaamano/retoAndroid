package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.euskalmet.cliente.ClientThread;

public class LoginActivity extends AppCompatActivity {


    private Button btnLogin;
    private Button btnRegister;
    private EditText textField;
    private EditText edtxtUser;
    private EditText edTxtPass;
    private Button button;
    private static final String SEPARADOR = "/////";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtxtUser  = findViewById(R.id.editText_usuario);
        edTxtPass = findViewById(R.id.editText_contraseña);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNumeric(edTxtPass.getText().toString()) == false) {

                    Toast contraseñaInt = Toast.makeText(getApplicationContext(), "la contraseña debe ser numérica", Toast.LENGTH_LONG);
                    contraseñaInt.show();

                } else {

                    // get the text message on the user and password
                    String messageSent = "1"+SEPARADOR + edtxtUser.getText().toString() + SEPARADOR + edTxtPass.getText().toString();
                    Envio messageResponse;
                    ClientThread clientThread = new ClientThread();
                    clientThread.setOption(1);
                    clientThread.setMessageSent(messageSent);

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