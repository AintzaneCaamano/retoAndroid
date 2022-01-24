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
    private EditText contraseñaRegister;
    private EditText confirmarContraseñaRegister;
    private String patron;
    private String texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister2 = findViewById(R.id.btn_register2);
        usuarioRegister = findViewById(R.id.editText_usuarioRegister);
        contraseñaRegister = findViewById(R.id.editText_contraseñaRegister);
        confirmarContraseñaRegister = findViewById(R.id.editText_ConfirmContraseña);

        btnRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contraseñaRegister != null && confirmarContraseñaRegister != null ) {
                    if (contraseñaRegister.getText().toString().equals(confirmarContraseñaRegister.getText().toString()) && isNumeric(contraseñaRegister.getText().toString())) {


                        String messageSent = "2-" + usuarioRegister.getText().toString() + "-" + contraseñaRegister.getText().toString();
                        Envio messageResponse;
                        ClientThread clientThread = new ClientThread();
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
                            btnRegister2.setText("no ha llegado");
                        } else if (messageResponse.getLogin()) {
                            Toast register = Toast.makeText(getApplicationContext(), "Se ha registrado correctamente", Toast.LENGTH_LONG);
                            register.show();
                            Intent goToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(goToLogin);
                        } else if (!messageResponse.getLogin()) {
                            btnRegister2.setText("No se ha podido registrar");
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