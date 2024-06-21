package service;

import java.util.ArrayList;

import model.entity.Carrinho;
import model.entity.Item;
import model.entity.filtros.CarrinhoFiltro;
import model.repository.CarrinhoRepository;

public class CarrinhoService {
	
	private CarrinhoRepository carrinhoRepository = new CarrinhoRepository();

	public Carrinho salvar(Carrinho novoCarrinho) {
		return carrinhoRepository.salvar(novoCarrinho);
	}
	
	public Boolean atualizarTotal(Item item) {
		return carrinhoRepository.atualizarTotal(item);
	}
	
	public Boolean atualizarFormaPagamento(Carrinho carrinho) {
		return carrinhoRepository.atualizarFormaPagamento(carrinho);
	}
	
	public ArrayList<Carrinho> consultarTodos() {
		return carrinhoRepository.consultarTodos();
	}
	
	public Carrinho consultarPorId(int id) {
		return carrinhoRepository.consultarPorId(id);
	}
	
	public Boolean excluir(int id) {
		return carrinhoRepository.excluir(id);
	}
	
	public ArrayList<Carrinho> buscarComFiltro(CarrinhoFiltro filtro) {
		return carrinhoRepository.buscarComFiltro(filtro);
	}
    
    public int contarRegistros(CarrinhoFiltro filtro) {
		return carrinhoRepository.contarRegistros(filtro);
	}
	
	public int totalPaginas(CarrinhoFiltro filtro) {
		return carrinhoRepository.totalPaginas(filtro);
	}
}