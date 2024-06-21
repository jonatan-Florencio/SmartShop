package model.entity;

import model.entity.enums.TipoUsuario;

public class Usuario {

    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private Bloco bloco;
    private Apartamento apartamento;
    private TipoUsuario tipoUsuario;
    private String senha;

    public Usuario() {
        super();
    }

    public Usuario(int id, String nome, String cpf, String telefone, String email,
                   Bloco bloco, Apartamento apartamento, TipoUsuario tipoUsuario, String senha) {
        super();
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.bloco = bloco;
        this.apartamento = apartamento;
        this.tipoUsuario = tipoUsuario;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bloco getBloco() {
        return bloco;
    }

    public void setBloco(Bloco bloco) {
        this.bloco = bloco;
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
