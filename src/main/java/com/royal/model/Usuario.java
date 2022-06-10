package com.royal.model;

/**
 * @author suporte
 */

public class Usuario {
    public boolean duasetapas;
    public String email;
    public String foto;
    public Integer id;
    public String nome;
    public String senha;

    public Usuario(String nome, String email, String senha, boolean duasetapas, String foto, Integer id) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.foto = foto;
        this.id = id;
        this.duasetapas = duasetapas;
    }

    public Usuario(String nome, String email, String senha, boolean duasetapas) {
        this(nome, email, senha, duasetapas, null, null);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Usuario{");
        sb.append("nome=").append(nome);
        sb.append(", email=").append(email);
        sb.append(", senha=").append(senha);
        sb.append(", foto=").append(foto);
        sb.append(", duasetapas=").append(duasetapas);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
