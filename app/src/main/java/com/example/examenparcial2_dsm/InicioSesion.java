package com.example.examenparcial2_dsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class InicioSesion extends AppCompatActivity {

    private EditText TextEmail;
    private EditText TextPassword;
    private Button BtnInicioSesion;
    private Button BtnRegistro;

    private String Correo = "";
    private String Contraseña = "";

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        fAuth = FirebaseAuth.getInstance();
        TextEmail = (EditText) findViewById(R.id.editTextEmail);
        TextPassword = (EditText) findViewById(R.id.editTextPassword);
        BtnInicioSesion = (Button) findViewById(R.id.btnIniciarSesion);
        BtnRegistro= (Button) findViewById(R.id.btnRegistro);

        BtnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Correo = TextEmail.getText().toString();
                Contraseña = TextPassword.getText().toString();

                if (!Correo.isEmpty() && !Contraseña.isEmpty()){
                  InicioLogin();
                }else{
                    Toast.makeText(InicioSesion.this, "Porfavor, complete los campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });

        BtnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( InicioSesion.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void InicioLogin(){
fAuth.signInWithEmailAndPassword(Correo, Contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            startActivity(new Intent(InicioSesion.this, PrincipalActivity.class));
        finish();
        }
        else{
            Toast.makeText(InicioSesion.this, "No se pudo iniciar sesión", Toast.LENGTH_SHORT).show();
        }
    }
});
    }
 /*   @Override
    protected void onStart() {
        super.onStart();
        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(InicioSesion.this, PrincipalActivity.class));
            finish();
        }
    }
*/



        }