package com.example.examenparcial2_dsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProductoActivity extends AppCompatActivity {

    private TextView txtNombreProducto;
    private TextView txtDescripcion;
    private TextView txtPrecio;
    private TextView txtnuevacantidad;
    private EditText txtCantidad;

    Productos productoActual;
    private Button Mas;
    private Button Menos;
    private Button AgregarCarrito;
    private String id = "";
    String nuevacantidad = "";

    private String nombre = "";
    private String precio = "";
    private String cantidadtomada = "";
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
        txtnuevacantidad = (TextView) findViewById(R.id.txtcantidad);

        AgregarCarrito = (Button) findViewById(R.id.btnAgregarCarrito);




        String idProducto = getIntent().getExtras().getString("producto");

        DBBReference.child("Medicinas").child(idProducto).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productoActual = snapshot.getValue(Productos.class);
               txtNombreProducto.setText(productoActual.nombre);
                txtDescripcion.setText(productoActual.descripcion);
                txtPrecio.setText(String.valueOf(productoActual.precio));
               txtnuevacantidad.setText(String.valueOf(productoActual.stock));

               nombre = txtNombreProducto.getText().toString();
               precio = txtPrecio.getText().toString();
               cantidadtomada = txtCantidad.getText().toString();



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

              String Cantidadresta = txtCantidad.getText().toString();
              Integer Cantidadresta2 = Integer.parseInt(Cantidadresta);

                nuevacantidad = txtnuevacantidad.getText().toString();

                double precio2 = Double.parseDouble(precio);

                if (Integer.parseInt(nuevacantidad) > Integer.parseInt(Cantidadresta)){
                    int totalcantidad = (Integer.parseInt(nuevacantidad) - Integer.parseInt(Cantidadresta));
                    DBBReference.child("Medicinas").child(idProducto).child("stock").setValue(totalcantidad);

                    Map<String, Object> map = new HashMap<>();

                    map.put( "id", idProducto);
                    map.put( "nombre", nombre);
                    map.put( "precio", precio2);
                    map.put ( "cantidadpedida", Cantidadresta2);



                    DBBReference.child("Usuarios").child(id).child("carrito").child(productoActual.id).setValue(map);
                  //  DBBReference.child("Usuarios").child(id).child("carrito").child(productoActual.id).child("precio").setValue(txtPrecio);
                  //  DBBReference.child("Usuarios").child(id).child("carrito").child(productoActual.id).child("cantidadpedida").setValue(Cantidadresta);

                    Toast.makeText(ProductoActivity.this, "Producto añadido al carrito correctamente", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(ProductoActivity.this, "Error, la cantidad que desea es mayor a la cantidad disponible", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });


    }



}