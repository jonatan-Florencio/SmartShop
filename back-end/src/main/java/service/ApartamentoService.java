package service;

import java.util.ArrayList;

import model.entity.Apartamento;
import model.repository.ApartamentoRepository;

public class ApartamentoService {

	ApartamentoRepository apartamentoRepository = new ApartamentoRepository();
	
	public Apartamento salvar(Apartamento novaApartamento) {		
		return apartamentoRepository.salvar(novaApartamento);
	}
	
	public Boolean alterar(Apartamento formaDePagamento) {	
		return apartamentoRepository.alterar(formaDePagamento);
	}
	
	public ArrayList<Apartamento> consultarTodos() {
		return apartamentoRepository.consultarTodos();
	}
	
	public Apartamento consultarPorId(int id) {
		return apartamentoRepository.consultarPorId(id);
	}
	
	public Boolean excluir(int id) {
		return apartamentoRepository.excluir(id);
	}
}