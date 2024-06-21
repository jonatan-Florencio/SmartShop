package controller;

import java.util.ArrayList;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entity.Carrinho;
import model.entity.Item;
import model.entity.filtros.CarrinhoFiltro;
import service.CarrinhoService;

@Path("/carrinho")
public class CarrinhoController {
	
	private CarrinhoService carrinhoService = new CarrinhoService();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Carrinho salvar(Carrinho novoCarrinho) {
		return carrinhoService.salvar(novoCarrinho);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/total")
	public Boolean atualizarTotal(Item item) {	
		return carrinhoService.atualizarTotal(item);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/pagamento")
	public Boolean atualizarFormaPagamento(Carrinho carrinho) {	
		return carrinhoService.atualizarFormaPagamento(carrinho);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/todos")
	public ArrayList<Carrinho> consultarTodos() {
		return carrinhoService.consultarTodos();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/buscar/{ id }")
	public Carrinho consultarPorId(@PathParam("id") int id) {
		return carrinhoService.consultarPorId(id);
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/excluir/{ id }")
	public Boolean excluir(@PathParam("id") int id) {
		return carrinhoService.excluir(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/filtrar")
	public ArrayList<Carrinho> buscarComFiltro(CarrinhoFiltro filtro) {
		return carrinhoService.buscarComFiltro(filtro);
	}
    
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/contarRegistros")
	public int contarRegistros(CarrinhoFiltro filtro) {
		return carrinhoService.contarRegistros(filtro);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/totalPaginas")
	public int totalPaginas(CarrinhoFiltro filtro) {
		return carrinhoService.totalPaginas(filtro);
	}
}
