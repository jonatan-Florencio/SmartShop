package controller;

import java.util.ArrayList;
import java.util.List;

import exception.SmartshopException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.entity.Usuario;
import model.entity.filtros.UsuarioFiltro;
import service.UsuarioService;

@Path("/usuario")
public class UsuarioController {

    private UsuarioService service = new UsuarioService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario salvar(Usuario novoUsuario) throws SmartshopException {
        return service.salvar(novoUsuario);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/alterar")
    public Boolean atualizar(Usuario usuarioEditado) {
        return service.atualizar(usuarioEditado);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/excluir/{id}")
    public Boolean excluir(@PathParam("id") int id) throws SmartshopException {
        return service.excluir(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/buscar/{id}")
    public Usuario consultarPorId(@PathParam("id") int id) {
        return service.consultarPorId(id);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/buscarcpf/{cpf}")
    public Usuario consultarPorCpf(@PathParam("cpf") String cpf) {
    	return service.consultarPorCpf(cpf);
    }

    @GET
    @Path("/todos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> consultarTodos() {
        return service.consultarTodos();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/contarRegistros")
    public int contarRegistros(UsuarioFiltro filtro) {
        return service.contarRegistros(filtro);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/filtrar")
    public ArrayList<Usuario> buscarComFiltro(UsuarioFiltro filtro) {
        return service.buscarComFiltro(filtro);
    }

}
