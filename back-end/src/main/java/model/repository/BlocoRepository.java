package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Bloco;

public class BlocoRepository implements BaseRepository<Bloco> {

	@Override
	public Bloco salvar(Bloco novoBloco) {
		String query = "INSERT INTO bloco (bloco) VALUES (?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			preencherParametrosParaInsertOuUpdate(pstmt, novoBloco);
			
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			
			if(resultado.next()) {
				novoBloco.setId(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao cadastrar bloco.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return novoBloco;
	}
	
	@Override
	public Boolean alterar(Bloco bloco) {
		boolean retorno = false;
		String query = "UPDATE bloco SET bloco=? WHERE id=?";		 
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		
		try {					 
			preencherParametrosParaInsertOuUpdate(pstmt, bloco);
			pstmt.setInt(2, bloco.getId());
			retorno = pstmt.executeUpdate() > 0;
		} catch(SQLException erro) {
			System.out.println("Não foi possível atualizar bloco.");
			System.out.println("Erro " + erro.getMessage());
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}
	
	@Override
	public ArrayList<Bloco> consultarTodos() {
		ArrayList<Bloco> listaBlocos = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM bloco";
		
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				Bloco bloco = new Bloco();
				
				preencherParametrosParaListarOuBuscar(resultado, bloco);				
				listaBlocos.add(bloco);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao executar consultar todos os blocos.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaBlocos;
	}
	
	@Override
	public Bloco consultarPorId(int id) {
		Bloco bloco = new Bloco();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = "SELECT * FROM bloco WHERE id = " + id;
		
		try{
			resultado = stmt.executeQuery(query);
			
			if(resultado.next()) {
				preencherParametrosParaListarOuBuscar(resultado, bloco);		
			}
						
		} catch (SQLException erro){
				System.out.println("Erro ao executar a busca por id.");
				System.out.println("Erro: " + erro.getMessage());
		} finally {
				Banco.closeResultSet(resultado);
				Banco.closeStatement(stmt);
				Banco.closeConnection(conn);
		}
		return bloco;
	}
	
	@Override
	public Boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM bloco WHERE id = " + id;
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir bloco.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}
	
	private void preencherParametrosParaInsertOuUpdate(PreparedStatement pstmt
			, Bloco novoBloco) throws SQLException {
		
		pstmt.setString(1, novoBloco.getBloco());		
	}
	
	private void preencherParametrosParaListarOuBuscar(ResultSet resultado
			, Bloco bloco) throws SQLException {
		
		bloco.setId(Integer.parseInt(resultado.getString("id")));
		bloco.setBloco(resultado.getString("bloco"));
	}
}