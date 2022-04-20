package com.royal.model;

import com.royal.Cripto;
import com.royal.Sistema;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author suporte
 */

public class Usuario {
    public String nome, email, senha, foto;
    public BigDecimal saldo;
    public boolean duasetapas;
    public Integer id;

    public Usuario(String nome, String email, String senha, BigDecimal saldo, boolean duasetapas, String foto, Integer id) {
	this.nome = Objects.requireNonNull(nome);
	this.email = Objects.requireNonNull(email);
	this.senha = Objects.requireNonNull(senha);
	this.foto = foto;
	this.id = id;
	this.saldo = Objects.requireNonNull(saldo);
	this.duasetapas = Objects.requireNonNull(duasetapas);
    }
    
    public Usuario(String nome, String email, String senha, BigDecimal saldo, boolean duasetapas) {
	this(nome, email, senha, saldo, duasetapas, null, null);
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("Usuario{");
	sb.append("nome=").append(nome);
	sb.append(", email=").append(email);
	sb.append(", senha=").append(senha);
	sb.append(", foto=").append(foto);
	sb.append(", saldo=").append(saldo);
	sb.append(", duasetapas=").append(duasetapas);
	sb.append(", id=").append(id);
	sb.append('}');
	return sb.toString();
    }

    public String token() {
	return Sistema.ENCRIPTA.encrypt(email).replace('=', '#') + "@" + Cripto.Encrypter.of(email).encrypt(senha).replace('=', '#');
    }
    
}
