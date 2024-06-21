package model.entity.filtros;

import model.entity.enums.TipoUsuario;

public class UsuarioFiltro extends BaseFiltro{
	
	private String nome;
	private String cpf;
	private String telefone;
	private String email;
	private Integer idBloco;
	private Integer idApartamento;
	private TipoUsuario tipoUsuario;

	public UsuarioFiltro() {
		super();	
	}

	public UsuarioFiltro(String nome, String cpf, String telefone, String email, Integer idBloco, Integer idApartamento,
			TipoUsuario tipoUsuario) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.idBloco = idBloco;
		this.idApartamento = idApartamento;
		this.tipoUsuario = tipoUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIdBloco() {
		return idBloco;
	}

	public void setIdBloco(Integer idBloco) {
		this.idBloco = idBloco;
	}

	public Integer getIdApartamento() {
		return idApartamento;
	}

	public void setIdApartamento(Integer idApartamento) {
		this.idApartamento = idApartamento;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	
	
}