package com.example.examenparcial2_dsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CarritoActivity extends AppCompatActivity {

    private ListView listacarrito;
    private TextView total;
    private FirebaseAuth fAuth;
    ArrayList<Productos> arrayListCarrito;
    ArrayAdapter<Productos> arrayAdapterCarrito;
    FirebaseDatabase miBase;
    DatabaseReference DBBReference;

    double PrecioTotal = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        miBase = FirebaseDatabase.getInstance();
        DBBReference = miBase.getReference();
        fAuth = FirebaseAuth.getInstance();
        String id = fAuth.getCurrentUser().getUid();



        listacarrito = (ListView) findViewById(R.id.lvCarrito);
        total = (TextView) findViewById(R.id.txtTotal);
        setTitle("Lista de productos del carrito");

        arrayListCarrito = new ArrayList<>();
        arrayAdapterCarrito = new ArrayAdapter<Productos>(CarritoActivity.this, android.R.layout.simple_list_item_1, arrayListCarrito);
        listacarrito.setAdapter(arrayAdapterCarrito);

        listacarrito.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long i) {
                DBBReference.child("Usuarios").child(id).child("carrito").child(arrayListCarrito.get(position).id).removeValue();
                return true;
            }
        });
        DBBReference.child("Usuarios").child(id).child("carrito").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.hasChildren()){
                arrayListCarrito.clear();
                for (DataSnapshot token: snapshot.getChildren()){
                    String id = token.getKey();
                    DBBReference.child("Medicinas").child(id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                       Productos medicina = snapshot.getValue(Productos.class);
                       arrayListCarrito.add(medicina);
                       arrayAdapterCarrito.notifyDataSetChanged();
                            CalcularPrecio();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }


            else{
                total.setText("Total de compra: $0; No hay productos");
                arrayAdapterCarrito.notifyDataSetChanged();
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
        private void CalcularPrecio(){
        for (Productos medicinas: arrayListCarrito){
            PrecioTotal = PrecioTotal + medicinas.precio;

            total.setText("Total de compra: $"+PrecioTotal);
        }
        }
}