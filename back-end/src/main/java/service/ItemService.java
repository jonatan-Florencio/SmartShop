package service;

import java.util.ArrayList;

import model.entity.Item;
import model.repository.ItemRepository;

public class ItemService {

	private ItemRepository itemRepository = new ItemRepository();
	
	public Item salvar(Item novoItem) {
		return itemRepository.salvar(novoItem);
	}
	
	public Boolean alterar(Item item) {
		return itemRepository.alterar(item);
	}
	
	public ArrayList<Item> consultarTodos() {
		return itemRepository.consultarTodos();
	}
	
	public Item consultarPorId(int id) {
		return itemRepository.consultarPorId(id);
	}
	
	public Boolean excluir(int id) {
		return itemRepository.excluir(id);
	}
}