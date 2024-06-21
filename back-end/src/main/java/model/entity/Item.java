package model.entity;

public class Item {

    private int id;
    private int idCarrinho;
    private Produto produto;
    private double preco;
    private int quantidade;
    
	public Item() {
		super();
	}

	public Item(int id, int idCarrinho, Produto produto, double preco, int quantidade) {
		super();
		this.id = id;
		this.idCarrinho = idCarrinho;
		this.produto = produto;
		this.preco = preco;
		this.quantidade = quantidade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdCarrinho() {
		return idCarrinho;
	}

	public void setIdCarrinho(int idCarrinho) {
		this.idCarrinho = idCarrinho;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}    
}