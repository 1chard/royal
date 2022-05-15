package com.royal.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author suporte
 */
public class TransferenciaUsuario {

	public Integer idTransferenciaUsuario;
	public BigDecimal valor;
	public java.sql.Date data;
	public String anexo;
	public String descricao;
	public String observacao;
	public boolean favorito;
	public java.sql.Date inicioRepeticao;
	public boolean parcelada;
	public boolean fixa;
	public Frequencia frequencia;
	public int idUsuario;
	public int idCategoria;

	public TransferenciaUsuario(BigDecimal valor, Date data, String descricao, boolean favorito, boolean parcelada, boolean fixa, int idUsuario, int idCategoria) {
		this.valor = valor;
		this.data = data;
		this.descricao = descricao;
		this.favorito = favorito;
		this.parcelada = parcelada;
		this.fixa = fixa;
		this.idUsuario = idUsuario;
		this.idCategoria = idCategoria;
	}

	public TransferenciaUsuario(BigDecimal valor, Date data, String descricao, boolean favorito, boolean parcelada, boolean fixa, int idUsuario, int idCategoria, Integer idTransferenciaUsuario, String anexo, String observacao, java.sql.Date inicioRepeticao, Frequencia frequencia) {
		this.idTransferenciaUsuario = idTransferenciaUsuario;
		this.valor = valor;
		this.data = data;
		this.anexo = anexo;
		this.descricao = descricao;
		this.observacao = observacao;
		this.favorito = favorito;
		this.inicioRepeticao = inicioRepeticao;
		this.parcelada = parcelada;
		this.fixa = fixa;
		this.frequencia = frequencia;
		this.idUsuario = idUsuario;
		this.idCategoria = idCategoria;

	}
	
	public boolean isDespesa(){
		return valor.compareTo(BigDecimal.ZERO) < 0;
	}
	
	public boolean isReceita(){
		return valor.compareTo(BigDecimal.ZERO) > 0;
	}

	public static class Builder {

		private Integer idTransferenciaUsuario;
		private final BigDecimal valor;
		private final Date data;
		private String anexo;
		private final String descricao;
		private String observacao;
		private final boolean favorito;
		private Date inicioRepeticao;
		private final boolean fixa;
		private Frequencia frequencia;
		private final int idUsuario;
		private final int idCategoria;
		private final boolean parcelada;

		public Builder(BigDecimal valor, Date data, String descricao, boolean favorito, boolean parcelada, boolean fixa, int idUsuario, int idCategoria) {
			this.valor = valor;
			this.data = data;
			this.descricao = descricao;
			this.favorito = favorito;
			this.parcelada = parcelada;
			this.fixa = fixa;
			this.idUsuario = idUsuario;
			this.idCategoria = idCategoria;
		}

		public Builder setIdTransferenciaUsuario(Integer idTransferenciaUsuario) {
			this.idTransferenciaUsuario = idTransferenciaUsuario;
			return this;
		}

		public Builder setAnexo(String anexo) {
			this.anexo = anexo;
			return this;
		}

		public Builder setObservacao(String observacao) {
			this.observacao = observacao;
			return this;
		}

		public Builder setInicioRepeticao(Date inicioRepeticao) {
			this.inicioRepeticao = inicioRepeticao;
			return this;
		}

		public Builder setFrequencia(Frequencia frequencia) {
			this.frequencia = frequencia;
			return this;
		}

		public TransferenciaUsuario build() {
			return new TransferenciaUsuario(valor, data, descricao, favorito, parcelada, fixa, idUsuario, idCategoria, idTransferenciaUsuario, anexo, observacao, inicioRepeticao, frequencia);
		}

	}

}
