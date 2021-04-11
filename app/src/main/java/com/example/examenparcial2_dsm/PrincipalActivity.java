package com.example.examenparcial2_dsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import org.w3c.dom.Text;


public class PrincipalActivity extends AppCompatActivity {

    private Button btnCerrar_Sesión;
    private FirebaseAuth fAuth;
    private DatabaseReference DBBReferencia;
    private TextView nombre_usuario;
    private Button btnCarrito;
    private Button btnMedicinas;
    private Button btnRegistro;

    private String nombre = "";
    private String correo = "";
    private String contraseña = "";
    private String Saludo = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    fAuth = FirebaseAuth.getInstance();
    DBBReferencia = FirebaseDatabase.getInstance().getReference();
    btnCerrar_Sesión = (Button) findViewById(R.id.btnCerrarSesión);
    nombre_usuario = (TextView) findViewById(R.id.textviewNombre);
    btnCarrito = (Button) findViewById(R.id.btnCarrito);
    btnMedicinas = (Button) findViewById(R.id.btnListaMedicinas);

    btnCerrar_Sesión.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fAuth.signOut();
            startActivity(new Intent(PrincipalActivity.this, InicioSesion.class));
            finish();
        }
    });

    btnMedicinas.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent( PrincipalActivity.this, ListadoProductos.class);
            startActivity(intent);
        }
    });

    btnCarrito.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent( PrincipalActivity.this, CarritoActivity.class);
            startActivity(intent);
        }
    });

    }

    private void InformacionUsuario(){
        String id = fAuth.getCurrentUser().getUid();
DBBReferencia.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.exists()){
          nombre = snapshot.child("Nombre").getValue().toString();
          correo = snapshot.child("Email").getValue().toString();
          contraseña = snapshot.child("Password").getValue().toString();
          Saludo = "Bienvenido: " + nombre;

        nombre_usuario.setText(Saludo);

        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
    }

    @Override
    protected void onStart() {
        super.onStart();
        InformacionUsuario();
    }
}