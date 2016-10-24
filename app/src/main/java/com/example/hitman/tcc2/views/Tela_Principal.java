package com.example.hitman.tcc2.views;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.Days;

import com.example.hitman.tcc2.R;
import com.example.hitman.tcc2.controllers.BDAlim;
import com.example.hitman.tcc2.controllers.BDEletro;
import com.example.hitman.tcc2.models.Contato;
import com.example.hitman.tcc2.models.alimento;
import com.example.hitman.tcc2.models.eletro;
import com.example.hitman.tcc2.controllers.Database_CriarAcessar;
import com.example.hitman.tcc2.util.EnvioEmail;
import com.example.hitman.tcc2.util.EnvioEmailEmpresa;


public class Tela_Principal extends AppCompatActivity {

    private Database_CriarAcessar baseDados;
    private BDAlim baseDados2;
    private BDEletro baseDados3;
    Contato[] contato;
    alimento[] alimentos;
    eletro[] eletros;
    String to;
    String to2 = "empresafic4001@gmail.com";
    Integer faturaCliente = 0, faturaEmpresa = 0;
    String usuarioAdmin = "HEMAN";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principaltela);


        baseDados = new Database_CriarAcessar(getApplicationContext());
        contato = baseDados.recuperarDados();

        if(contato[0].getNome().equals(usuarioAdmin)){
            // torna o botao visivel

            Toast aux = Toast.makeText(this,"DENTRO DO IF HEMAN = " + contato[0].getNome() , Toast.LENGTH_LONG);
            aux.show();
            View b2 = findViewById(R.id.PrincBTN);
            b2.setVisibility(View.VISIBLE);
        }// FIM IF DO HEMAN

        baseDados2 = new BDAlim(getApplicationContext());
        alimentos = baseDados2.recuperarDados();

        baseDados3 = new BDEletro(getApplicationContext());
        eletros = baseDados3.recuperarDados();



        if(contato.length > 0){

            Date dataConver = null;
            Date dataHojeConvert = null;
            SimpleDateFormat formatar = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String dataHoje = formatar.format(new Date());
            String dataString = contato[0].getDate();
            to = contato[0].getEmail();


            try{
            dataConver = formatar.parse(dataString);
            dataHojeConvert = formatar.parse(dataHoje);
            }catch(ParseException ex){
                ex.printStackTrace();
            }

            System.out.println("-------Data de cadastro/String------ == " + dataString );
            DateTime dataInicial = new DateTime(dataConver);
            DateTime dataFinal = new DateTime(dataHojeConvert);
            int dias = Days.daysBetween(dataInicial, dataFinal).getDays();

            if(dias >= 30 ){

                System.out.println("-------Lista Fechou envio de Fatura------ \n");

                new AsyncTask<Void, Void, Integer>() {
                    @Override
                    public Integer doInBackground(Void... params) {
                        try {
                            salvarFatura(contato, alimentos, eletros);
                            faturaCliente = 1;
                        } catch (Exception e) {
                            Log.e("Fatura não foi salva", e.getMessage(), e);
                        }
                        return faturaCliente;
                    }
                }.execute(); // fim AsyncTask

                new AsyncTask<Void, Void, Integer>() {
                    @Override
                    public Integer doInBackground(Void... params) {
                        try {
                            salvarFaturaEmpresa(contato, alimentos, eletros);
                            faturaEmpresa = 1;
                        } catch (Exception e) {
                            Log.e("Fatura não foi salva", e.getMessage(), e);
                        }
                        return faturaEmpresa;
                    }
                }.execute(); // fim AsyncTask

                if(faturaCliente == 1 && faturaEmpresa == 1){

                Toast aux = Toast.makeText(this,"Usuario Cadastro = " + to, Toast.LENGTH_SHORT);
                aux.show();

                new AsyncTask<Void, Void, Integer>() {
                    @Override
                    public Integer doInBackground(Void... params) {
                        try {
                            EnvioEmail teste = new EnvioEmail();
                            teste.sendTarifa(to);
                        } catch (Exception e) {
                            Log.e("Falha-EMAIL-CLIENTE", e.getMessage(), e);
                        }
                        return 0;
                    }
                }.execute();

                new AsyncTask<Void, Void, Integer>() {
                    @Override
                    public Integer doInBackground(Void... params) {
                        try {
                            EnvioEmailEmpresa teste2 = new EnvioEmailEmpresa();
                            teste2.sendTarifa(to2);
                        } catch (Exception e) {
                            Log.e("Falha-EMAIL-EMPRESA", e.getMessage(), e);
                        }
                        return 0;
                    }
                }.execute();
                }
                else{

                    System.out.println("-------Ocorreu erro ao gerar as Faturas, email não será enviado------ \n");
                }

            }
            else{
               System.out.println("-------Continue Comprando------ \n");

            } // fim else dias >= 30
        }// fim if contato.length
    }// fim metodo onCreate



    public void onClickPrincipal(View v) {

        if(v.getId() == R.id.PrincAlimentacao){
            Intent i = new Intent(Tela_Principal.this,Alimentos.class);
            startActivity(i);
        }// fim do if R.id.PrincAlimentacao

		if (v.getId() == R.id.PrincEletroEletr){
            Intent i = new Intent(this,Eletronicos.class);
            startActivity(i);
         }// fim do if R.id.PrincEletroEletr



		 if(v.getId() == R.id.PrincBTN){

             // ************************************
             System.out.println("-------Lista Fechou envio de Fatura------ \n");

             new AsyncTask<Void, Void, Integer>() {
                 @Override
                 public Integer doInBackground(Void... params) {
                     try {
                         salvarFatura(contato, alimentos, eletros);
                         faturaCliente = 1;
                     } catch (Exception e) {
                         Log.e("Fatura não foi salva", e.getMessage(), e);
                     }
                     return faturaCliente;
                 }
             }.execute(); // fim AsyncTask

             new AsyncTask<Void, Void, Integer>() {
                 @Override
                 public Integer doInBackground(Void... params) {
                     try {
                         salvarFaturaEmpresa(contato, alimentos, eletros);
                         faturaEmpresa = 1;
                     } catch (Exception e) {
                         Log.e("Fatura não foi salva", e.getMessage(), e);
                     }
                     return faturaEmpresa;
                 }
             }.execute(); // fim AsyncTask

             if(faturaCliente == 1 && faturaEmpresa == 1){

                 Toast aux = Toast.makeText(this,"Usuario Cadastro = " + to, Toast.LENGTH_SHORT);
                 aux.show();

                 new AsyncTask<Void, Void, Integer>() {
                     @Override
                     public Integer doInBackground(Void... params) {
                         try {
                             EnvioEmail teste = new EnvioEmail();
                             teste.sendTarifa(to);
                         } catch (Exception e) {
                             Log.e("Falha-EMAIL-CLIENTE", e.getMessage(), e);
                         }
                         return 0;
                     }
                 }.execute();

                 new AsyncTask<Void, Void, Integer>() {
                     @Override
                     public Integer doInBackground(Void... params) {
                         try {
                             EnvioEmailEmpresa teste2 = new EnvioEmailEmpresa();
                             teste2.sendTarifa(to2);
                         } catch (Exception e) {
                             Log.e("Falha-EMAIL-EMPRESA", e.getMessage(), e);
                         }
                         return 0;
                     }
                 }.execute();
             }

             //*************************************


        } // fim do if R.id.PrincBTN


    }// fim onClick


    public void salvarFatura(Contato[] contato,
                             alimento[] alimentos, eletro[] eletros ) {


        String filename = "fatura.txt";
        double TotalReal = (alimentos[0].getTgeral()) + (eletros[0].getTgeral());
        StringBuffer sb = new StringBuffer();
        sb.append("Cliente: " + contato[0].getNome() +
           "\n" + "Email: " + contato[0].getEmail() + "\n" +
           "\n" + "Total em Alimentos: " + alimentos[0].getTgeral() + "\n" +
           "\n" + "Total em Eletrônicos: " + eletros[0].getTgeral() + "\n" +
           "\n" + "Total a pagar: " + TotalReal + "\n" +
           "\n" + "Agência: 01x6" + "\n" +
           "\n" + "Conta Corrente: 115xx-2 " + "\n"
        );
        String strToSave = sb.toString();
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(strToSave.getBytes());
            outputStream.close();
        } catch (Exception e) {
            Log.e("SAVE_FILE", e.getMessage());
        }
    }// fim salvarFatura


    public void salvarFaturaEmpresa(Contato[] contato,
                             alimento[] alimentos, eletro[] eletros ) {

        double valor1 = 0, valor2 = 0, valor3 = 0 ,valor4 = 0;
        int quantidade1 = 0, quantidade2 = 0,quantidade3 = 0,quantidade4 = 0;
        String prod1 = "null", prod2 = "null", prod3 =  "null", prod4 = "null";

        if(alimentos[0].getCh1() == 1 ){
            valor1 = 2.45 * alimentos[0].getQuant1();
            prod1 = "Bolacha Bono";
            quantidade1 = alimentos[0].getQuant1();;
        }
        if(alimentos[0].getCh2() == 1 ){
            valor2 = 10.99 * alimentos[0].getQuant2();
            prod2 = "Pizza Seara";
            quantidade2 = alimentos[0].getQuant2();
        }
        if(eletros[0].getCh1() == 1 ){
            valor3 = 120.80 * eletros[0].getQuant1();
            prod3 = "DVD Player";
            quantidade3 = eletros[0].getQuant1();;
        }
        if(eletros[0].getCh2() == 1 ){
            valor4 = 1990.99 * eletros[0].getQuant2();
            prod4 = "PS4";
            quantidade4 = eletros[0].getQuant2();
        }

        String filename = "faturaEmpresa.txt";
        double TotalReal = (alimentos[0].getTgeral()) + (eletros[0].getTgeral());
        StringBuffer sb = new StringBuffer();
        sb.append("Cliente: " + contato[0].getNome() +
                        "\n" + "Email: " + contato[0].getEmail() + "\n" +
                        "\n Produto= "+prod1+ "Quant= " + quantidade1 + " valorTotal= " + valor1 +
                        "\n Produto= "+prod2+ "Quant= " + quantidade2 + " valorTotal= " + valor2 +
                        "\n \n" + "Total em Alimentos: " + alimentos[0].getTgeral() + "\n" +
                        "\n Produto= "+prod3+ "Quant= " + quantidade3 + " valorTotal= " + valor3 +
                        "\n Produto= "+prod4+ "Quant= " + quantidade4 + " valorTotal= " + valor4 +
                        "\n \n" + "Total em Eletrônicos: " + eletros[0].getTgeral() + "\n" +
                        "\n \n" + "Total a pagar: " + TotalReal + "\n" +
                        "\n \n" + "Endereco de Entrega: " + contato[0].getEndereco() + "\n" +
                        "\n"
        );
        String strToSave = sb.toString();
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(strToSave.getBytes());
            outputStream.close();
        } catch (Exception e) {
            Log.e("SAVE_FILE", e.getMessage());
        }
    }// fim salvarFaturaEmpresa



}// fim class
