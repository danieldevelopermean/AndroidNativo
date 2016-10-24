package com.example.hitman.tcc2.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hitman.tcc2.R;
import com.example.hitman.tcc2.controllers.BDEletro;
import com.example.hitman.tcc2.models.eletro;

public class Eletronicos extends AppCompatActivity implements
        View.OnClickListener{

// variaveis
private EditText txtQBolac,txtQPizza;
private CheckBox bbono, pizza ;
private EditText txtTotal;

// botoes
private Button btnTeste1, btnTeste2;

// base de dados
private BDEletro baseDados;
        eletro[] alimento;

// uteis
int quant1,ch1,quant2,ch2;
double t1, t2, total;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eletronicos);

        //variaveis
        txtQBolac = (EditText) findViewById(R.id.ELETRO_QUANT1);
        bbono = (CheckBox)findViewById(R.id.ELETRO_CHECK1);
        txtQPizza = (EditText) findViewById(R.id.ELETRO_QUANT2);
        pizza = (CheckBox)findViewById(R.id.ELETRO_CHECK2);
        txtTotal = (EditText) findViewById(R.id.ALIM_Total);

        bbono.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {ch1 = 1;
                } else {ch1 = 0;}
        }}); // fim bbono.setOnCheckedChangeListener

        pizza.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {ch2 = 1;
                } else {ch2 = 0;}
        }}); // fim pizza.setOnCheckedChangeListener

        // botoes
        btnTeste1 = (Button) findViewById(R.id.btnSalvar);
        btnTeste2 = (Button) findViewById(R.id.btnCarregar);
        btnTeste1.setOnClickListener((View.OnClickListener) this);
        btnTeste2.setOnClickListener((View.OnClickListener) this);
        //base de dados
        baseDados = new BDEletro(getApplicationContext());
}// fim onCreate

@Override
public void onClick(View v){

        switch (v.getId()){

        case R.id.btnSalvar:
        quant1 = Integer.parseInt(txtQBolac.getText().toString());
        quant2 = Integer.parseInt(txtQPizza.getText().toString());

                if(ch1 == 1){
                        t1 = quant1 * 120.80;
                        total += quant1 * 120.80; }
                if(ch2 == 1){
                        t2 = quant2 * 1990.99;
                        total += quant2 * 1990.99;}

        baseDados.Salvar(quant1, ch1, quant2, ch2, t1, t2, total );
        Intent i1 = new Intent(this, Tela_Principal.class);
        startActivity(i1);

        String totalString = Double.toString(total);
        txtTotal.setText(totalString);
        break;



        case R.id.btnCarregar:
        alimento = baseDados.recuperarDados();

        if(alimento.length > 0){
        txtQBolac.setText(String.valueOf(alimento[0].getQuant1()));
        txtQPizza.setText(String.valueOf(alimento[0].getQuant2()));

        if(alimento[0].getCh1() == 1){bbono.setChecked(true);
        }else{bbono.setChecked(false);}

        if(alimento[0].getCh2() == 1){pizza.setChecked(true);
        }else{pizza.setChecked(false);}

        txtTotal.setText(String.valueOf(alimento[0].getTgeral()));

        }
        else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Nada ainda foi cadastrado", Toast.LENGTH_LONG);
                toast.show();
        }// fim else - // fim if alimento.length > 0

        break;

        }// fim switch
        }// fim onClick

}// fim class
