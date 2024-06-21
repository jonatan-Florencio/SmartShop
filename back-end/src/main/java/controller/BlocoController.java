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
import model.entity.Bloco;
import service.BlocoService;

@Path("/bloco")
public class BlocoController {

	BlocoService blocoService = new BlocoService();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Bloco salvar(Bloco novaBloco) {
		return blocoService.salvar(novaBloco);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/alterar")
	public Boolean alterar(Bloco formaDePagamento) {	
		return blocoService.alterar(formaDePagamento);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/todos")
	public ArrayList<Bloco> consultarTodos() {
		return blocoService.consultarTodos();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/buscar/{ id }")
	public Bloco consultarPorId(@PathParam("id") int id) {
		return blocoService.consultarPorId(id);
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/excluir/{ id }")
	public Boolean excluir(@PathParam("id") int id) {
		return blocoService.excluir(id);
	}
}
