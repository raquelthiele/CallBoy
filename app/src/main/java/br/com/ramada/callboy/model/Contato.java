package br.com.ramada.callboy.model;

import java.util.List;

/**
 * Created by Ramada on 21/05/2016.
 */
public class Contato {

    private Integer id;
    private String nome;
    private String numeroTelefone;
    private Configuracao configuracao;
    private List<Grupo> grupos;

    public Contato() {

    }

    public Contato(String nome, String numeroTelefone) {
        this.nome = nome;
        this.numeroTelefone = numeroTelefone;
    }


    public Contato(Integer id, String nome, String numeroTelefone) {
        this.id = id;
        this.nome = nome;
        this.numeroTelefone = numeroTelefone;
    }

    public Contato(Integer id, String nome, String numeroTelefone, Configuracao configuracao) {
        this.id = id;
        this.nome = nome;
        this.numeroTelefone = numeroTelefone;
        this.configuracao = configuracao;
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

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }
}
