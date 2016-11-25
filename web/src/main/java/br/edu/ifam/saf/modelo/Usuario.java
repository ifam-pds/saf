package br.edu.ifam.saf.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.edu.ifam.saf.enums.Perfil;

@Entity
@Table(name = "usuario")
public class Usuario extends EntidadeBase {


    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String cpf;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataNascimento;
    private String telefone;
    private String numeroHabilitacao;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(length = 64)
    private String token;
    @Column(nullable = false, length = 60)
    private String senha;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Perfil perfil;
    private String endereco;
    @ManyToOne
    private Bairro bairro;

    public Usuario() {
    }

    public Usuario(String nome, String cpf, Date dataNascimento, String telefone, String numeroHabilitacao, String email, String token, String senha, Perfil perfil, String endereco, Bairro bairro) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.numeroHabilitacao = numeroHabilitacao;
        this.email = email;
        this.token = token;
        this.senha = senha;
        this.perfil = perfil;
        this.endereco = endereco;
        this.bairro = bairro;
    }

    public Usuario(String nome) {
        this.nome = nome;
    }

    public boolean isAdmin() {
        return perfil == Perfil.ADMINISTRADOR;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Usuario usuario = (Usuario) o;

        if (nome != null ? !nome.equals(usuario.nome) : usuario.nome != null) return false;
        if (cpf != null ? !cpf.equals(usuario.cpf) : usuario.cpf != null) return false;
        if (dataNascimento != null ? !dataNascimento.equals(usuario.dataNascimento) : usuario.dataNascimento != null)
            return false;
        if (telefone != null ? !telefone.equals(usuario.telefone) : usuario.telefone != null)
            return false;
        if (numeroHabilitacao != null ? !numeroHabilitacao.equals(usuario.numeroHabilitacao) : usuario.numeroHabilitacao != null)
            return false;
        if (email != null ? !email.equals(usuario.email) : usuario.email != null) return false;
        if (token != null ? !token.equals(usuario.token) : usuario.token != null) return false;
        if (senha != null ? !senha.equals(usuario.senha) : usuario.senha != null) return false;
        if (perfil != usuario.perfil) return false;
        if (endereco != null ? !endereco.equals(usuario.endereco) : usuario.endereco != null)
            return false;
        return bairro != null ? bairro.equals(usuario.bairro) : usuario.bairro == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (cpf != null ? cpf.hashCode() : 0);
        result = 31 * result + (dataNascimento != null ? dataNascimento.hashCode() : 0);
        result = 31 * result + (telefone != null ? telefone.hashCode() : 0);
        result = 31 * result + (numeroHabilitacao != null ? numeroHabilitacao.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (senha != null ? senha.hashCode() : 0);
        result = 31 * result + (perfil != null ? perfil.hashCode() : 0);
        result = 31 * result + (endereco != null ? endereco.hashCode() : 0);
        result = 31 * result + (bairro != null ? bairro.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", telefone='" + telefone + '\'' +
                ", numeroHabilitacao='" + numeroHabilitacao + '\'' +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                ", perfil=" + perfil +
                ", endereco='" + endereco + '\'' +
                ", bairro=" + bairro +
                '}';
    }

    public static class Builder {
        private Integer id;
        private String nome;
        private String cpf;
        private Date dataNascimento;
        private String telefone;
        private String numeroHabilitacao;
        private String email;
        private String token;
        private String senha;
        private Perfil perfil;
        private String endereco;
        private Bairro bairro;


        public Builder id(Integer id) {
            this.id = id;
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

        public Builder perfil(Perfil perfil) {
            this.perfil = perfil;
            return this;
        }

        public Builder endereco(String endereco) {
            this.endereco = endereco;
            return this;
        }

        public Builder bairro(Bairro bairro) {
            this.bairro = bairro;
            return this;
        }

        public Usuario build() {
            return new Usuario(nome, cpf, dataNascimento, telefone, numeroHabilitacao, email, token, senha, perfil, endereco, bairro);
        }
    }

}
