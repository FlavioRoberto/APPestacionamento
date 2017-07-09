package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenciasVaga {

    private Context context;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "preferenciavaga";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;
    private final String CHAVE_NUMERO="numero";
    private final String CHAVE_SETOR="setor";
    private final String CHAVE="chave";
    private final Boolean CHAVE_VAGAESPECIAL=false;

    public  PreferenciasVaga(Context contextoParametro){
        context = contextoParametro;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();
    }

    public void salvarVagaPreferences(String numero, String setor, String chave, Boolean vagaEspecial){
        editor.clear();
        editor.putString(CHAVE_NUMERO, numero);
        editor.putString(CHAVE_SETOR, setor);
        editor.putString(CHAVE, chave);
        editor.putBoolean(String.valueOf(CHAVE_VAGAESPECIAL), vagaEspecial);
        editor.commit();
    }

    public String recuperaNumero(Context context){
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        return preferences.getString(CHAVE_NUMERO, "");
    }

    public String recuperaSetor(Context context){
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        return preferences.getString(CHAVE_SETOR, "");
    }

    public String recuperaChave(Context context){
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        return preferences.getString(CHAVE, "");
    }

    public String recuperaVagaEspecial(Context context){
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        return preferences.getString(String.valueOf(CHAVE_VAGAESPECIAL), "");
    }


}