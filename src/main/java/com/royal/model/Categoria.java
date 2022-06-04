package com.royal.model;

import com.royal.dao.CategoriaDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author suporte
 */
public final class Categoria {
    public static final List<Categoria> DESPESAS = Collections.synchronizedList(new ArrayList<>());
    public static final List<Categoria> RECEITAS = Collections.synchronizedList(new ArrayList<>());

    static {
        CategoriaDAO.listar().forEach(categoria -> {
            switch (categoria.tipoTransferencia) {
                case DESPESA -> DESPESAS.add(categoria);
                case RECEITA -> RECEITAS.add(categoria);
            }
        });
    }

    public String cor;
    public String icone;
    public Integer idCategoria;
    public String nome;
    public TipoTransferencia tipoTransferencia;

    public Categoria(Integer idCategoria, String nome, String cor, String icone, TipoTransferencia tipoTransferencia) {
        this.idCategoria = idCategoria;
        this.nome = nome;
        this.cor = cor;
        this.icone = icone;
        this.tipoTransferencia = tipoTransferencia;
    }

    public static Categoria despesaPorId(int id) {
        for (Categoria despesa : DESPESAS) {
            if (despesa.idCategoria == id) {
                return despesa;
            }
        }

        return null;
    }

    public static Categoria receitaPorId(int id) {
        for (Categoria receita : RECEITAS) {
            if (receita.idCategoria == id) {
                return receita;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Categoria{");
        sb.append("idCategoria=").append(idCategoria);
        sb.append(", nome=").append(nome);
        sb.append(", cor=").append(cor);
        sb.append(", icone=").append(icone);
        sb.append(", tipoTransferencia=").append(tipoTransferencia);
        sb.append('}');
        return sb.toString();
    }


    public enum TipoTransferencia {
        DESPESA,
        RECEITA
    }
}
