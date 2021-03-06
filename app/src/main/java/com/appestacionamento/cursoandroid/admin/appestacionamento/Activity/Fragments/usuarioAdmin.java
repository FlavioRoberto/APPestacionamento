package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Admin.AdmActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.CadastroUsuarioActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.ConsultaUsuarioActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.InativaUsuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Secretaria.SecretariaActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.Preferencias;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.verificaUsuarioLogado;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class usuarioAdmin extends Fragment {

    private ImageView cadastrarUsuario;
    private ImageView consultaUsuario;
    private ImageView excluirUsuario;
    private ImageView inativaUsuario;

    public usuarioAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         final View view = inflater.inflate(R.layout.fragment_usuario_admin, container, false);
        cadastrarUsuario = (ImageView) view.findViewById(R.id.cadastroId);
        consultaUsuario = (ImageView)view.findViewById(R.id.consultaId);
        inativaUsuario = (ImageView)view.findViewById(R.id.inativaId);
      //  excluirUsuario = (ImageView)view.findViewById(R.id.excluirId);



        //ao clicar no botao consulta chama tela de consulta
        consultaUsuario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConsultaUsuarioActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        //ao clicar no botao adicionar chama tela adicionar
        cadastrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CadastroUsuarioActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        //ao clicar no botao chama a tela inativa
        inativaUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InativaUsuario.class);
                startActivity(intent);
                getActivity().finish();
    }
});

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        if((verificaUsuarioLogado().equals("ADM"))){
            ((AdmActivity) getActivity()).onFragmentViewCreated(view);
        }
        if(verificaUsuarioLogado().equals("SECRETARIA"))
        {
            ((SecretariaActivity)getActivity()).onFragmentViewCreated(view);
        }
    }

    @Override
    public void onDestroy() {
        getActivity().finish();
        super.onDestroy();
    }




    public String verificaUsuarioLogado(){
        return verificaUsuarioLogado.verificaUsuarioLogado(getContext());
    }
}
