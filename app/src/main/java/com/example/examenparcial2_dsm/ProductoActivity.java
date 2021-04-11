package com.example.examenparcial2_dsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductoActivity extends AppCompatActivity {

    private TextView txtNombreProducto;
    private TextView txtDescripcion;
    private TextView txtPrecio;
    private EditText txtCantidad;

    Productos productoActual;
    private Button Mas;
    private Button Menos;
    private Button AgregarCarrito;
    private String id = "";

    FirebaseDatabase miBase;
    DatabaseReference DBBReference;
    private FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        miBase = FirebaseDatabase.getInstance();
        DBBReference = miBase.getReference();
        fAuth = FirebaseAuth.getInstance();
        String id = fAuth.getCurrentUser().getUid();
        txtNombreProducto = (TextView) findViewById(R.id.txtNombreProducto);
        txtDescripcion = (TextView) findViewById(R.id.txtDescripcion);
        txtPrecio = (TextView) findViewById(R.id.txtPrecio);
        txtCantidad = (EditText) findViewById(R.id.editCantidad);
        Mas = (Button) findViewById(R.id.btnMas);
        Menos = (Button) findViewById(R.id.btnMenos);
        AgregarCarrito = (Button) findViewById(R.id.btnAgregarCarrito);




        String idProducto = getIntent().getExtras().getString("producto");

        DBBReference.child("Medicinas").child(idProducto).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productoActual = snapshot.getValue(Productos.class);
               txtNombreProducto.setText(productoActual.nombre);
                txtDescripcion.setText(productoActual.descripcion);
                txtPrecio.setText("$"+productoActual.precio);
                setTitle("Medicamento: "+productoActual.nombre);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        AgregarCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBBReference.child("Usuarios").child(id).child("carrito").child(productoActual.id).setValue(true);
                finish();
            }
        });

    }

}