package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.euskalmet.cliente.ClientThread;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister2;
    private EditText usuarioRegister;
    private EditText contraseniaRegister;
    private EditText confirmarContraseniaRegister;
    private String patron;
    private String texto;
    private static final String SEPARADOR = "/////";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister2 = findViewById(R.id.btn_register2);
        usuarioRegister = findViewById(R.id.editText_usuarioRegister);
        contraseniaRegister = findViewById(R.id.editText_contraseñaRegister);
        confirmarContraseniaRegister = findViewById(R.id.editText_ConfirmContraseña);

        btnRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contraseniaRegister != null && confirmarContraseniaRegister != null ) {
                    if (contraseniaRegister.getText().toString().equals(confirmarContraseniaRegister.getText().toString()) && isNumeric(contraseniaRegister.getText().toString())) {


                        String messageSent = "2"+ SEPARADOR + usuarioRegister.getText().toString() + SEPARADOR + contraseniaRegister.getText().toString();

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
                        Envio messageResponse = clientThread.getMessageResponse();


                        if (null == messageResponse) {
                            Toast toast = Toast.makeText(getApplicationContext(), "no ha llegado", Toast.LENGTH_LONG);
                            toast.show();
                        } else if (messageResponse.getLogin()) {
                            Toast register = Toast.makeText(getApplicationContext(), "Se ha registrado correctamente", Toast.LENGTH_LONG);
                            register.show();
                            Intent goToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(goToLogin);
                        } else if (!messageResponse.getLogin()) {
                            Toast toast = Toast.makeText(getApplicationContext(), "No se ha podido registrar o el ususario ya existe", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                    else {
                        Toast mismacontra = Toast.makeText(getApplicationContext(), "debes introducir la misma contraseña y debe ser numérica", Toast.LENGTH_LONG);
                        mismacontra.show();
                    }
                }
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