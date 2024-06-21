package controller;

import java.util.ArrayList;
import java.util.List;

import exception.SmartshopException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entity.Categoria;
import model.entity.filtros.CategoriaFiltro;
import service.CategoriaService;

@Path("/categoria")
public class CategoriaController {
	
	private CategoriaService service = new CategoriaService();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Categoria salvar(Categoria novaCategoria) throws SmartshopException {
		return service.salvar(novaCategoria);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/alterar")
	public Boolean atualizar(Categoria categoriaEditada) {
		return service.atualizar(categoriaEditada);
	}
	
	@DELETE
	@Path("/excluir/{id}")
	public Boolean excluir(@PathParam("id") int id) throws SmartshopException{
		return service.excluir(id);
	}
	
	@GET
	@Path("/buscar/{id}")
	public Categoria consultarPorId(@PathParam("id") int id) {
		return service.consultarPorId(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/filtrar")
	public ArrayList<Categoria> buscarComFiltro(CategoriaFiltro filtro) {
		return service.buscarComFiltro(filtro);
	}
	
	@GET
	@Path("/todos")
	public List<Categoria> consultarTodas(){
		return service.consultarTodas();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/contarRegistros")
	public int contarRegistros(CategoriaFiltro filtro) {
		return service.contarRegistros(filtro);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/totalPaginas")
	public int totalPaginas(CategoriaFiltro filtro) {
		return service.totalPaginas(filtro);
	}
}