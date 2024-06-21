package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import model.entity.Carrinho;
import model.entity.Item;
import model.entity.filtros.CarrinhoFiltro;

public class CarrinhoRepository {

	public Carrinho salvar(Carrinho novoCarrinho) {
		String query = "INSERT INTO carrinho (id_cliente, data_compra, total) VALUES (?, ?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			if(novoCarrinho.getCliente() != null) {
				pstmt.setInt(1, novoCarrinho.getCliente().getId());				
			} else {
				pstmt.setInt(1, 0);
			}
			pstmt.setObject(2, LocalDate.now());	
			pstmt.setDouble(3, 0);
			
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			
			if(resultado.next()) {
				novoCarrinho.setId(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao iniciar carrinho.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return novoCarrinho;
	}
	
	public Boolean atualizarTotal(Item item) {
		boolean retorno = false;
		String query = "UPDATE carrinho SET total=? WHERE id= " + item.getIdCarrinho();		 
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		Double totalAtualizado = item.getPreco() * item.getQuantidade();
		
		try {					 
			pstmt.setDouble(1, totalAtualizado);
			retorno = pstmt.executeUpdate() > 0;
		} catch(SQLException erro) {
			System.out.println("Não foi possível atualizar total carrinho.");
			System.out.println("Erro " + erro.getMessage());
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}
	
	public Boolean atualizarFormaPagamento(Carrinho carrinho) {
		boolean retorno = false;
		String query = "UPDATE carrinho SET id_forma_pagamento=? WHERE id=?";		 
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		
		try {					 
			pstmt.setInt(1, carrinho.getFormaDePagamento().getId());
			pstmt.setInt(2, carrinho.getId());
			retorno = pstmt.executeUpdate() > 0;
		} catch(SQLException erro) {
			System.out.println("Não foi possível atualizar forma de pagamento.");
			System.out.println("Erro " + erro.getMessage());
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}
	
	public ArrayList<Carrinho> consultarTodos() {
		ArrayList<Carrinho> listaCompras = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM carrinho";
		
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				Carrinho carrinho = new Carrinho();
				
				preencherParametrosParaListarOuBuscar(resultado, carrinho);				
				listaCompras.add(carrinho);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao executar consultar todas as compras.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaCompras;
	}
	
	public ArrayList<Carrinho> buscarComFiltro(CarrinhoFiltro filtro) {
		ArrayList<Carrinho> carrinhos = new ArrayList<Carrinho>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);		
		ResultSet resultado = null;
		String query = "SELECT c.* FROM carrinho c inner join usuario u on c.id_cliente = u.id "
				+ "inner join forma_pagamento fp on c.id_forma_pagamento = fp.id ";
		
		query += preencherFiltros(filtro);
		
		if(filtro.temPaginacao()) {
			query += " LIMIT " + filtro.getLimite() + " OFFSET " + filtro.getOffset();
		}
		
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				Carrinho carrinho = new Carrinho();
				preencherParametrosParaListarOuBuscar(resultado, carrinho);
				carrinhos.add(carrinho);
			}
		} catch(SQLException erro) {
			System.out.println("Erro ao buscar carrinhos");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}		
		return carrinhos;
	}
	
	public Carrinho consultarPorId(int id) {
		Carrinho carrinho = new Carrinho();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = "SELECT * FROM carrinho WHERE id = " + id;
		
		try{
			resultado = stmt.executeQuery(query);
			
			if(resultado.next()) {
				preencherParametrosParaListarOuBuscar(resultado, carrinho);		
			}
						
		} catch (SQLException erro){
				System.out.println("Erro ao executar a busca por id.");
				System.out.println("Erro: " + erro.getMessage());
		} finally {
				Banco.closeResultSet(resultado);
				Banco.closeStatement(stmt);
				Banco.closeConnection(conn);
		}
		return carrinho;
	}
	
	public Boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM carrinho WHERE id = " + id;
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir compra");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}
	
	private void preencherParametrosParaListarOuBuscar(ResultSet resultado
			, Carrinho carrinho) throws SQLException {
		FormaDePagamentoRepository formaDePagamento = new FormaDePagamentoRepository();
		UsuarioRepository cliente = new UsuarioRepository();
		
		carrinho.setId(Integer.parseInt(resultado.getString("id")));
		carrinho.setFormaDePagamento(formaDePagamento.consultarPorId(resultado.getInt("id_forma_pagamento")));
		if(resultado.getInt("id_cliente") != 0){
			carrinho.setCliente(cliente.consultarPorId(resultado.getInt("id_cliente")));			
		}
		carrinho.setData(resultado.getDate("data_compra").toLocalDate());
		carrinho.setTotal(resultado.getDouble("total"));
	}
	
	public String preencherFiltros(CarrinhoFiltro filtro) {
		String query = "";
		
		boolean primeiro = true;
		
		if(filtro.getDataInicial() != null && filtro.getDataFinal() != null) {
			if(primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += "c.data BETWEEN '" + filtro.getDataInicial() + "' AND '" + filtro.getDataFinal() + "'";
			primeiro = false;
		}
		
		if(filtro.getFormaDePagamento() != null) {
			if(primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += "UPPER(fp.nome) LIKE UPPER('%" + filtro.getFormaDePagamento() + "%')";
			primeiro = false;
		}
		
		if(filtro.getCliente() != null) {
			if(primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += "u.nome COLLATE utf8mb4_general_ci LIKE '%" + filtro.getCliente() + "%'";
			primeiro = false;
		}
		return query;
	}
	
	public int contarRegistros(CarrinhoFiltro filtro) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);		
		int qtdeRegistros = 0;
		String query = "SELECT COUNT(c.id) FROM carrinho c inner join usuario u on c.id_cliente = u.id "
				+ "inner join forma_pagamento fp on c.id_forma_pagamento = fp.id ";
		
		query += preencherFiltros(filtro);
		
		try {
			ResultSet resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				qtdeRegistros = resultado.getInt(1);
			}
		} catch(SQLException erro) {
			System.out.println("Erro ao buscar carrinhos");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return qtdeRegistros;
	}
	
	public int totalPaginas(CarrinhoFiltro filtro) {
		int limite = filtro.getLimite();
		int totalRegistros = contarRegistros(filtro);
		int qtdPaginas = totalRegistros / limite;
		int resto = totalRegistros % limite;
		
		if(resto > 0) {
			qtdPaginas++;
		}
		return qtdPaginas;
	}
}
