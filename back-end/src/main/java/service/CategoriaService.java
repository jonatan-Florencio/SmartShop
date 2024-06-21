package service;

import java.util.ArrayList;
import java.util.List;

import exception.SmartshopException;
import model.entity.Categoria;
import model.entity.filtros.CategoriaFiltro;
import model.repository.CategoriaRepository;

public class CategoriaService {

	private CategoriaRepository repository = new CategoriaRepository();

	public Categoria salvar(Categoria novaCategoria) throws SmartshopException {
		if(repository.verificarCategoriaExiste(novaCategoria)) {
			throw new SmartshopException("Categoria " + novaCategoria.getNome() +" já existe, favor verificar.");
		} else {
			return repository.salvar(novaCategoria);			
		}
	}

	public boolean atualizar(Categoria categoriaEditada) {
		return repository.alterar(categoriaEditada);
	}

	public boolean excluir(int id) throws SmartshopException {
		return repository.excluir(id);
	}

	public Categoria consultarPorId(int id) {
		return repository.consultarPorId(id);
	}

	public List<Categoria> consultarTodas() {
		return repository.consultarTodos();
	}
	
	public ArrayList<Categoria> buscarComFiltro(CategoriaFiltro filtro) {
		return repository.buscarComFiltro(filtro);
	}
	
	public int contarRegistros(CategoriaFiltro filtro) {
		return repository.contarRegistros(filtro);
	}
	
	public int totalPaginas(CategoriaFiltro filtro) {
		return repository.totalPaginas(filtro);
	}
}