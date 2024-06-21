package model.entity.filtros;

import java.time.LocalDate;

public class CarrinhoFiltro extends BaseFiltro {

	private LocalDate dataInicial;
	private LocalDate dataFinal;
	private String formaDePagamento;
	private String cliente;
	
	public CarrinhoFiltro() {
		super();
	}

	public CarrinhoFiltro(LocalDate dataInicial, LocalDate dataFinal, String formaDePagamento, String cliente) {
		super();
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
		this.formaDePagamento = formaDePagamento;
		this.cliente = cliente;
	}

	public LocalDate getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(LocalDate dataInicial) {
		this.dataInicial = dataInicial;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getFormaDePagamento() {
		return formaDePagamento;
	}

	public void setFormaDePagamento(String formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
}
