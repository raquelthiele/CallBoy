package br.com.ramada.callboy.model;

/**
 * Created by PRUP-16 on 21/05/2016.
 */
public class Configuracao {

    private boolean bloqueioChamada;
    private boolean anuncioChamada;
    private boolean bloqueioSms;
    private boolean anuncioSms;

    public Configuracao() {
    }

    public Configuracao(boolean bloqueioChamada, boolean bloqueioSms, boolean anuncioChamada, boolean anuncioSms) {
        this.bloqueioChamada = bloqueioChamada;
        this.bloqueioSms = bloqueioSms;
        this.anuncioChamada = anuncioChamada;
        this.anuncioSms = anuncioSms;
    }
}
