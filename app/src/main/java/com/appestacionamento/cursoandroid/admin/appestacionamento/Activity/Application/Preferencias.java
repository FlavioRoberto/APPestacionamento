package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {

    private Context context;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "preferenciawhatsapp";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;
    private final String CHAVE_NOME="nome";
    private final String CHAVE_TELEFONE="telefone";
    private final String CHAVE_TIPO="tipo";

    public  Preferencias(Context contextoParametro){
        context = contextoParametro;
        preferences = context.getSharedPreferences(NOME_ARQUIVO,MODE);
        editor = preferences.edit();
    }

<<<<<<< HEAD
    public void salvarusuarioPreferences(String email, String senha, String token){
        editor.clear();
        editor.putString(CHAVE_NOME,email);
        editor.putString(CHAVE_TELEFONE,senha);
        editor.putString(CHAVE_TOKEN,token);
=======
    public void salvarusuarioPreferences(String nome, String telefone, String token){
        editor.putString(CHAVE_NOME,nome);
        editor.putString(CHAVE_TELEFONE,telefone);
        editor.putString(CHAVE_TIPO,token);
>>>>>>> 7156278f22af47a1bee892cdd7efa1716659229b
        editor.commit();

    }

    //Hasgmap retorna uma `lista` com dois indices do tipo string
    public HashMap<String,String> retornaDadosusuario(){

        HashMap<String,String> dados = new HashMap<>();
        dados.put(CHAVE_NOME,preferences.getString(CHAVE_NOME,null));
        dados.put(CHAVE_TIPO,preferences.getString(CHAVE_TIPO,null));
        dados.put(CHAVE_TELEFONE,preferences.getString(CHAVE_TELEFONE,null));

        return dados;

    }
}