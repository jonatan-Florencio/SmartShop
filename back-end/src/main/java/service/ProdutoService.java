package service;

import java.util.ArrayList;
import java.util.List;

import exception.SmartshopException;
import model.entity.Produto;
import model.entity.filtros.ProdutoFiltro;
import model.repository.ProdutoRepository;

public class ProdutoService {

    private ProdutoRepository repository = new ProdutoRepository();

    public Produto salvar(Produto novoProduto) {
        return repository.salvar(novoProduto);
    }

    public boolean atualizar(Produto produtoEditado) {
        return repository.alterar(produtoEditado);
    }

    public boolean excluir(int id) throws SmartshopException {
        return repository.excluir(id);
    }

    public Produto consultarPorId(int id) {
        return repository.consultarPorId(id);
    }

    public List<Produto> consultarTodos() {
        return repository.consultarTodos();
    }
    
    public ArrayList<Produto> buscarComFiltro(ProdutoFiltro filtro) {
		return repository.buscarComFiltro(filtro);
	}
    
    public int contarRegistros(ProdutoFiltro filtro) {
		return repository.contarRegistros(filtro);
	}
	
	public int totalPaginas(ProdutoFiltro filtro) {
		return repository.totalPaginas(filtro);
	}
}
