package com.royal;

import com.royal.jdbc.MySQL;
import com.royal.jdbc.SQL;
import com.royal.model.Usuario;
import jakarta.servlet.annotation.WebServlet;

import java.sql.SQLException;
import java.util.*;


@WebServlet(loadOnStartup = 1)
public class Sistema {
    public static final String PASTA;
    public static final SQL BANCO;
    public static final Map<String, Sessao> PESSOAS = Collections.synchronizedMap(new HashMap<>());
    public static final Calendar CALENDARIO = GregorianCalendar.getInstance();
    private static final String HOST;
    private static final String DATABASE;
    private static final String USER;
    private static final String PASSWORD;
    private static final String CHAVE;
    private static final long serialVersionUID = 1L;

    static {
        switch (System.getProperty("user.name")) {
            case "richard" -> {
                HOST = "localhost:3306";
                DATABASE = "royal";
                USER = "richard";
                PASSWORD = "123";
                CHAVE = "pOrQuEaTeRrAéPlAnA";
                PASTA = "/home/richard/Área de trabalho/imagem2";
            }
            case "suporte" -> {
                HOST = "localhost:3306";
                DATABASE = "royal";
                USER = "root";
                PASSWORD = "12345678";
                CHAVE = "cumonista";
                PASTA = "/Users/suporte/Desktop/imagem";
            }
            case "thime" -> {
                HOST = "localhost:3306";
                DATABASE = "royal";
                USER = "root";
                PASSWORD = "bcd127";
                CHAVE = "cumonista";
                PASTA = "C:\\Users\\thime\\OneDrive\\Área de Trabalho\\senai\\TCC\\backend\\royal-master\\imagens";
            }
            default -> {
                HOST = "10.5.60.39:3306";
                DATABASE = "royal";
                USER = "royal";
                PASSWORD = "royal";
                CHAVE = "alemanhaSemprePerde";
                PASTA = null;
            }
        }

        try {
            BANCO = new MySQL(HOST, DATABASE, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static final Cripto ENCRIPTA = Cripto.Encrypter.of(CHAVE);
    public static final Cripto DESENCRIPTA = Cripto.Decrypter.of(CHAVE);

    private Sistema() {
    }

    public static class Sessao {
        //        public Map<String, Object> objetos = new HashMap<>();
        public Usuario usuario;
        private Date ultimaConexao = new Date();

        /**
         * Retorna se passou um dia ou mais, atualizando a ultima conexao
         */
        public boolean atualizarUltimaConexao() {
            var novo = new Date();
            int dias = (int) (novo.getTime() / 86400000L - ultimaConexao.getTime() / 86400000L);
            ultimaConexao = novo;

            return dias > 0;
        }
    }
}
