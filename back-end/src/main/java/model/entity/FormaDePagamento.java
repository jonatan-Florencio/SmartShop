package model.entity;

import model.entity.enums.TipoFormaPagamento;

public class FormaDePagamento {

	private int id;
	private TipoFormaPagamento tipoFormaPagamento;
	
	public FormaDePagamento() {
		super();
	}

	public FormaDePagamento(int id, TipoFormaPagamento tipoFormaPagamento) {
		super();
		this.id = id;
		this.tipoFormaPagamento = tipoFormaPagamento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TipoFormaPagamento getTipoFormaPagamento() {
		return tipoFormaPagamento;
	}

	public void setTipoFormaPagamento(TipoFormaPagamento tipoFormaPagamento) {
		this.tipoFormaPagamento = tipoFormaPagamento;
	}

}