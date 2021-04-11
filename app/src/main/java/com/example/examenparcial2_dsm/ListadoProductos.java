package com.example.examenparcial2_dsm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.*;
import java.util.ArrayList;

public class ListadoProductos extends AppCompatActivity {
    ListView LVlista;
    ArrayList<Productos> arrayList;
    ArrayAdapter<Productos> arrayAdapter;

    FirebaseDatabase miBase;
    DatabaseReference DBBReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_productos);
       miBase = FirebaseDatabase.getInstance();
       DBBReference = miBase.getReference();
        LVlista = (ListView) findViewById(R.id.lvListaProductos);
        arrayList = new ArrayList<>();


        arrayAdapter = new ArrayAdapter<Productos>(ListadoProductos.this, android.R.layout.simple_list_item_1, arrayList);
        LVlista.setAdapter(arrayAdapter);

        //Evento ItemClickListener del ListView para al dar click en un item, hara una acción
        LVlista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListadoProductos.this, ProductoActivity.class);
                intent.putExtra("producto", arrayList.get(position).id);
                startActivity(intent);
              //  Toast.makeText(ListadoProductos.this, "Id:" + arrayList.get(position).id, Toast.LENGTH_SHORT).show();
            }
        });


    DBBReference.child("Medicinas").addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.hasChildren()){
            arrayList.clear();
            for (DataSnapshot dato: snapshot.getChildren()){

                Productos value = dato.getValue(Productos.class);
                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});



    }
}