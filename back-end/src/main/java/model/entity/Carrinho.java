package model.entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class Carrinho {

    private int id;
    private LocalDate data;
    private double total;
    private Usuario cliente;
    private FormaDePagamento formaDePagamento;
    private ArrayList<Item> itens;
    
	public Carrinho() {
		super();
	}

	public Carrinho(int id, LocalDate data, double total, Usuario cliente, FormaDePagamento formaDePagamento,
			ArrayList<Item> itens) {
		super();
		this.id = id;
		this.data = data;
		this.total = total;
		this.cliente = cliente;
		this.formaDePagamento = formaDePagamento;
		this.itens = itens;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public FormaDePagamento getFormaDePagamento() {
		return formaDePagamento;
	}

	public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public ArrayList<Item> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Item> itens) {
		this.itens = itens;
	}	    
}