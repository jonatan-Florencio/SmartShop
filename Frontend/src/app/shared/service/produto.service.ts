import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Produto } from '../model/produto';
import { Observable } from 'rxjs';
import { ProdutoFiltro } from '../model/filtros/produto.filtro';

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {

  constructor(private httpClient: HttpClient) { }

  private readonly API: string = 'http://localhost:8080/SmartShop/rest/produto';

  salvar(produto: Produto) {
    return this.httpClient.post<Produto>(this.API, produto)
  }

  atualizar(produto: Produto): Observable<boolean> {
    return this.httpClient.put<boolean>(this.API + "/alterar", produto);
  }

  consultarTodas(): Observable<Array<Produto>> {
    return this.httpClient.get<Array<Produto>>(this.API + '/todos');
  }

  consultarComFiltro(filtro: ProdutoFiltro): Observable<Array<Produto>> {
    return this.httpClient.post<Array<Produto>>(this.API + '/filtrar', filtro);
  }

  buscar(id: number): Observable<Produto> {
    return this.httpClient.get<Produto>(this.API + "/buscar/" + id);
  }

  buscarPorCodigo(codigoDeBarras: string): Observable<Produto> {
    return this.httpClient.get<Produto>(this.API + "/buscarprod/" + codigoDeBarras);
  }

  excluir(id: number): Observable<boolean> {
    return this.httpClient.delete<boolean>(this.API + "/excluir/" + id);
  }

  contarRegistro(filtro: ProdutoFiltro): Observable<number> {
    return this.httpClient.post<number>(this.API + "/contarRegistros", filtro)
  }

  contarPaginas(filtro: ProdutoFiltro): Observable<number> {
    return this.httpClient.post<number>(this.API + "/totalPaginas", filtro)
  }
}
