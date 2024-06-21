package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Item;

public class ItemRepository implements BaseRepository<Item> {

	@Override
	public Item salvar(Item novoItem) {
		String query = "INSERT INTO item (id_carrinho, id_produto, preco, quantidade) VALUES (?, ?, ?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			preencherParametrosParaInsertOuUpdate(pstmt, novoItem);
			
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			
			if(resultado.next()) {
				novoItem.setId(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao adicionar item.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return novoItem;
	}
	
	@Override
	public Boolean alterar(Item item) {
		boolean retorno = false;
		String query = "UPDATE item SET id_carrinho=?, id_produto=?, preco=?, quantidade=? WHERE id=?";		 
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		
		try {					 
			preencherParametrosParaInsertOuUpdate(pstmt, item);
			pstmt.setInt(5, item.getId());
			retorno = pstmt.executeUpdate() > 0;
		} catch(SQLException erro) {
			System.out.println("Não foi possível atualizar item.");
			System.out.println("Erro " + erro.getMessage());
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}
	
	@Override
	public ArrayList<Item> consultarTodos() {
		ArrayList<Item> listaItems = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM item";
		
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				Item item = new Item();
				
				preencherParametrosParaListarOuBuscar(resultado, item);				
				listaItems.add(item);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao executar consultar todos os itens.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaItems;
	}
	
	@Override
	public Item consultarPorId(int id) {
		Item item = new Item();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = "SELECT * FROM item WHERE id = " + id;
		
		try{
			resultado = stmt.executeQuery(query);
			
			if(resultado.next()) {
				preencherParametrosParaListarOuBuscar(resultado, item);		
			}
						
		} catch (SQLException erro){
				System.out.println("Erro ao executar a busca por id.");
				System.out.println("Erro: " + erro.getMessage());
		} finally {
				Banco.closeResultSet(resultado);
				Banco.closeStatement(stmt);
				Banco.closeConnection(conn);
		}
		return item;
	}
	
	@Override
	public Boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM item WHERE id = " + id;
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir item");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}
	
	private void preencherParametrosParaInsertOuUpdate(PreparedStatement pstmt
			, Item novoItem) throws SQLException {
		
		pstmt.setInt(1, novoItem.getIdCarrinho());
		pstmt.setInt(2, novoItem.getProduto().getId());
		pstmt.setDouble(3, novoItem.getProduto().getPreco());
		pstmt.setInt(4, novoItem.getQuantidade());
	}
	
	private void preencherParametrosParaListarOuBuscar(ResultSet resultado
			, Item item) throws SQLException {
		
		ProdutoRepository produto = new ProdutoRepository();
		
		item.setId(resultado.getInt("id"));
		item.setIdCarrinho(resultado.getInt("id_carrinho"));
		item.setProduto(produto.consultarPorId(resultado.getInt("id_produto")));
		item.setPreco(resultado.getDouble("preco"));
		item.setQuantidade(resultado.getInt("quantidade"));
	}
}
