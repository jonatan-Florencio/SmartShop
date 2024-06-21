package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Marca;
import model.entity.filtros.MarcaFiltro;

public class MarcaRepository implements BaseRepository<Marca> {

	@Override
	public Marca salvar(Marca novaMarca) {

		String sql = " INSERT INTO marca (nome) " + " VALUES(?) ";
		Connection conexao = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);

		try {
			stmt.setString(1, novaMarca.getNome());

			stmt.execute();
			ResultSet resultado = stmt.getGeneratedKeys();
			if (resultado.next()) {
				novaMarca.setId(resultado.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar nova marca");
			System.out.println("Erro: " + e.getMessage());
		}

		return novaMarca;
	}

	@Override
	public Boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM marca WHERE id = " + id;
		try {
			if (stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir marca");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public Boolean alterar(Marca marcaEditada) {
		boolean alterou = false;
		String query = " UPDATE marca "
		+ " SET nome=? "
		+ " WHERE id=? ";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		try {
		pstmt.setString(1, marcaEditada.getNome());
		pstmt.setInt(2, marcaEditada.getId());

				alterou = pstmt.executeUpdate() > 0;
			} catch (SQLException erro) {
				System.out.println("Erro ao atualizar marca");
				System.out.println("Erro: " + erro.getMessage());
			} finally {
				Banco.closePreparedStatement(pstmt);
				Banco.closeConnection(conn);
			}
			return alterou;
		}

	@Override
	public Marca consultarPorId(int id) {
		
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		Marca marca = null;
		ResultSet resultado = null;
		String query = " SELECT * FROM marca WHERE id = " + id;
		
		try{
			resultado = stmt.executeQuery(query);

			if(resultado.next()){
				marca = new Marca();
				preencherParametrosParaBuscarOuConsultarTodas(resultado, marca);
			
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar marca com o id: " + id);
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return marca;
	}

	@Override
	public ArrayList<Marca> consultarTodos() {
		ArrayList<Marca> marcas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM marca";
		
		try{
			resultado = stmt.executeQuery(query);
			
			while(resultado.next()){
				Marca marca = new Marca();
				preencherParametrosParaBuscarOuConsultarTodas(resultado, marca);
				
				marcas.add(marca);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar todas as marcas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return marcas;
	}
	
	public ArrayList<Marca> buscarComFiltro(MarcaFiltro filtro) {
		ArrayList<Marca> marcas = new ArrayList<Marca>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);		
		ResultSet resultado = null;
		String query = "SELECT * FROM marca";
		
		if(filtro.getNome() != null && !filtro.getNome().isEmpty()) {
			query += " WHERE nome COLLATE utf8mb4_general_ci LIKE '%" + filtro.getNome() + "%' ";
		}
				
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				Marca marca = new Marca();
				preencherParametrosParaBuscarOuConsultarTodas(resultado, marca);
				marcas.add(marca);
			}
		} catch(SQLException erro) {
			System.out.println("Erro ao buscar marcas.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}		
		return marcas;
	}
	
	public void preencherParametrosParaBuscarOuConsultarTodas(ResultSet resultado, Marca marca) throws SQLException {
		
		marca.setId(Integer.parseInt(resultado.getString("ID")));
		marca.setNome(resultado.getString("NOME"));
	}
	
	public int contarRegistros(MarcaFiltro filtro) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);		
		int qtdeRegistros = 0;
		String query = "SELECT COUNT(marca.id) FROM marca ";
				
		if(filtro.getNome() != null && !filtro.getNome().isEmpty()) {
			query += " WHERE nome COLLATE utf8mb4_general_ci LIKE '%" + filtro.getNome() + "%' ";
		}
		
		try {
			ResultSet resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				qtdeRegistros = resultado.getInt(1);
			}
		} catch(SQLException erro) {
			System.out.println("Erro ao buscar marcas.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return qtdeRegistros;
	}
	
	public int totalPaginas(MarcaFiltro filtro) {
		int limite = filtro.getLimite();
		int totalRegistros = contarRegistros(filtro);
		int qtdPaginas = totalRegistros / limite;
		int resto = totalRegistros % limite;
		
		if(resto > 0) {
			qtdPaginas++;
		}
		return qtdPaginas;
	}
	
	public boolean verificarMarcaExiste(Marca marca) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		String query = "SELECT * FROM marca WHERE nome COLLATE utf8mb4_general_ci LIKE '%" + marca.getNome() + "%'";
		try {
			resultado = stmt.executeQuery(query); 
			if(resultado.next()) { 
				retorno = true;
			}
		} catch(SQLException erro) {
			System.out.println("\nErro ao executar a query do método verificarMarcaExiste.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;		
	}
}