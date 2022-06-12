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

	public Usuario(String nome, String email, String senha, boolean duasetapas) {
		this(nome, email, senha, duasetapas, null, null);
	}

	public Usuario(String nome, String email, String senha, boolean duasetapas, String foto, Integer id) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.foto = foto;
		this.id = id;
		this.duasetapas = duasetapas;
	}

	@Override
	public String toString() {
		final String sb = "Usuario{" +
				"nome=" + nome +
				", email=" + email +
				", senha=" + senha +
				", foto=" + foto +
				", duasetapas=" + duasetapas +
				", id=" + id +
				'}';
		return sb;
	}
}
