package com.example.despertador;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.despertador.dal.RelogDAL;
import com.example.despertador.dto.Relog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AlarmsActivity extends AppCompatActivity {

    private RelogDAL relogDAL;
    private ListView list;
    private ArrayAdapter<Relog> adapter;
    private ArrayList<Relog> relogList;
    private FloatingActionButton btnAgregar;
    private int codPosicion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);
        this.relogDAL = new RelogDAL(getApplicationContext());

        this.list = findViewById(R.id.list);
        this.relogDAL = new RelogDAL(getApplicationContext());
        this.relogList = relogDAL.seleccionar();
        this.btnAgregar = (FloatingActionButton) findViewById(R.id.btnAgregar);

        this.adapter = new ArrayAdapter<Relog>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                this.relogList
        );

        list.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirmación");
        builder.setMessage("¿Desea Quitar la alarma?");
        builder.setPositiveButton("Quitar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int id = ((Relog) relogList.get(codPosicion)).getId();
                boolean r = relogDAL.eliminar(id);
                if(r){
                    Toast.makeText(getApplicationContext(), "Se ha eliminado correctamente", Toast.LENGTH_SHORT).show();
                    actualizarLista();
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha podido eliminar", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        final AlertDialog dialog = builder.create();

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                codPosicion = posicion;
                dialog.show();
                return true;
            }
        });

        // Tap simple
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                codPosicion = posicion;
                abrirActivity();
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivity();
            }
        });


    }

    private void abrirActivity() {
        Intent intento = new Intent(AlarmsActivity.this, MainActivity.class);

        startActivityForResult(intento, 100);
    }

    private void actualizarLista() {
        adapter.clear();
        adapter.addAll(relogDAL.seleccionar());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume(){
        super.onResume();
        actualizarLista();
    }
}
