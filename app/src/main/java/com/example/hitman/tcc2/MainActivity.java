package com.example.hitman.tcc2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hitman.tcc2.controllers.Database_CriarAcessar;
import com.example.hitman.tcc2.models.Contato;
import com.example.hitman.tcc2.views.Tela_Principal;
import com.example.hitman.tcc2.views.UserCadastrar;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    // acesso ao banco de dados
    Database_CriarAcessar bancoDados = new Database_CriarAcessar(this);
    Contato[] contato;


    @Override // metodo construtor
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // verifica a existencia de um usuario já cadastrado
        // caso exista um usuario o link para cadastro permanece invisivel
        contato = bancoDados.recuperarDados();
        if(contato.length == 0 ) {
            View b2 = findViewById(R.id.LinkCadastre);
            b2.setVisibility(View.VISIBLE);
            System.out.println("Não existe CLIENTE cadastrado");
        }
        else{
            System.out.println("Já existe CLIENTE cadastrado");
        }


    } // fim do metodo construtor

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // meu metodo
    @Override // metodo respnsavel por "escutar" o click em algum link ou botao
    public void onClick(View v) {

        if(v.getId() == R.id.BTNLogar){


            EditText usuario = (EditText)findViewById(R.id.CTuser);
            EditText senha = (EditText)findViewById(R.id.CTsenha);
            String user = usuario.getText().toString();
            String pass = senha.getText().toString();


            // recupera ao valor da senha passado
            String senha2 = bancoDados.jaCadastrado(user);

            // compara o valor de senha recuperado no banco com o valor digitado no campo
            if(pass.equals(senha2)){
                // caso ok, segue para a proxima tela
                Intent i = new Intent(this,Tela_Principal.class);
                startActivity(i);


            }// fim if interno
            else{
                //mensagem de erro
                Toast temp = Toast.makeText(this, "Senha incorreta!", Toast.LENGTH_SHORT);
                temp.show();

            }// fim else



        }//fim if 1 externo


            //por padrao esse link é invisivel somente
            //caso nao existe um usuario cadastrado esse link se torna visivel
            if (v.getId() == R.id.LinkCadastre) {
                Intent i = new Intent(this, UserCadastrar.class);
                startActivity(i);
            }// fim if R.id.LinkCadastre






    }// fim onClick (Botao)


}// fim class
