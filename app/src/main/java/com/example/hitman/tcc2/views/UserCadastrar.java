package com.example.hitman.tcc2.views;


import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hitman.tcc2.MainActivity;
import com.example.hitman.tcc2.R;
import com.example.hitman.tcc2.models.Contato;
import com.example.hitman.tcc2.controllers.Database_CriarAcessar;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Hitman on 26/12/2015.
 */
public class UserCadastrar extends Activity {

    Database_CriarAcessar bancoDados = new Database_CriarAcessar(this);

    EditText dateHoje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);



    }


    public void onClickCadastrar(View v) {
        // click no botao cadastrar
        if(v.getId() == R.id.BTNCadCadastro){

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = sdf.format(new Date());

            // recupera valor dos campos
            EditText nome = (EditText)findViewById(R.id.CadNome);
            EditText email = (EditText)findViewById(R.id.CadEmail);
            EditText senha = (EditText)findViewById(R.id.CadSenha);
            EditText confSenha = (EditText)findViewById(R.id.CadConfirma);
            dateHoje = (EditText)findViewById(R.id.dataFechamento);
            EditText endereco = (EditText)findViewById(R.id.CadEndereco);

            // converter valores recuperados para string
            String nomeSTR = nome.getText().toString();
            String emailSTR = email.getText().toString();
            String senhaSTR = senha.getText().toString();
            String confSenhaSTR = confSenha.getText().toString();
            dateHoje.setText(strDate);
            String enderecoSTR = endereco.getText().toString();

            // compara strings senha e confSenhaSTR
            if(!senhaSTR.equals(confSenhaSTR) ){
                Toast aux = Toast.makeText(this,"Senhas não coincidem", Toast.LENGTH_SHORT);
                aux.show();
            }else{
                // mapeia os campos
                Contato c = new Contato();
                c.setNome(nomeSTR);
                c.setEmail(emailSTR);
                c.setSenha(senhaSTR);
                c.setEndereco(enderecoSTR);
                // inseri os valores mapeados no banco de dados
                bancoDados.Inserir(c);
                String UsuarioOK = c.getNome();

                Toast aux = Toast.makeText(this,"Cadastro com sucesso" + "-" + UsuarioOK, Toast.LENGTH_SHORT);
                aux.show();
                // direciona usuario cadastrado para a tela de login
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
            }// fim else do if interno
        }// fim if externo
    }// fim onClick

}// fim class
