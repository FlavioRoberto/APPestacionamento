package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.Base64Custom;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.Usuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.Veiculo;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CadastroVeicuoActivity extends AppCompatActivity implements IActivity {

    //inicio declaração de variáveis
    private Toolbar toolbar;
    private String uid, tipo, placa, emailDono, marca, modelo, cor;
    private EditText editTextPlaca, editTextEmailDono, editTextModelo;
    private Spinner spinner,spinnerMarcaVeiculo,spinnerCorVeicullo;
    private Button buttonCadastrarVeiculo;
    private Veiculo veiculo = new Veiculo();
    private DatabaseReference databaseReferenceUsers;
    private String emailCodificado, emailDatabase;
    private Boolean emailaValido = false;
    private ProgressDialog progressDialog;
    //Fim declaração de variáveis

    //Inicio Oncreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_veicuo);

        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("users");

        //inicializando Toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("Cadastro de veículo");
        setSupportActionBar(toolbar);


        //setando componentes
        editTextPlaca = (EditText) findViewById(R.id.placaVeiculoId);
        editTextEmailDono = (EditText) findViewById(R.id.emailDonoId);
        editTextModelo = (EditText) findViewById(R.id.modeloVeiculoId);
        spinnerMarcaVeiculo = (Spinner)findViewById(R.id.spinnerMarcaVeiculo);
        spinner = (Spinner)findViewById(R.id.spinnerTipoVeiculo);
        spinnerCorVeicullo = (Spinner)findViewById(R.id.spinnerCorVeiculoId);
        buttonCadastrarVeiculo = (Button) findViewById(R.id.button_cadastroVeiculo);
        progressDialog = new ProgressDialog(CadastroVeicuoActivity.this);

        //Spiner Adapter
        SpinnerAdapter adapter = spinner.getAdapter();
        //inicializa o spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipo = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner marca veiculo
        adapter = spinnerMarcaVeiculo.getAdapter();
        spinnerMarcaVeiculo.setAdapter(adapter);
        spinnerMarcaVeiculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               marca = parent.getItemAtPosition(position).toString();
            //   Toast.makeText(getApplicationContext(),marca,Toast.LENGTH_LONG).show();
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

        //Spinner Cor do veiculo
        adapter = spinnerCorVeicullo.getAdapter();
        spinnerCorVeicullo.setAdapter(adapter);
        spinnerCorVeicullo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cor = parent.getItemAtPosition(position).toString();
              //  Toast.makeText(getApplicationContext(),cor,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //adiciona mascara na placa
        adicionaMascara();
        buttonCadastrarVeiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserirVeiculo();
            }
        });
    } //FIM DO ONCREATE

    //Inicio metodo INserir veiculo
    public void inserirVeiculo(){
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Cadastrando Veículo...");
        progressDialog.show();

        placa = editTextPlaca.getText().toString().toUpperCase().trim();
        emailDono = editTextEmailDono.getText().toString().toLowerCase().trim();
        modelo = editTextModelo.getText().toString().toUpperCase().trim();
        emailCodificado = Base64Custom.codificarBase64(emailDono);
      try {
          Query query = databaseReferenceUsers;
          query.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(DataSnapshot dataSnapshot) {
                  for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                      emailDatabase = postSnapshot.child("uid").getValue(String.class);
                      try{
                          if(emailDatabase.equals(emailCodificado)){
                              if(!TextUtils.isEmpty(emailCodificado) && !TextUtils.isEmpty(cor) && !TextUtils.isEmpty(marca) &&
                                      !TextUtils.isEmpty(modelo) && !TextUtils.isEmpty(placa) && !TextUtils.isEmpty(tipo) &&
                                      !TextUtils.isEmpty(emailDono)) {
                                  veiculo.setUid(emailCodificado);
                                  veiculo.setCor(cor);
                                  veiculo.setMarca(marca);
                                  veiculo.setModelo(modelo);
                                  veiculo.setPlaca(placa);
                                  veiculo.setTipo(tipo);
                                  veiculo.setEmail(emailDono);
                                  veiculo.create();
                                  emailaValido = true;
                                  progressDialog.dismiss();
                                  Toast.makeText(getApplicationContext(), "Veículo inserido com sucesso!", Toast.LENGTH_LONG).show();
                                  finish();
                                  break;
                              }
                          }
                      }catch (Exception e){
                      }
                  }
                  if(emailaValido == false){
                      Toast.makeText(getApplicationContext(), "Email Inválido",Toast.LENGTH_LONG).show();
                      progressDialog.dismiss();
                      finish();
                      return;
                  }
              }
              @Override
              public void onCancelled(DatabaseError databaseError) {
              }
          });
      }catch (Exception e){
      }
    }//FIM METODO Inserir Veiculo

    //sobrescreve metodo da interface IActivity para ativar os icones no menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_anterior: voltar();break;
            case R.id.menu_meusdados: break;
            case R.id.menu_sair: sair();break;
            default:break;
        }
        return true;
    }

    //invoca os itens no menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //metodo para adicionar mascara na placa
    public void adicionaMascara(){
        SimpleMaskFormatter smf = new SimpleMaskFormatter("LLL-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(editTextPlaca,smf);
        editTextPlaca.addTextChangedListener(mtw);

    }

    //desloga usuario e vai pra tela de login
    public void sair(){
        Usuario usuario = new Usuario();
        usuario.desloga();
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //retorna para a página inicial
    public  void voltar(){
        Intent intent = new Intent(getApplicationContext(),AdmActivity.class);
        startActivity(intent);
        finish();
    }
}
