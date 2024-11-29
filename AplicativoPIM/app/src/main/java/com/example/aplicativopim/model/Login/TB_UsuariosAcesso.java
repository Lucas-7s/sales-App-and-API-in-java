package com.example.aplicativopim.model.Login;

import java.util.Date;

public class TB_UsuariosAcesso {
    private Integer codigo;        // Correspondente ao campo Código
    private String nomeUsuario;    // Correspondente ao campo NomeUsuario
    private String email;          // Correspondente ao campo Email
    private String senha;          // Correspondente ao campo Senha
    private Date dataCriacao;      // Correspondente ao campo DataCriacao
    private Integer nivelAcesso;   // Correspondente ao campo NivelAcesso
    private Integer statusPerfil;  // Correspondente ao campo StatusPerfil

    // Getters e Setters para cada campo
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Integer getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(Integer nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public Integer getStatusPerfil() {
        return statusPerfil;
    }

    public void setStatusPerfil(Integer statusPerfil) {
        this.statusPerfil = statusPerfil;
    }
}