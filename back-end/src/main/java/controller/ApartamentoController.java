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
import model.entity.Apartamento;
import service.ApartamentoService;

@Path("/apartamento")
public class ApartamentoController {

	ApartamentoService apartamentoService = new ApartamentoService();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Apartamento salvar(Apartamento novaApartamento) {
		return apartamentoService.salvar(novaApartamento);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/alterar")
	public Boolean alterar(Apartamento formaDePagamento) {	
		return apartamentoService.alterar(formaDePagamento);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/todos")
	public ArrayList<Apartamento> consultarTodos() {
		return apartamentoService.consultarTodos();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/buscar/{ id }")
	public Apartamento consultarPorId(@PathParam("id") int id) {
		return apartamentoService.consultarPorId(id);
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/excluir/{ id }")
	public Boolean excluir(@PathParam("id") int id) {
		return apartamentoService.excluir(id);
	}
}