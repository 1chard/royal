package com.royal.model;

import jakarta.validation.constraints.Null;
import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author suporte
 */
public class DespesaUsuario {
    
    
    public Integer idDespesaUsuario;
    public BigDecimal valor;
    public java.sql.Date data;
    public boolean pendente;
    public String anexo;
    public String descricao;
    public String observacao;
    public boolean favorito;
    public java.sql.Date inicioRepeticao;
    public Integer totalParcelas;
    public Integer parcelasPagas;
    public Boolean parcelasFixas;
    public Frequencia nomeFrequencia;
    public int idUsuario;
    public int idCategoria;

    public DespesaUsuario(BigDecimal valor, Date data, boolean pendente, String descricao, boolean favorito, int idUsuario, int idCategoria, Integer idDespesaUsuario, String anexo, String observacao, Date inicioRepeticao, Integer totalParcelas, Integer parcelasPagas, Boolean parcelasFixas, Frequencia nomeFrequencia) {
	this.idDespesaUsuario = idDespesaUsuario;
	this.valor = valor;
	this.data = data;
	this.pendente = pendente;
	this.anexo = anexo;
	this.descricao = descricao;
	this.observacao = observacao;
	this.favorito = favorito;
	this.inicioRepeticao = inicioRepeticao;
	this.totalParcelas = totalParcelas;
	this.parcelasPagas = parcelasPagas;
	this.parcelasFixas = parcelasFixas;
	this.nomeFrequencia = nomeFrequencia;
	this.idUsuario = idUsuario;
	this.idCategoria = idCategoria;
    }

    public static class Builder{
	private Integer idDespesaUsuario;
	private final BigDecimal valor;
	private final java.sql.Date data;
	private final boolean pendente;
	private String anexo;
	private final String descricao;
	private String observacao;
	private final boolean favorito;
	private java.sql.Date inicioRepeticao;
	private Integer totalParcelas;
	private Integer parcelasPagas;
	private Boolean parcelasFixas;
	private Frequencia nomeFrequencia;
	private final int idUsuario;
	private final int idCategoria;

	public Builder(BigDecimal valor, Date data, boolean pendente, String descricao, boolean favorito, int idUsuario, int idCategoria) {
	    this.valor = valor;
	    this.data = data;
	    this.pendente = pendente;
	    this.descricao = descricao;
	    this.favorito = favorito;
	    this.idUsuario = idUsuario;
	    this.idCategoria = idCategoria;
	}

	public Builder idDespesaUsuario(Integer idDespesaUsuario) {
	    this.idDespesaUsuario = idDespesaUsuario;
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

	public Builder setTotalParcelas(Integer totalParcelas) {
	    this.totalParcelas = totalParcelas;
	    return this;
	}

	public Builder setParcelasPagas(Integer parcelasPagas) {
	    this.parcelasPagas = parcelasPagas;
	    return this;
	}

	public Builder setParcelasFixas(Boolean parcelasFixas) {
	    this.parcelasFixas = parcelasFixas;
	    return this;
	}

	public Builder setNomeFrequencia(Frequencia nomeFrequencia) {
	    this.nomeFrequencia = nomeFrequencia;
	    return this;
	}
	
	public DespesaUsuario build(){
	    return new DespesaUsuario(valor, data, pendente, descricao, favorito, idUsuario, idCategoria, idDespesaUsuario, anexo, observacao, inicioRepeticao, totalParcelas, parcelasPagas, parcelasFixas, nomeFrequencia);
	}
	
    }
    
}
