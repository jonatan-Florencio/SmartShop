package service;

import java.util.ArrayList;
import java.util.List;

import exception.SmartshopException;
import model.entity.Marca;
import model.entity.filtros.MarcaFiltro;
import model.repository.MarcaRepository;

public class MarcaService {

	private MarcaRepository repository = new MarcaRepository();

	public Marca salvar(Marca novaMarca) throws SmartshopException {
		if(repository.verificarMarcaExiste(novaMarca)) {
			throw new SmartshopException("Marca " + novaMarca.getNome() +" já existe, favor verificar.");
		} else {
			return repository.salvar(novaMarca);			
		}
	}

	public boolean atualizar(Marca marcaEditada) {
		return repository.alterar(marcaEditada);
	}

	public boolean excluir(int id) throws SmartshopException {
		return repository.excluir(id);
	}

	public Marca consultarPorId(int id) {
		return repository.consultarPorId(id);
	}

	public List<Marca> consultarTodas() {
		return repository.consultarTodos();
	}
	
	public ArrayList<Marca> buscarComFiltro(MarcaFiltro filtro) {
		return repository.buscarComFiltro(filtro);
	}
	
	public int contarRegistros(MarcaFiltro filtro) {
		return repository.contarRegistros(filtro);
	}
	
	public int totalPaginas(MarcaFiltro filtro) {
		return repository.totalPaginas(filtro);
	}
}