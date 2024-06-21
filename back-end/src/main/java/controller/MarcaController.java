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
import model.entity.Marca;
import model.entity.filtros.MarcaFiltro;
import service.MarcaService;

@Path("/marca")
public class MarcaController {
	
	private MarcaService service = new MarcaService();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Marca salvar(Marca novaMarca) throws SmartshopException {
		return service.salvar(novaMarca);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/alterar")
	public Boolean atualizar(Marca marcaEditada) {
		return service.atualizar(marcaEditada);
	}
	
	@DELETE
	@Path("/excluir/{id}")
	public Boolean excluir(@PathParam("id") int id) throws SmartshopException{
		return service.excluir(id);
	}
	
	@GET
	@Path("/buscar/{id}")
	public Marca consultarPorId(@PathParam("id") int id) {
		return service.consultarPorId(id);
	}
	
	@GET
	@Path("/todos")
	public List<Marca> consultarTodas(){
		return service.consultarTodas();
	}	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/filtrar")
	public ArrayList<Marca> buscarComFiltro(MarcaFiltro filtro) {
		return service.buscarComFiltro(filtro);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/contarRegistros")
	public int contarRegistros(MarcaFiltro filtro) {
		return service.contarRegistros(filtro);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/totalPaginas")
	public int totalPaginas(MarcaFiltro filtro) {
		return service.totalPaginas(filtro);
	}
}