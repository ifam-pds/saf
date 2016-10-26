package br.edu.ifam.saf.api.dto;

import java.util.Date;

import br.edu.ifam.saf.enums.Perfil;

public class UsuarioDTO {


    private Integer id;
    private String email;
    private String token;
    private String senha;
    private String nome;
    private String cpf;
    private Date dataNascimento;
    private String telefone;
    private String numeroHabilitacao;
    private Perfil perfil;
    private String endereco;
    private BairroDTO bairro;

    public UsuarioDTO(Integer id, String email, String token, String senha, String nome, String cpf, Date dataNascimento, String telefone, String numeroHabilitacao, Perfil perfil, String endereco, BairroDTO bairro) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.senha = senha;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.numeroHabilitacao = numeroHabilitacao;
        this.perfil = perfil;
        this.endereco = endereco;
        this.bairro = bairro;
    }

    public UsuarioDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNumeroHabilitacao() {
        return numeroHabilitacao;
    }

    public void setNumeroHabilitacao(String numeroHabilitacao) {
        this.numeroHabilitacao = numeroHabilitacao;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public BairroDTO getBairro() {
        return bairro;
    }

    public void setBairro(BairroDTO bairro) {
        this.bairro = bairro;
    }

    public static class Builder {
        private Integer id;
        private String email;
        private String token;
        private String senha;
        private String nome;
        private String cpf;
        private Date dataNascimento;
        private String telefone;
        private String numeroHabilitacao;
        private Perfil perfil;
        private String endereco;
        private BairroDTO bairro;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder senha(String senha) {
            this.senha = senha;
            return this;
        }

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder cpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public Builder dataNascimento(Date dataNascimento) {
            this.dataNascimento = dataNascimento;
            return this;
        }

        public Builder telefone(String telefone) {
            this.telefone = telefone;
            return this;
        }

        public Builder numeroHabilitacao(String numeroHabilitacao) {
            this.numeroHabilitacao = numeroHabilitacao;
            return this;
        }

        public Builder perfil(Perfil perfil) {
            this.perfil = perfil;
            return this;
        }

        public Builder endereco(String endereco) {
            this.endereco = endereco;
            return this;
        }

        public Builder bairro(BairroDTO bairro) {
            this.bairro = bairro;
            return this;
        }

        public UsuarioDTO build() {
            return new UsuarioDTO(id, email, token, senha, nome, cpf, dataNascimento, telefone, numeroHabilitacao, perfil, endereco, bairro);
        }
    }
}
