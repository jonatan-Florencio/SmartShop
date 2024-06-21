package model.entity.filtros;

public class MarcaFiltro extends BaseFiltro {

	private String nome;

	public MarcaFiltro() {
		super();
	}

	public MarcaFiltro(String nome) {
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