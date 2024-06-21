package service;

import java.util.ArrayList;

import model.entity.Bloco;
import model.repository.BlocoRepository;

public class BlocoService {

	BlocoRepository blocoRepository = new BlocoRepository();
	
	public Bloco salvar(Bloco novaBloco) {		
		return blocoRepository.salvar(novaBloco);
	}
	
	public Boolean alterar(Bloco formaDePagamento) {	
		return blocoRepository.alterar(formaDePagamento);
	}
	
	public ArrayList<Bloco> consultarTodos() {
		return blocoRepository.consultarTodos();
	}
	
	public Bloco consultarPorId(int id) {
		return blocoRepository.consultarPorId(id);
	}
	
	public Boolean excluir(int id) {
		return blocoRepository.excluir(id);
	}
}