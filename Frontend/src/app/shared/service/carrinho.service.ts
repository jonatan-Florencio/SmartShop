import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Carrinho } from '../model/carrinho';
import { CarrinhoFiltro } from '../model/filtros/carrinho.filtro';
import { Item } from '../model/item';

@Injectable({
  providedIn: 'root'
})
export class CarrinhoService {
  constructor(private httpClient: HttpClient) { }

  private readonly API: string = 'http://localhost:8080/SmartShop/rest/carrinho';

  salvar(carrinho: Carrinho): Observable<Carrinho> {
    return this.httpClient.post<Carrinho>(this.API, carrinho);
  }

  atualizarTotal(item: Item): Observable<boolean> {
    return this.httpClient.put<boolean>(this.API + "/total", item);
  }

  consultarTodas(): Observable<Array<Carrinho>> {
    return this.httpClient.get<Array<Carrinho>>(this.API + '/todos');
  }

  consultarComFiltro(filtro: CarrinhoFiltro): Observable<Array<Carrinho>> {
    return this.httpClient.post<Array<Carrinho>>(this.API + '/filtrar', filtro);
  }

  buscar(id: number): Observable<Carrinho> {
    return this.httpClient.get<Carrinho>(this.API + "/buscar/" + id);
  }

  excluir(id: number): Observable<boolean> {
    return this.httpClient.delete<boolean>(this.API + "/excluir/" + id);
  }

  contarRegistro(filtro: CarrinhoFiltro): Observable<number> {
    return this.httpClient.post<number>(this.API + "/contarRegistros", filtro);
  }

  contarPaginas(filtro: CarrinhoFiltro): Observable<number> {
    return this.httpClient.post<number>(this.API + "/totalPaginas", filtro);
  }

  atualizarFormaPagamento(carrinho: Carrinho): Observable<Carrinho> {
    return this.httpClient.put<Carrinho>(`${this.API}/atualizarFormaPagamento`, carrinho);
  }
}
