import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Categoria } from '../model/categoria';
import { Observable } from 'rxjs';
import { CategoriaFiltro } from '../model/filtros/categoria.filtro';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  constructor(private httpClient: HttpClient) { }

  private readonly API: string = 'http://localhost:8080/SmartShop/rest/categoria';

  salvar(categoria: Categoria) {
    return this.httpClient.post<Categoria>(this.API, categoria)
  }

  atualizar(categoria: Categoria): Observable<boolean> {
    return this.httpClient.put<boolean>(this.API + "/alterar", categoria);
  }

  consultarTodas(): Observable<Array<Categoria>> {
    return this.httpClient.get<Array<Categoria>>(this.API + '/todos');
  }

  consultarComFiltro(filtro: CategoriaFiltro): Observable<Array<Categoria>> {
    return this.httpClient.post<Array<Categoria>>(this.API + '/filtrar', filtro);
  }

  buscar(id: number): Observable<Categoria> {
    return this.httpClient.get<Categoria>(this.API + "/buscar/" + id);
  }

  excluir(id: number): Observable<boolean> {
    return this.httpClient.delete<boolean>(this.API + "/excluir/" + id);
  }

  contarRegistro(filtro: CategoriaFiltro): Observable<number> {
    return this.httpClient.post<number>(this.API + "/contarRegistros", filtro)
  }

  contarPaginas(filtro: CategoriaFiltro): Observable<number> {
    return this.httpClient.post<number>(this.API + "/totalPaginas", filtro)
  }
}