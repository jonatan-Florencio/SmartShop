package service;

import java.util.ArrayList;
import java.util.List;

import exception.SmartshopException;
import model.entity.Apartamento;
import model.entity.Bloco;
import model.entity.Usuario;
import model.entity.filtros.UsuarioFiltro;
import model.repository.UsuarioRepository;

public class UsuarioService {

    private UsuarioRepository repository = new UsuarioRepository();

    public Usuario salvar(Usuario novoUsuario) throws SmartshopException {
        if (repository.verificarUsuarioExiste(novoUsuario)) {
            throw new SmartshopException("Usuário com o email " + novoUsuario.getEmail() + " já existe.");
        } else {
            return repository.salvar(novoUsuario);
        }
    }

    public boolean atualizar(Usuario usuarioEditado) {
        return repository.alterar(usuarioEditado);
    }

    public boolean excluir(int id) throws SmartshopException {
        return repository.excluir(id);
    }

    public Usuario consultarPorId(int id) {
        return repository.consultarPorId(id);
    }
    
    public Usuario consultarPorCpf(String cpf) {
    	return repository.consultarPorCpf(cpf);
    }

    public List<Usuario> consultarTodos() {
        return repository.consultarTodos();
    }

    public ArrayList<Usuario> buscarComFiltro(UsuarioFiltro filtro) {
        return repository.buscarComFiltro(filtro);
    }

    public int contarRegistros(UsuarioFiltro filtro) {
        return repository.contarRegistros(filtro);
    }


    public Bloco consultarBlocoPorNome(String nome) {
        return repository.consultarBlocoPorNome(nome);
    }

    public Apartamento consultarApartamentoPorNumero(int numero) {
        return repository.consultarApartamentoPorNumero(numero);
    }
}
