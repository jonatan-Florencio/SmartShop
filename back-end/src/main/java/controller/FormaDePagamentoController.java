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
import model.entity.FormaDePagamento;
import service.FormaDePagamentoService;

@Path("/formaPagamento")
public class FormaDePagamentoController {

	FormaDePagamentoService formaDePagamentoService = new FormaDePagamentoService();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public FormaDePagamento salvar(FormaDePagamento novaFormaDePagamento) {
		return formaDePagamentoService.salvar(novaFormaDePagamento);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/alterar")
	public Boolean alterar(FormaDePagamento formaDePagamento) {	
		return formaDePagamentoService.alterar(formaDePagamento);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/todos")
	public ArrayList<FormaDePagamento> consultarTodos() {
		return formaDePagamentoService.consultarTodos();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/buscar/{ id }")
	public FormaDePagamento consultarPorId(@PathParam("id") int id) {
		return formaDePagamentoService.consultarPorId(id);
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/excluir/{ id }")
	public Boolean excluir(@PathParam("id") int id) {
		return formaDePagamentoService.excluir(id);
	}
}