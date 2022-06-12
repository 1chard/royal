package com.royal.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author suporte
 */
public class TransferenciaUsuario {
	public static class Builder {
		private final Date data;
		private final String descricao;
		private final boolean favorito;
		private final boolean fixa;
		private final int idCategoria;
		private final int idUsuario;
		private final boolean parcelada;
		private final BigDecimal valor;
		private String anexo;
		private Frequencia frequencia;
		private Integer idTransferenciaUsuario;
		private String observacao;
		private int parcelas;

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

		public TransferenciaUsuario build() {
			return new TransferenciaUsuario(valor, data, descricao, favorito, parcelada, fixa, idUsuario, idCategoria, idTransferenciaUsuario, anexo, observacao, frequencia, parcelas);
		}

		public Builder setAnexo(String anexo) {
			this.anexo = anexo;
			return this;
		}


		public Builder setFrequencia(Frequencia frequencia) {
			this.frequencia = frequencia;
			return this;
		}

		public Builder setIdTransferenciaUsuario(Integer idTransferenciaUsuario) {
			this.idTransferenciaUsuario = idTransferenciaUsuario;
			return this;
		}

		public Builder setObservacao(String observacao) {
			this.observacao = observacao;
			return this;
		}

		public Builder setParcelas(int parcelas) {
			this.parcelas = parcelas;
			return this;
		}


	}
	public String anexo;
	public java.sql.Date data;
	public String descricao;
	public boolean favorito;
	public boolean fixa;
	public Frequencia frequencia;
	public int idCategoria;
	public Integer idTransferenciaUsuario;
	public int idUsuario;
	public String observacao;
	public boolean parcelada;
	public int parcelas;
	public BigDecimal valor;

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

	public TransferenciaUsuario(BigDecimal valor, Date data, String descricao, boolean favorito, boolean parcelada, boolean fixa, int idUsuario, int idCategoria, Integer idTransferenciaUsuario, String anexo, String observacao, Frequencia frequencia, int parcelas) {
		this.idTransferenciaUsuario = idTransferenciaUsuario;
		this.valor = valor;
		this.data = data;
		this.anexo = anexo;
		this.descricao = descricao;
		this.observacao = observacao;
		this.favorito = favorito;
		this.parcelada = parcelada;
		this.fixa = fixa;
		this.frequencia = frequencia;
		this.idUsuario = idUsuario;
		this.idCategoria = idCategoria;
		this.parcelas = parcelas;
	}

	@Override
	public String toString() {
		return "TransferenciaUsuario{" + "anexo=" + anexo + ", data=" + data + ", descricao=" + descricao + ", favorito=" + favorito + ", fixa=" + fixa + ", frequencia=" + frequencia + ", idCategoria=" + idCategoria + ", idTransferenciaUsuario=" + idTransferenciaUsuario + ", idUsuario=" + idUsuario + ", observacao=" + observacao + ", parcelada=" + parcelada + ", parcelas=" + parcelas + ", valor=" + valor + '}';
	}

	public boolean isDespesa() {
		return valor.compareTo(BigDecimal.ZERO) < 0;
	}

	public boolean isReceita() {
		return valor.compareTo(BigDecimal.ZERO) > 0;
	}

}
