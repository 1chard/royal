package com.royal.servlet;

import com.qsoniter.JsonArray;
import com.qsoniter.JsonIterator;
import com.qsoniter.JsonObject;
import com.qsoniter.ValueType;
import com.qsoniter.spi.JsonException;
import com.qsoniter.spi.TypeMismatchException;
import com.royal.API;
import com.royal.Extra;
import com.royal.Sistema;
import com.royal.Status;
import com.royal.dao.TransferenciaUsuarioDAO;
import com.royal.dao.UsuarioDAO;
import com.royal.model.Categoria;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * @author suporte
 */
@WebServlet(name = "Dashboard", urlPatterns = {"/data/*"})
public class Data extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var args = API.parameters(req);

        String token;

        if (Sistema.PESSOAS.containsKey((token = req.getParameter("k")))) {


            var sessao = Sistema.PESSOAS.get(token);
            var pessoa = sessao.usuario;

            if(sessao.atualizarUltimaConexao()){
                TransferenciaUsuarioDAO.atualizarFixas(pessoa.id);
            }

            var retornos = new Object[args.length];

            for (int i = 0, length = args.length; i < length; i++) {
                final Object json;

                switch (args[i]) {
                    case "saldo", "liquido" -> {
                        final BigDecimal despesa;
                        final BigDecimal receita;

                        var ano = Extra.parseInteger(req.getParameter("ano"));

                        if (ano != null) {

                            var mes = Extra.parseInteger(req.getParameter("mes"));

                            if (mes != null) {
                                despesa = TransferenciaUsuarioDAO.despesaLiquidaMensal(pessoa.id, ano, mes);
                                receita = TransferenciaUsuarioDAO.receitaLiquidaMensal(pessoa.id, ano, mes);
                            } else {
                                despesa = TransferenciaUsuarioDAO.despesaLiquidaAnual(pessoa.id, ano);
                                receita = TransferenciaUsuarioDAO.receitaLiquidaAnual(pessoa.id, ano);
                            }
                        } else {
                            despesa = TransferenciaUsuarioDAO.despesaLiquidaGeral(pessoa.id);
                            receita = TransferenciaUsuarioDAO.receitaLiquidaGeral(pessoa.id);
                        }

                        json = new JsonObject().add("despesa", despesa)
                                .add("receita", receita)
                                .add("saldo", receita.subtract(despesa));
                    }
                    case "bruto", "saldo-bruto" -> {
                        final BigDecimal despesa;
                        final BigDecimal receita;

                        var ano = Extra.parseInteger(req.getParameter("ano"));

                        if (ano != null) {

                            var mes = Extra.parseInteger(req.getParameter("mes"));

                            if (mes != null) {
                                despesa = TransferenciaUsuarioDAO.despesaMensal(pessoa.id, ano, mes);
                                receita = TransferenciaUsuarioDAO.receitaMensal(pessoa.id, ano, mes);
                            } else {
                                despesa = TransferenciaUsuarioDAO.despesaAnual(pessoa.id, ano);
                                receita = TransferenciaUsuarioDAO.receitaAnual(pessoa.id, ano);
                            }
                        } else {
                            despesa = TransferenciaUsuarioDAO.despesaGeral(pessoa.id);
                            receita = TransferenciaUsuarioDAO.receitaGeral(pessoa.id);
                        }

                        json = new JsonObject().add("despesa", despesa)
                                .add("receita", receita)
                                .add("saldo", receita.subtract(despesa));
                    }
                    case "saldo-geral" -> json = TransferenciaUsuarioDAO.saldoLiquidoGeral(pessoa.id);

                    case "categorias" -> {
                        var objeto = new JsonObject();

                        objeto.add("despesas", Categoria.DESPESAS);
                        objeto.add("receitas", Categoria.RECEITAS);

                        json = objeto;
                    }
                    case "favorito" -> {
                        var lista = new JsonArray();

                        TransferenciaUsuarioDAO.favoritos(pessoa.id).forEach(despesa -> lista.add(
                                new JsonObject()
                                        .add("id", despesa.idTransferenciaUsuario)
                                        .add("valor", despesa.valor)
                                        .add("data", despesa.data.toString())
                                        .add("anexo", despesa.anexo)
                                        .add("descricao", despesa.descricao)
                                        .add("observacao", despesa.observacao)
                                        .add("parcelada", despesa.parcelada)
                                        .add("fixa", despesa.fixa)
                                        .add("nomeFrequencia", despesa.frequencia != null ? despesa.frequencia.toString() : null)
                                        .add("categoria", despesa.idCategoria)
                                        .add("parcelas", despesa.parcelas)
                        ));

                        json = lista;
                    }
                    case "transferencia" -> {
                        var ids = Extra.orDefault(req.getParameterValues("id"), new String[0]);


                        var lista = new JsonArray();

                        TransferenciaUsuarioDAO.buscarIds(pessoa.id, ids).forEach(despesa -> lista.add(
                                new JsonObject()
                                        .add("id", despesa.idTransferenciaUsuario)
                                        .add("valor", despesa.valor)
                                        .add("data", despesa.data.toString())
                                        .add("anexo", despesa.anexo)
                                        .add("descricao", despesa.descricao)
                                        .add("observacao", despesa.observacao)
                                        .add("parcelada", despesa.parcelada)
                                        .add("fixa", despesa.fixa)
                                        .add("nomeFrequencia", despesa.frequencia != null ? despesa.frequencia.toString() : null)
                                        .add("categoria", despesa.idCategoria)
                                        .add("parcelas", despesa.parcelas)
                        ));

                        json = lista;

                    }
                    case "perfil" -> json = new JsonObject()
                            .add("nome", pessoa.nome)
                            .add("email", pessoa.email)
                            .add("duasetapas", pessoa.duasetapas)
                            .add("foto", pessoa.foto);
                    case "extrato-mes" -> {
                        var lista = new ArrayList<JsonObject>();

                        var mes = Integer.parseInt(req.getParameter("mes"));
                        var ano = Integer.parseInt(req.getParameter("ano"));
//			var categorias = Extra.orDefault(req.getParameterValues("cat"), new String[0]);

                        TransferenciaUsuarioDAO.listarMensal(pessoa.id, ano, mes).forEach(despesa -> lista.add(
                                new JsonObject()
                                        .add("id", despesa.idTransferenciaUsuario)
                                        .add("data", despesa.data)
                                        .add("valor", despesa.valor)
                                        .add("categoria", despesa.idCategoria)
                                        .add("descricao", despesa.descricao)
                                        .add("indice", despesa.indice)
                                        .add("parcelas", despesa.parcelas)
                        ));


                        lista.sort((o1, o2) -> ((Date) o2.get("data")).compareTo(((Date) o1.get("data"))));

                        lista.forEach(mapa -> mapa.put("data", mapa.get("data").toString()));

                        json = lista;
                    }
                    default -> {
                        resp.sendError(400);
                        return;
                    }
                }

                retornos[i] = json;
            }

            if (retornos.length == 1) {
                resp.getWriter().append(retornos[0].toString()).flush();
            } else {
                resp.getWriter().append(Arrays.toString(retornos)).flush();
            }
        } else {
            resp.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var args = API.parameters(req);

        String token;

        if (Sistema.PESSOAS.containsKey((token = req.getParameter("k"))) && args.length > 0) {

            var pessoa = Sistema.PESSOAS.get(token).usuario;
            var json = JsonIterator.deserialize(req.getInputStream().readAllBytes());

            var mensagem = new JsonObject();
            Status status;
            int httpStatus;

            try {
                switch (args[0]) {
                    case "perfil" -> {
                        String nome = json.get("nome").mustBe(ValueType.STRING).asString();
                        boolean duasetapas = json.get("duasetapas").mustBe(ValueType.BOOLEAN).asBoolean();
                        String foto = json.get("foto").asString();

                        UsuarioDAO.editarNomeDuasEtapasFoto(
                                pessoa.id,
                                nome,
                                duasetapas,
                                foto
                        );

                        synchronized (pessoa) {
                            pessoa.nome = nome;
                            pessoa.duasetapas = duasetapas;
                            pessoa.foto = foto;
                        }

                        status = Status.OK;
                        httpStatus = 200;
                    }
                    case "desfavoritar" -> {
                        if (TransferenciaUsuarioDAO.desfavoritar(json.get("id").mustBe(ValueType.NUMBER).asInt())) {
                            status = Status.OK;
                            httpStatus = 200;
                        } else {
                            status = Status.REQUISICAO_INVALIDA;
                            httpStatus = 400;
                        }
                    }
                    case "senha" -> {
                        var novaSenha = json.get("nova").mustBe(ValueType.STRING).asString();
                        var antigaSenha = json.get("antiga").mustBe(ValueType.STRING).asString();

                        if (pessoa.senha.equals(antigaSenha)) {

                            if (UsuarioDAO.editarSenhaPorId(pessoa.id, novaSenha)) {
                                httpStatus = 200;
                                status = Status.OK;
                            } else {
                                status = Status.REQUISICAO_INVALIDA;
                                httpStatus = 400;
                            }

                        } else {
<<<<<<< HEAD
			    status = Status.SENHA_INCORRETA;
                            httpStatus = 400;
			}
=======
                            status = Status.SENHA_INCORRETA;
                            httpStatus = 204;
                        }
>>>>>>> e7b64a68dc8573413779deb1d9232a4f23347aac
                    }
                    default -> {
                        status = Status.REQUISICAO_INVALIDA;
                        httpStatus = 400;
                    }
                }
            } catch (TypeMismatchException e) {
                status = Status.CAMPO_TIPO_INCORRETO;
                httpStatus = 400;
            } catch (JsonException e) {
                status = Status.JSON_INVALIDO;
                httpStatus = 400;
            }

            resp.setStatus(httpStatus);
            mensagem.add("status", status.codigo);

            resp.getWriter().append(mensagem.toString()).flush();

        } else {
            resp.sendError(404);
        }
    }

}
