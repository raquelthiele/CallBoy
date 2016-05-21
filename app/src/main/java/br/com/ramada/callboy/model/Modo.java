package br.com.ramada.callboy.model;

/**
 * Created by Ramada on 21/05/2016.
 */
public class Modo {

    private String nome;
            private String brilho;
    private String volume;
    private String vibracao;

    public Modo() {
    }

    public Modo(String nome, String brilho, String vibracao, String volume) {

        this.nome = nome;
        this.brilho = brilho;
        this.vibracao = vibracao;
        this.volume = volume;
    }

    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVibracao() {
        return vibracao;
    }

    public void setVibracao(String vibracao) {
        this.vibracao = vibracao;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getBrilho() {
        return brilho;
    }

    public void setBrilho(String brilho) {
        this.brilho = brilho;
    }
}
