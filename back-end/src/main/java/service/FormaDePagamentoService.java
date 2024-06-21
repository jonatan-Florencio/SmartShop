package service;

import java.util.ArrayList;

import model.entity.FormaDePagamento;
import model.repository.FormaDePagamentoRepository;

public class FormaDePagamentoService {

	FormaDePagamentoRepository formaDePagamentoRepository = new FormaDePagamentoRepository();
	
	public FormaDePagamento salvar(FormaDePagamento novaFormaDePagamento) {		
		return formaDePagamentoRepository.salvar(novaFormaDePagamento);
	}
	
	public Boolean alterar(FormaDePagamento formaDePagamento) {	
		return formaDePagamentoRepository.alterar(formaDePagamento);
	}
	
	public ArrayList<FormaDePagamento> consultarTodos() {
		return formaDePagamentoRepository.consultarTodos();
	}
	
	public FormaDePagamento consultarPorId(int id) {
		return formaDePagamentoRepository.consultarPorId(id);
	}
	
	public Boolean excluir(int id) {
		return formaDePagamentoRepository.excluir(id);
	}
}