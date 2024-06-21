package model.entity;

public class Produto {

	private int id;
	private String nome;
	private Marca marca;
	private String codigoDeBarras;
	private double preco;
	private int estoque;
	private Categoria categoria;
	
	public Produto() {
		super();
	}

	public Produto(int id, String nome, Marca marca, String codigoDeBarras, double preco, int estoque,
			Categoria categoria) {
		super();
		this.id = id;
		this.nome = nome;
		this.marca = marca;
		this.codigoDeBarras = codigoDeBarras;
		this.preco = preco;
		this.estoque = estoque;
		this.categoria = categoria;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}	
}