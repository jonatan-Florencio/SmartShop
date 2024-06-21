package model.entity.filtros;

public class ProdutoFiltro extends BaseFiltro {

	private String nome;
	private String marca;
	private String codigoDeBarras;
	private Double precoMinimo;
	private Double precoMaximo;
	private Integer estoqueMinimo;
	private Integer estoqueMaximo;
	private String categoria;
	
	public ProdutoFiltro() {
		super();
	}

	public ProdutoFiltro(String nome, String marca, String codigoDeBarras, Double precoMinimo, Double precoMaximo,
			Integer estoqueMinimo, Integer estoqueMaximo, String categoria) {
		super();
		this.nome = nome;
		this.marca = marca;
		this.codigoDeBarras = codigoDeBarras;
		this.precoMinimo = precoMinimo;
		this.precoMaximo = precoMaximo;
		this.estoqueMinimo = estoqueMinimo;
		this.estoqueMaximo = estoqueMaximo;
		this.categoria = categoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	public Double getPrecoMinimo() {
		return precoMinimo;
	}

	public void setPrecoMinimo(Double precoMinimo) {
		this.precoMinimo = precoMinimo;
	}

	public Double getPrecoMaximo() {
		return precoMaximo;
	}

	public void setPrecoMaximo(Double precoMaximo) {
		this.precoMaximo = precoMaximo;
	}

	public Integer getEstoqueMinimo() {
		return estoqueMinimo;
	}

	public void setEstoqueMinimo(Integer estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}

	public Integer getEstoqueMaximo() {
		return estoqueMaximo;
	}

	public void setEstoqueMaximo(Integer estoqueMaximo) {
		this.estoqueMaximo = estoqueMaximo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
}
