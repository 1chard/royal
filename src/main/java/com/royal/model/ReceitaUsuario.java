package com.royal.model;

import jakarta.validation.constraints.Null;
import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author suporte
 */
public class ReceitaUsuario {
    
    
    public Integer idReceitaUsuario;
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

    public ReceitaUsuario(BigDecimal valor, Date data, boolean pendente, String descricao, boolean favorito, int idUsuario, int idCategoria, Integer idReceitaUsuario, String anexo, String observacao, Date inicioRepeticao, Integer totalParcelas, Integer parcelasPagas, Boolean parcelasFixas, Frequencia nomeFrequencia) {
	this.idReceitaUsuario = idReceitaUsuario;
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

    public static class ReceitaUsuarioBuilder{
	private Integer idReceitaUsuario;
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

	public ReceitaUsuarioBuilder(BigDecimal valor, Date data, boolean pendente, String descricao, boolean favorito, int idUsuario, int idCategoria) {
	    this.valor = valor;
	    this.data = data;
	    this.pendente = pendente;
	    this.descricao = descricao;
	    this.favorito = favorito;
	    this.idUsuario = idUsuario;
	    this.idCategoria = idCategoria;
	}

	public ReceitaUsuarioBuilder idReceitaUsuario(Integer idReceitaUsuario) {
	    this.idReceitaUsuario = idReceitaUsuario;
	    return this;
	}

	public ReceitaUsuarioBuilder setAnexo(String anexo) {
	    this.anexo = anexo;
	    return this;
	}

	public ReceitaUsuarioBuilder setObservacao(String observacao) {
	    this.observacao = observacao;
	    return this;
	}

	public ReceitaUsuarioBuilder setInicioRepeticao(Date inicioRepeticao) {
	    this.inicioRepeticao = inicioRepeticao;
	    return this;
	}

	public ReceitaUsuarioBuilder setTotalParcelas(Integer totalParcelas) {
	    this.totalParcelas = totalParcelas;
	    return this;
	}

	public ReceitaUsuarioBuilder setParcelasPagas(Integer parcelasPagas) {
	    this.parcelasPagas = parcelasPagas;
	    return this;
	}

	public ReceitaUsuarioBuilder setParcelasFixas(Boolean parcelasFixas) {
	    this.parcelasFixas = parcelasFixas;
	    return this;
	}

	public ReceitaUsuarioBuilder setNomeFrequencia(Frequencia nomeFrequencia) {
	    this.nomeFrequencia = nomeFrequencia;
	    return this;
	}
	
	public ReceitaUsuario build(){
	    return new ReceitaUsuario(valor, data, pendente, descricao, favorito, idUsuario, idCategoria, idReceitaUsuario, anexo, observacao, inicioRepeticao, totalParcelas, parcelasPagas, parcelasFixas, nomeFrequencia);
	}
	
    }
    
}
