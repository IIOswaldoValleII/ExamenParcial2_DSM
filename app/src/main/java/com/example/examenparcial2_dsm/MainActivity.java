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

import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText TextNombre;
    private EditText TextEmail;
    private EditText TextPassword;
    private Button BtnRegistrar;

    private String Nombre = "";
    private String Correo = "";
    private String Contraseña = "";

    FirebaseAuth fAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference DBBreferencia;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth = FirebaseAuth.getInstance();
        DBBreferencia = FirebaseDatabase.getInstance().getReference();
        TextNombre = (EditText) findViewById(R.id.txTNombre);
    TextEmail = (EditText) findViewById(R.id.txtEmail);
    TextPassword = (EditText) findViewById(R.id.txtPassword);
    BtnRegistrar = (Button) findViewById(R.id.btnRegistrar);

    BtnRegistrar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Correo = TextEmail.getText().toString();
            Contraseña = TextPassword.getText().toString();
            Nombre = TextNombre.getText().toString();

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
            Map<String, Object> map = new HashMap<>();
            map.put( "Nombre", Nombre);
            map.put( "Email", Correo);
            map.put ( "Password", Contraseña);

            String id = fAuth.getCurrentUser().getUid();

            DBBreferencia.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task2) {
                if (task2.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Error de registro", Toast.LENGTH_SHORT).show();
                }
                }
            });


        }
        else{
            Toast.makeText(MainActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
        }
    }
});

    }
}