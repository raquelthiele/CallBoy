package br.com.ramada.callboy.model;

import java.util.List;

/**
 * Created by Ramada on 21/05/2016.
 */
public class Contato {

    private String nome;
    private String numeroTelefone;

    private List<Grupo> grupos;

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public Contato(String nome, String numeroTelefone) {
        this.nome = nome;
        this.numeroTelefone = numeroTelefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    public void adicionarGrupo(Grupo grupo){}

    public void removerGrupo(Grupo grupo){}
}
