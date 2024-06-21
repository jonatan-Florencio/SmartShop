package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Categoria;
import model.entity.Produto;
import model.entity.filtros.ProdutoFiltro;
import service.CategoriaService;
import service.MarcaService;

public class ProdutoRepository implements BaseRepository<Produto>{
	
	public Categoria categoria = new Categoria();

	@Override
	public Produto salvar(Produto novoProduto) {
		String sql = "INSERT INTO produto (nome, id_marca, codigo_de_barras, preco, estoque, id_categoria) "
				+ " VALUES(?,?,?,?,?,?) ";
		Connection conexao = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conexao, sql);
		
		try {
			pstmt.setString(1, novoProduto.getNome());
			pstmt.setInt(2, novoProduto.getMarca().getId());
			pstmt.setString(3, novoProduto.getCodigoDeBarras());
			pstmt.setDouble(4, novoProduto.getPreco());
			pstmt.setInt(5, novoProduto.getEstoque());
			pstmt.setInt(6, novoProduto.getCategoria().getId());
			
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();

			if (resultado.next()) {
				novoProduto.setId(resultado.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar novo produto");
			System.out.println("Erro " + e.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conexao);
		}
		
		return novoProduto;
	}

	@Override
	public Boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM produto WHERE id = " + id;
		try {
			if (stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir produto");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}
	
	@Override
	public Boolean alterar(Produto produtoEditado) {
	    boolean alterou = false;
	    String query = " UPDATE produto "
	                 + " SET nome=?, id_marca=?, codigo_de_barras=?, preco=?, estoque=?, id_categoria=? "
	                 + " WHERE id=? ";
	    Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
	    try {
	        pstmt.setString(1, produtoEditado.getNome());
	        pstmt.setInt(2, produtoEditado.getMarca().getId());
	        pstmt.setString(3, produtoEditado.getCodigoDeBarras());
	        pstmt.setDouble(4, produtoEditado.getPreco());
	        pstmt.setInt(5, produtoEditado.getEstoque());
	        pstmt.setInt(6, produtoEditado.getCategoria().getId());
	        pstmt.setInt(7, produtoEditado.getId());

	        alterou = pstmt.executeUpdate() > 0;
	    } catch (SQLException erro) {
	        System.out.println("Erro ao atualizar produto");
	        System.out.println("Erro: " + erro.getMessage());
	    } finally {
	        Banco.closePreparedStatement(pstmt);
	        Banco.closeConnection(conn);
	    }
	    return alterou;
	}

	@Override
	public Produto consultarPorId(int id) {
	    Connection conn = Banco.getConnection();
	    Statement stmt = Banco.getStatement(conn);
	    
	    Produto produto = null;
	    ResultSet resultado = null;
	    String query = "SELECT * FROM produto WHERE id = " + id;
	    
	    try {
	        resultado = stmt.executeQuery(query);
	        
	        if (resultado.next()) {
	        	MarcaRepository marca = new MarcaRepository();
		        CategoriaRepository categoria = new CategoriaRepository();
		        
	            produto = new Produto();
	            produto.setId(resultado.getInt("ID"));
	            produto.setNome(resultado.getString("NOME"));
	            produto.setMarca(marca.consultarPorId(resultado.getInt("ID_MARCA")));
	            produto.setCodigoDeBarras(resultado.getString("CODIGO_DE_BARRAS"));
	            produto.setPreco(resultado.getDouble("PRECO"));
	            produto.setEstoque(resultado.getInt("ESTOQUE"));
	            produto.setCategoria(categoria.consultarPorId(resultado.getInt("ID_CATEGORIA")));
	        }
	    } catch (SQLException erro) {
	        System.out.println("Erro ao consultar produto com o id: " + id);
	        System.out.println("Erro: " + erro.getMessage());
	    } finally {
	        Banco.closeResultSet(resultado);
	        Banco.closeStatement(stmt);
	        Banco.closeConnection(conn);
	    }
	    return produto;
	}

	@Override
	public ArrayList<Produto> consultarTodos() {
	    ArrayList<Produto> produtos = new ArrayList<>();
	    Connection conn = Banco.getConnection();
	    Statement stmt = Banco.getStatement(conn);
	    
	    ResultSet resultado = null;
	    String query = "SELECT * FROM produto";
	    
	    try {
	        resultado = stmt.executeQuery(query);
	       	        
	        while (resultado.next()) {
	            Produto produto = new Produto();

	            preencherParametrosParaBuscarOuListarTodas(resultado, produto);
	            
	            produtos.add(produto);
	        }
	    } catch (SQLException erro) {
	        System.out.println("Erro ao consultar todos os produtos");
	        System.out.println("Erro: " + erro.getMessage());
	    } finally {
	        Banco.closeResultSet(resultado);
	        Banco.closeStatement(stmt);
	        Banco.closeConnection(conn);
	    }
	    return produtos;
	}
	
	public ArrayList<Produto> buscarComFiltro(ProdutoFiltro filtro) {
		ArrayList<Produto> produtos = new ArrayList<Produto>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);		
		ResultSet resultado = null;
		String query = "SELECT p.* FROM produto p inner join categoria c on p.id_categoria = c.id "
				+ "inner join marca m on p.id_marca = m.id ";
		
		query += preencherFiltros(filtro);
		
		if(filtro.temPaginacao()) {
			query += " LIMIT " + filtro.getLimite() + " OFFSET " + filtro.getOffset();
		}
		
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				Produto produto = new Produto();
				preencherParametrosParaBuscarOuListarTodas(resultado, produto);
				produtos.add(produto);
			}
		} catch(SQLException erro) {
			System.out.println("Erro ao buscar produtos");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}		
		return produtos;
	}
	
	public void preencherParametrosParaBuscarOuListarTodas(ResultSet resultado, Produto produto) throws SQLException {
		CategoriaService categoria = new CategoriaService();
		MarcaService marca = new MarcaService();
		
		produto.setId(resultado.getInt("ID"));
        produto.setNome(resultado.getString("NOME"));
        produto.setMarca(marca.consultarPorId(resultado.getInt("ID_MARCA")));
        produto.setCodigoDeBarras(resultado.getString("CODIGO_DE_BARRAS"));
        produto.setPreco(resultado.getDouble("PRECO"));
        produto.setEstoque(resultado.getInt("ESTOQUE"));
        produto.setCategoria(categoria.consultarPorId(resultado.getInt("ID_CATEGORIA")));
	}
	
	public String preencherFiltros(ProdutoFiltro filtro) {
		String query = "";
		
		boolean primeiro = true;
		
		if(filtro.getNome() != null) {
			if(primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += "UPPER(p.nome) LIKE UPPER('%" + filtro.getNome() + "%')";
			primeiro = false;
		}
		
		if(filtro.getCodigoDeBarras() != null) {
			if(primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += "p.codigo_de_barras COLLATE utf8mb4_general_ci LIKE '%" + filtro.getCodigoDeBarras() + "%'";
			primeiro = false;
		}
		
		if(filtro.getMarca() != null) {
			if(primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += "UPPER(m.nome) LIKE UPPER('%" + filtro.getMarca() + "%')";
			primeiro = false;
		}
		
		if(filtro.getCategoria() != null) {
			if(primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += "UPPER(c.nome) LIKE UPPER('%" + filtro.getCategoria() + "%')";
			primeiro = false;
		}
		
		if(filtro.getPrecoMinimo() != null && filtro.getPrecoMaximo() != null) {
			if(primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += "p.preco BETWEEN " + filtro.getPrecoMinimo() + " AND " + filtro.getPrecoMaximo();
			primeiro = false;
		}
		
		if(filtro.getEstoqueMinimo() != null && filtro.getEstoqueMaximo() != null) {
			if(primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += "p.estoque BETWEEN " + filtro.getEstoqueMinimo() + " AND " + filtro.getEstoqueMaximo();
			primeiro = false;
		}
		return query;
	}
	
	public int contarRegistros(ProdutoFiltro filtro) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);		
		int qtdeRegistros = 0;
		String query = "SELECT COUNT(p.id) FROM produto p inner join categoria c on p.id_categoria = c.id "
				+ "inner join marca m on p.id_marca = m.id ";
		
		query += preencherFiltros(filtro);
		
		try {
			ResultSet resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				qtdeRegistros = resultado.getInt(1);
			}
		} catch(SQLException erro) {
			System.out.println("Erro ao buscar produtos");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return qtdeRegistros;
	}
	
	public int totalPaginas(ProdutoFiltro filtro) {
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