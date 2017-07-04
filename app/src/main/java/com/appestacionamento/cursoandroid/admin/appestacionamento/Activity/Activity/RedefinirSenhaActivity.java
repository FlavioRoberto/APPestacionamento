package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.RedefinirSenha;

public class RedefinirSenhaActivity extends AppCompatActivity implements IActivity {

    private EditText editTextEmail;
    private Button buttonEnviar;
    private String email;
    private Toolbar toolbar;
    private boolean flag = false;
    private RedefinirSenha redefinirSenha = new RedefinirSenha(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);


        //Toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbarId) ;
        toolbar.setTitle("Redefinição de senha");
        setSupportActionBar(toolbar);

        //fim toolbar

        editTextEmail = (EditText) findViewById(R.id.editTextEmailRedefinirSenha);
        buttonEnviar = (Button) findViewById(R.id.buttonRedefinirSenha);
        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == false) {
                   // flag = true;
                    enviaEmail();



                }
            }
        });

    }

    private void enviaEmail(){
        try {
            email = editTextEmail.getText().toString().toLowerCase().trim();
            redefinirSenha.redefineSenha(email);
            flag = true;
        }catch (Exception e){
            flag = false;
        }
        }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.menu_anterior: voltar();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_redefinicao,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void adicionaMascara() {

    }

    @Override
    public void sair() {

    }

    @Override
    public void voltar() {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        finish();
        startActivity(intent);
    }
}
