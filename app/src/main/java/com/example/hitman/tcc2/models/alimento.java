package com.example.hitman.tcc2.models;


public class alimento {

    int id, ch1, quant1, ch2, quant2;
    double t1,t2,tgeral;

    public alimento(int id, int quant1, int ch1, int quant2, int ch2,
                    double t1, double t2, double tgeral) {
        this.id = id;
        this.quant1 = quant1;
        this.ch1 = ch1;
        this.quant2 = quant2;
        this.ch2 = ch2;
        this.t1 = 0;
        this.t2 = 0;
        this.tgeral = 0;
    }// fim construtor com parametros

    public alimento(){
        this.id = 0; // inicia a variavel global com um valor padrao
        this.quant1 = 0;
        this.ch1 = 0;
        this.quant2 = 0;
        this.ch2 = 0;
        this.t1 = 0;
        this.t2 = 0;
        this.tgeral = 0;

     } // fim construtor padrao

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCh1() {
        return ch1;
    }

    public void setCh1(int ch1) {
        this.ch1 = ch1;
    }

    public int getQuant1() {
        return quant1;
    }

    public void setQuant1(int quant1) {
        this.quant1 = quant1;
    }

    public int getQuant2() {
        return quant2;
    }

    public void setQuant2(int quant2) {
        this.quant2 = quant2;
    }

    public int getCh2() {
        return ch2;
    }

    public void setCh2(int ch2) {
        this.ch2 = ch2;
    }

    public double getT1() {
        return t1;
    }

    public void setT1(double t1) {
        this.t1 = t1;
    }

    public double getTgeral() {
        return tgeral;
    }

    public void setTgeral(double tgeral) {
        this.tgeral = tgeral;
    }

    public double getT2() {
        return t2;
    }

    public void setT2(double t2) {
        this.t2 = t2;
    }
}// fim class alimento
