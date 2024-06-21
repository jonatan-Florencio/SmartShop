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
import model.entity.Produto;
import model.entity.filtros.ProdutoFiltro;
import service.ProdutoService;

@Path("/produto")
public class ProdutoController {
    
    private ProdutoService service = new ProdutoService();
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Produto salvar(Produto novoProduto) {
        return service.salvar(novoProduto);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/alterar")
    public boolean atualizar(Produto produtoEditado) {
        return service.atualizar(produtoEditado);
    }
    
    @DELETE
    @Path("/excluir/{id}")
    public boolean excluir(@PathParam("id") int id) throws SmartshopException {
        return service.excluir(id);
    }
    
    @GET
    @Path("/buscar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Produto consultarPorId(@PathParam("id") int id) {
        return service.consultarPorId(id);
    }
    
    @GET
    @Path("/todos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Produto> consultarTodos() {
        return service.consultarTodos();
    }
    
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/filtrar")
	public ArrayList<Produto> buscarComFiltro(ProdutoFiltro filtro) {
		return service.buscarComFiltro(filtro);
	}
    
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/contarRegistros")
	public int contarRegistros(ProdutoFiltro filtro) {
		return service.contarRegistros(filtro);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/totalPaginas")
	public int totalPaginas(ProdutoFiltro filtro) {
		return service.totalPaginas(filtro);
	}
}
