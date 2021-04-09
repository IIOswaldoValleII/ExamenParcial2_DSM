package com.example.examenparcial2_dsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText TextEmail;
    private EditText TextPassword;
    private Button BtnRegistrar;

    private String Correo = "";
    private String Contraseña = "";

    FirebaseAuth fAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth = FirebaseAuth.getInstance();
    TextEmail = (EditText) findViewById(R.id.txtEmail);
    TextPassword = (EditText) findViewById(R.id.txtPassword);
    BtnRegistrar = (Button) findViewById(R.id.btnRegistrar);

    BtnRegistrar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Correo = TextEmail.getText().toString();
            Contraseña = TextPassword.getText().toString();
            if (!Correo.isEmpty() && !Contraseña.isEmpty()){
                if (Contraseña.length() >= 6){
                    RegistrarUsuario();
                }
                else{
                    Toast.makeText(MainActivity.this, "La contraseña debe tener almenos 6 caracteres", Toast.LENGTH_SHORT).show();
                }
            }

            else{
                Toast.makeText(MainActivity.this, "Porfavor, Llene los campos vacios para completar el registro", Toast.LENGTH_SHORT).show();
            }
        }
    });

    }
    private void RegistrarUsuario(){
fAuth.createUserWithEmailAndPassword(Correo, Contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            Toast.makeText(MainActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
        }
    }
});

    }
}