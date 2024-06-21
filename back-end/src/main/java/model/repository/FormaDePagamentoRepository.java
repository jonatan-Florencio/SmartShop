package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.FormaDePagamento;
import model.entity.enums.TipoFormaPagamento;

public class FormaDePagamentoRepository implements BaseRepository<FormaDePagamento> {

    @Override
    public FormaDePagamento salvar(FormaDePagamento novaFormaDePagamento) {
        String query = "INSERT INTO forma_pagamento (tipo) VALUES (?)";
        Connection conn = Banco.getConnection();
        PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
        try {
            preencherParametrosParaInsertOuUpdate(pstmt, novaFormaDePagamento);

            pstmt.execute();
            ResultSet resultado = pstmt.getGeneratedKeys();

            if (resultado.next()) {
                novaFormaDePagamento.setId(resultado.getInt(1));
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao cadastrar forma de pagamento.");
            System.out.println("Erro: " + erro.getMessage());
        } finally {
            Banco.closeStatement(pstmt);
            Banco.closeConnection(conn);
        }
        return novaFormaDePagamento;
    }

    @Override
    public Boolean alterar(FormaDePagamento formaDePagamento) {
        boolean retorno = false;
        String query = "UPDATE forma_pagamento SET tipo=? WHERE id=?";
        Connection conn = Banco.getConnection();
        PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);

        try {
            preencherParametrosParaInsertOuUpdate(pstmt, formaDePagamento);
            pstmt.setInt(2, formaDePagamento.getId());
            retorno = pstmt.executeUpdate() > 0;
        } catch (SQLException erro) {
            System.out.println("Não foi possível atualizar forma de pagamento.");
            System.out.println("Erro: " + erro.getMessage());
        } finally {
            Banco.closePreparedStatement(pstmt);
            Banco.closeConnection(conn);
        }
        return retorno;
    }

    @Override
    public ArrayList<FormaDePagamento> consultarTodos() {
        ArrayList<FormaDePagamento> listaFormaDePagamentos = new ArrayList<>();
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);

        ResultSet resultado = null;
        String query = "SELECT * FROM forma_pagamento";

        try {
            resultado = stmt.executeQuery(query);
            while (resultado.next()) {
                FormaDePagamento formaDePagamento = new FormaDePagamento();
                preencherParametrosParaListarOuBuscar(resultado, formaDePagamento);
                listaFormaDePagamentos.add(formaDePagamento);
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao consultar todas as formas de pagamento.");
            System.out.println("Erro: " + erro.getMessage());
        } finally {
            Banco.closeResultSet(resultado);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return listaFormaDePagamentos;
    }

    @Override
    public FormaDePagamento consultarPorId(int id) {
        FormaDePagamento formaDePagamento = new FormaDePagamento();
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);

        ResultSet resultado = null;
        String query = "SELECT * FROM forma_pagamento WHERE id = " + id;

        try {
            resultado = stmt.executeQuery(query);
            if (resultado.next()) {
                preencherParametrosParaListarOuBuscar(resultado, formaDePagamento);
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao buscar forma de pagamento por id.");
            System.out.println("Erro: " + erro.getMessage());
        } finally {
            Banco.closeResultSet(resultado);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return formaDePagamento;
    }

    @Override
    public Boolean excluir(int id) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        boolean excluiu = false;
        String query = "DELETE FROM forma_pagamento WHERE id = " + id;
        try {
            if (stmt.executeUpdate(query) == 1) {
                excluiu = true;
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao excluir forma de pagamento.");
            System.out.println("Erro: " + erro.getMessage());
        } finally {
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return excluiu;
    }

    private void preencherParametrosParaInsertOuUpdate(PreparedStatement pstmt, FormaDePagamento novaFormaDePagamento) throws SQLException {
        pstmt.setString(1, novaFormaDePagamento.getTipoFormaPagamento().name());
    }

    private void preencherParametrosParaListarOuBuscar(ResultSet resultado, FormaDePagamento formaDePagamento) throws SQLException {
        formaDePagamento.setId(resultado.getInt("id"));
        formaDePagamento.setTipoFormaPagamento(TipoFormaPagamento.valueOf(resultado.getString("tipo")));
    }
}
