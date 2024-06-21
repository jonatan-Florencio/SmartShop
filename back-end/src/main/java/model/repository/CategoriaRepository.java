package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Categoria;
import model.entity.filtros.CategoriaFiltro;

public class CategoriaRepository implements BaseRepository<Categoria> {

	@Override
	public Categoria salvar(Categoria novaCategoria) {

		String sql = " INSERT INTO categoria (nome) " + " VALUES(?) ";
		Connection conexao = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);

		try {
			stmt.setString(1, novaCategoria.getNome());

			stmt.execute();
			ResultSet resultado = stmt.getGeneratedKeys();
			if (resultado.next()) {
				novaCategoria.setId(resultado.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar nova categoria");
			System.out.println("Erro: " + e.getMessage());
		}

		return novaCategoria;
	}

	@Override
	public Boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM categoria WHERE id = " + id;
		try {
			if (stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir categoria");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public Boolean alterar(Categoria categoriaEditada) {
		boolean alterou = false;
		String query = " UPDATE categoria "
		+ " SET nome=? "
		+ " WHERE id=? ";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		try {
		pstmt.setString(1, categoriaEditada.getNome());
		pstmt.setInt(2, categoriaEditada.getId());

				alterou = pstmt.executeUpdate() > 0;
			} catch (SQLException erro) {
				System.out.println("Erro ao atualizar categoria");
				System.out.println("Erro: " + erro.getMessage());
			} finally {
				Banco.closePreparedStatement(pstmt);
				Banco.closeConnection(conn);
			}
			return alterou;
		}

	@Override
	public Categoria consultarPorId(int id) {
		
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		Categoria categoria = null;
		ResultSet resultado = null;
		String query = " SELECT * FROM categoria WHERE id = " + id;
		
		try{
			resultado = stmt.executeQuery(query);

			if(resultado.next()){
				categoria = new Categoria();
				preencherParametrosParaBuscarOuConsultarTodas(resultado, categoria);
			
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar categoria com o id: " + id);
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return categoria;
	}

	@Override
	public ArrayList<Categoria> consultarTodos() {
		ArrayList<Categoria> categorias = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM categoria";
		
		try{
			resultado = stmt.executeQuery(query);
			
			while(resultado.next()){
				Categoria categoria = new Categoria();
				preencherParametrosParaBuscarOuConsultarTodas(resultado, categoria);
				
				categorias.add(categoria);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar todas as categorias");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return categorias;
	}
	
	public ArrayList<Categoria> buscarComFiltro(CategoriaFiltro filtro) {
		ArrayList<Categoria> categorias = new ArrayList<Categoria>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);		
		ResultSet resultado = null;
		String query = "SELECT * FROM categoria";
		
		if(filtro.getNome() != null && !filtro.getNome().isEmpty()) {
			query += " WHERE nome COLLATE utf8mb4_general_ci LIKE '%" + filtro.getNome() + "%' ";
		}
				
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				Categoria categoria = new Categoria();
				preencherParametrosParaBuscarOuConsultarTodas(resultado, categoria);
				categorias.add(categoria);
			}
		} catch(SQLException erro) {
			System.out.println("Erro ao buscar categorias.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}		
		return categorias;
	}
	
	public void preencherParametrosParaBuscarOuConsultarTodas(ResultSet resultado, Categoria categoria) throws SQLException {
				
		categoria.setId(Integer.parseInt(resultado.getString("ID")));
		categoria.setNome(resultado.getString("NOME"));
	}
		
	public int contarRegistros(CategoriaFiltro filtro) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);		
		int qtdeRegistros = 0;
		String query = "SELECT COUNT(categoria.id) FROM categoria ";
				
		if(filtro.getNome() != null && !filtro.getNome().isEmpty()) {
			query += " WHERE nome COLLATE utf8mb4_general_ci LIKE '%" + filtro.getNome() + "%' ";
		}
		
		try {
			ResultSet resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				qtdeRegistros = resultado.getInt(1);
			}
		} catch(SQLException erro) {
			System.out.println("Erro ao buscar categorias.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return qtdeRegistros;
	}
	
	public int totalPaginas(CategoriaFiltro filtro) {
		int limite = filtro.getLimite();
		int totalRegistros = contarRegistros(filtro);
		int qtdPaginas = totalRegistros / limite;
		int resto = totalRegistros % limite;
		
		if(resto > 0) {
			qtdPaginas++;
		}
		return qtdPaginas;
	}
	
	public boolean verificarCategoriaExiste(Categoria categoria) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		String query = "SELECT * FROM categoria WHERE nome COLLATE utf8mb4_general_ci LIKE '%" + categoria.getNome() + "%'";
		try {
			resultado = stmt.executeQuery(query); 
			if(resultado.next()) { 
				retorno = true;
			}
		} catch(SQLException erro) {
			System.out.println("\nErro ao executar a query do método verificarCategoriaExiste.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;		
	}	
}