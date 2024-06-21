package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Apartamento;
import model.entity.Bloco;
import model.entity.Usuario;
import model.entity.enums.TipoUsuario;
import model.entity.filtros.UsuarioFiltro;

public class UsuarioRepository implements BaseRepository<Usuario> {

	@Override
	public Usuario salvar(Usuario novoUsuario) {
		String sql = "INSERT INTO usuario (nome, cpf, telefone, email, id_bloco, id_apartamento, tipo, senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		Connection conexao = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);

		try {
			stmt.setString(1, novoUsuario.getNome());
			stmt.setString(2, novoUsuario.getCpf());
			stmt.setString(3, novoUsuario.getTelefone());
			stmt.setString(4, novoUsuario.getEmail());
			stmt.setInt(5, novoUsuario.getBloco().getId());
			stmt.setInt(6, novoUsuario.getApartamento().getId());
			stmt.setString(7, novoUsuario.getTipoUsuario().toString());
			stmt.setString(8, novoUsuario.getSenha());

			stmt.execute();
			ResultSet resultado = stmt.getGeneratedKeys();
			if (resultado.next()) {
				novoUsuario.setId(resultado.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar novo usuário");
			System.out.println("Erro: " + e.getMessage());
		}

		return novoUsuario;
	}

	@Override
	public Boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM usuario WHERE id = " + id;
		try {
			if (stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir usuário");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public Boolean alterar(Usuario usuarioEditado) {
		boolean alterou = false;
		String query = "UPDATE usuario SET nome=?, cpf=?, telefone=?, email=?, id_bloco=?, id_apartamento=?, tipo=?, senha=? WHERE id=?";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		try {
			pstmt.setString(1, usuarioEditado.getNome());
			pstmt.setString(2, usuarioEditado.getCpf());
			pstmt.setString(3, usuarioEditado.getTelefone());
			pstmt.setString(4, usuarioEditado.getEmail());
			pstmt.setInt(5, usuarioEditado.getBloco().getId());
			pstmt.setInt(6, usuarioEditado.getApartamento().getId());
			pstmt.setString(7, usuarioEditado.getTipoUsuario().toString());
			pstmt.setString(8, usuarioEditado.getSenha());
			pstmt.setInt(9, usuarioEditado.getId());

			alterou = pstmt.executeUpdate() > 0;
		} catch (SQLException erro) {
			System.out.println("Erro ao atualizar usuário");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return alterou;
	}

	@Override
	public Usuario consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);

		Usuario usuario = null;
		ResultSet resultado = null;
		String query = "SELECT * FROM usuario WHERE id = " + id;

		try {
			resultado = stmt.executeQuery(query);

			if (resultado.next()) {
				usuario = new Usuario();
				preencherParametrosParaBuscarOuConsultarTodas(resultado, usuario);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar usuário com o id: " + id);
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return usuario;
	}

	public Usuario consultarPorCpf(String cpf) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		Usuario usuario = null;
		String query = "SELECT * FROM usuario WHERE cpf = '" + cpf + "'";
		try {
			resultado = stmt.executeQuery(query);

			if (resultado.next()) {
				usuario = new Usuario();
				preencherParametrosParaBuscarOuConsultarTodas(resultado, usuario);
			}
		} catch (SQLException erro) {
			System.out.println("\nErro ao executar a query do método verificarCpfExiste.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return usuario;
	}

	@Override
	public ArrayList<Usuario> consultarTodos() {
		ArrayList<Usuario> usuarios = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);

		ResultSet resultado = null;
		String query = "SELECT * FROM usuario";

		try {
			resultado = stmt.executeQuery(query);

			while (resultado.next()) {
				Usuario usuario = new Usuario();
				preencherParametrosParaBuscarOuConsultarTodas(resultado, usuario);
				usuarios.add(usuario);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar todos os usuários");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return usuarios;
	}

	public ArrayList<Usuario> buscarComFiltro(UsuarioFiltro filtro) {
		ArrayList<Usuario> usuarios = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		String query = "SELECT * FROM usuario";

		ArrayList<String> clausulas = new ArrayList<>();

		if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
			clausulas.add("nome COLLATE utf8mb4_general_ci LIKE '%" + filtro.getNome() + "%'");
		}
		if (filtro.getCpf() != null && !filtro.getCpf().isEmpty()) {
			clausulas.add("cpf LIKE '%" + filtro.getCpf() + "%'");
		}
		if (filtro.getTelefone() != null && !filtro.getTelefone().isEmpty()) {
			clausulas.add("telefone LIKE '%" + filtro.getTelefone() + "%'");
		}
		if (filtro.getEmail() != null && !filtro.getEmail().isEmpty()) {
			clausulas.add("email LIKE '%" + filtro.getEmail() + "%'");
		}
		if (filtro.getIdBloco() != null) {
			clausulas.add("id_bloco =" + filtro.getIdBloco());
		}
		if (filtro.getIdApartamento() != null) {
			clausulas.add("id_apartamento=" + filtro.getIdApartamento());
		}
		if (filtro.getTipoUsuario() != null && filtro.getTipoUsuario().toString() != null
				&& !filtro.getTipoUsuario().toString().isEmpty()) {
			clausulas.add("tipo LIKE '%" + filtro.getTipoUsuario().toString() + "%'");
		}

		if (!clausulas.isEmpty()) {
			query += " WHERE " + String.join(" AND ", clausulas);
		}

		try {
			resultado = stmt.executeQuery(query);
			while (resultado.next()) {
				Usuario usuario = new Usuario();
				preencherParametrosParaBuscarOuConsultarTodas(resultado, usuario);
				usuarios.add(usuario);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao buscar usuários.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return usuarios;
	}

	public void preencherParametrosParaBuscarOuConsultarTodas(ResultSet resultado, Usuario usuario)
			throws SQLException {
		usuario.setId(resultado.getInt("ID"));
		usuario.setNome(resultado.getString("NOME"));
		usuario.setCpf(resultado.getString("CPF"));
		usuario.setTelefone(resultado.getString("TELEFONE"));
		usuario.setEmail(resultado.getString("EMAIL"));
		BlocoRepository blocoRepository = new BlocoRepository();
		Bloco bloco = blocoRepository.consultarPorId((resultado.getInt("ID_BLOCO")));
		usuario.setBloco(bloco);
		ApartamentoRepository apartamentoRepository = new ApartamentoRepository();
		Apartamento apartamento = apartamentoRepository.consultarPorId((resultado.getInt("ID_APARTAMENTO")));
		usuario.setApartamento(apartamento);
		usuario.setTipoUsuario(TipoUsuario.valueOf(resultado.getString("TIPO")));
		usuario.setSenha(resultado.getString("SENHA"));
	}

	public int contarRegistros(UsuarioFiltro filtro) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		int qtdeRegistros = 0;
		String query = "SELECT COUNT(usuario.id) FROM usuario";

		ArrayList<String> clausulas = new ArrayList<>();

		if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
			clausulas.add("nome COLLATE utf8mb4_general_ci LIKE '%" + filtro.getNome() + "%'");
		}
		if (filtro.getCpf() != null && !filtro.getCpf().isEmpty()) {
			clausulas.add("cpf LIKE '%" + filtro.getCpf() + "%'");
		}
		if (filtro.getTelefone() != null && !filtro.getTelefone().isEmpty()) {
			clausulas.add("telefone LIKE '%" + filtro.getTelefone() + "%'");
		}
		if (filtro.getEmail() != null && !filtro.getEmail().isEmpty()) {
			clausulas.add("email LIKE '%" + filtro.getEmail() + "%'");
		}
		if (filtro.getIdBloco() != null) {
			clausulas.add("id_bloco =" + filtro.getIdBloco());
		}
		if (filtro.getIdApartamento() != null) {
			clausulas.add("id_apartamento =" + filtro.getIdApartamento());
		}
		if (filtro.getTipoUsuario() != null && filtro.getTipoUsuario().toString() != null
				&& !filtro.getTipoUsuario().toString().isEmpty()) {
			clausulas.add("tipo LIKE '%" + filtro.getTipoUsuario().toString() + "%'");
		}

		if (!clausulas.isEmpty()) {
			query += " WHERE " + String.join(" AND ", clausulas);
		}

		try {
			ResultSet resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				qtdeRegistros = resultado.getInt(1);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao contar registros.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return qtdeRegistros;
	}

	public boolean verificarUsuarioExiste(Usuario usuario) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		String query = "SELECT * FROM usuario WHERE email COLLATE utf8mb4_general_ci LIKE '%" + usuario.getEmail()
				+ "%'";
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método verificarUsuarioExiste.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public Bloco consultarBlocoPorNome(String nome) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		Bloco bloco = null;
		String query = "SELECT * FROM bloco WHERE bloco COLLATE utf8mb4_general_ci LIKE '%" + nome + "%'";
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				bloco = new Bloco();
				bloco.setId(resultado.getInt("ID"));
				bloco.setBloco(resultado.getString("BLOCO"));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao buscar bloco por nome.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return bloco;
	}

	public Apartamento consultarApartamentoPorNumero(int numero) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		Apartamento apartamento = null;
		String query = "SELECT * FROM apartamento WHERE apartamento = " + numero;
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				apartamento = new Apartamento();
				apartamento.setId(resultado.getInt("ID"));
				apartamento.setApartamento(resultado.getInt("APARTAMENTO"));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao buscar apartamento por número.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return apartamento;
	}
}
