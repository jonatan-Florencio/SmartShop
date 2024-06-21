package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Apartamento;

public class ApartamentoRepository implements BaseRepository<Apartamento> {

	@Override
	public Apartamento salvar(Apartamento novoApartamento) {
		String query = "INSERT INTO apartamento (apartamento) VALUES (?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			preencherParametrosParaInsertOuUpdate(pstmt, novoApartamento);
			
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			
			if(resultado.next()) {
				novoApartamento.setId(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao cadastrar apartamento.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return novoApartamento;
	}
	
	@Override
	public Boolean alterar(Apartamento apartamento) {
		boolean retorno = false;
		String query = "UPDATE apartamento SET apartamento=? WHERE id=?";		 
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		
		try {					 
			preencherParametrosParaInsertOuUpdate(pstmt, apartamento);
			pstmt.setInt(2, apartamento.getId());
			retorno = pstmt.executeUpdate() > 0;
		} catch(SQLException erro) {
			System.out.println("Não foi possível atualizar apartamento.");
			System.out.println("Erro " + erro.getMessage());
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}
	
	@Override
	public ArrayList<Apartamento> consultarTodos() {
		ArrayList<Apartamento> listaApartamentos = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM apartamento";
		
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				Apartamento apartamento = new Apartamento();
				
				preencherParametrosParaListarOuBuscar(resultado, apartamento);				
				listaApartamentos.add(apartamento);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao executar consultar todos os apartamentos.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaApartamentos;
	}
	
	@Override
	public Apartamento consultarPorId(int id) {
		Apartamento apartamento = new Apartamento();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = "SELECT * FROM apartamento WHERE id = " + id;
		
		try{
			resultado = stmt.executeQuery(query);
			
			if(resultado.next()) {
				preencherParametrosParaListarOuBuscar(resultado, apartamento);		
			}
						
		} catch (SQLException erro){
				System.out.println("Erro ao executar a busca por id.");
				System.out.println("Erro: " + erro.getMessage());
		} finally {
				Banco.closeResultSet(resultado);
				Banco.closeStatement(stmt);
				Banco.closeConnection(conn);
		}
		return apartamento;
	}
	
	@Override
	public Boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM apartamento WHERE id = " + id;
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir apartamento.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}
	
	private void preencherParametrosParaInsertOuUpdate(PreparedStatement pstmt
			, Apartamento novoApartamento) throws SQLException {
		
		pstmt.setInt(1, novoApartamento.getApartamento());		
	}
	
	private void preencherParametrosParaListarOuBuscar(ResultSet resultado
			, Apartamento apartamento) throws SQLException {
		
		apartamento.setId(Integer.parseInt(resultado.getString("id")));
		apartamento.setApartamento(resultado.getInt("apartamento"));
	}
}