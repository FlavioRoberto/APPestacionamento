package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Adapter.vagaAdapter;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.modelVaga;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by renna on 09/07/2017.
 */

public class BuscarVagaListaActivity extends AppCompatActivity implements IActivity {

    private ListView ListadeVagas;
    private String vagaBanco;
    private boolean flag;
    private Toolbar toolbar;
    private DatabaseReference databaseReferenceVaga = FirebaseDatabase.getInstance().getReference("vaga");
    private int quant,position;
    private ArrayList<modelVaga> Vagas;
    private ValueEventListener valueEventListenerVagas;
    private  ArrayAdapter<modelVaga> adapter;
    private modelVaga vaga;
    private Query query = databaseReferenceVaga;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscarvagaLista);

        toolbar = (Toolbar) findViewById(R.id.toolbarId);
        toolbar.setTitle("Buscar Vaga");
        setSupportActionBar(toolbar);

        Vagas =new ArrayList<>();
        vaga = new modelVaga();

        ListadeVagas = (ListView) findViewById(R.id.ListviewId);



        adapter = new vagaAdapter(BuscarVagaListaActivity.this,Vagas);
        ListadeVagas.setAdapter(adapter);
       ListadeVagas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               parent.getItemAtPosition(position);
           }
       });

        pesquisaVaga();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.menu_anterior:voltar();break;
           case R.id.menu_sair:sair();break;
           case R.id.menu_sobre: sobre();break;
       }
        return super.onOptionsItemSelected(item);
    }

    public void pesquisaVaga(){
        valueEventListenerVagas = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Vagas.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    vagaBanco = postSnapshot.child("status").getValue(String.class);

                    if (vagaBanco.equals("OCUPADO")) {
                        flag = true;
                        vaga = postSnapshot.getValue(modelVaga.class);
                        Vagas.add(vaga);

                    }
                }
                adapter.notifyDataSetChanged();

                if (flag == false) {
                    Toast.makeText(getApplicationContext(), "Nenhuma vaga Encontrada!", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        query.addValueEventListener(valueEventListenerVagas);
    }

    @Override
    protected void onStop() {
        super.onStop();
        query.removeEventListener(valueEventListenerVagas);
    }

    @Override
    public void adicionaMascara() {

    }

    @Override
    public void sair() {

    }

    @Override
    public void sobre() {

    }

    @Override
    public void voltar() {

    }
}
