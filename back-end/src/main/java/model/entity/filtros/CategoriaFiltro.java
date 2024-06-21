package model.entity.filtros;

public class CategoriaFiltro extends BaseFiltro {

	private String nome;

	public CategoriaFiltro() {
		super();
	}

	public CategoriaFiltro(String nome) {
		super();
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}