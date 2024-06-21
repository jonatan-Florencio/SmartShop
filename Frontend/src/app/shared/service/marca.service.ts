import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Marca } from '../model/marca';
import { Observable } from 'rxjs';
import { MarcaFiltro } from '../model/filtros/marca.filtro';

@Injectable({
  providedIn: 'root'
})
export class MarcaService {

  constructor(private httpClient: HttpClient) { }

  private readonly API: string = 'http://localhost:8080/SmartShop/rest/marca';

  salvar(marca: Marca) {
    return this.httpClient.post<Marca>(this.API, marca)
  }

  atualizar(marca: Marca): Observable<boolean> {
    return this.httpClient.put<boolean>(this.API + "/alterar", marca);
  }

  consultarTodas(): Observable<Array<Marca>> {
    return this.httpClient.get<Array<Marca>>(this.API + '/todos');
  }

  consultarComFiltro(filtro: MarcaFiltro): Observable<Array<Marca>> {
    return this.httpClient.post<Array<Marca>>(this.API + '/filtrar', filtro);
  }

  buscar(id: number): Observable<Marca> {
    return this.httpClient.get<Marca>(this.API + "/buscar/" + id);
  }

  excluir(id: number): Observable<boolean> {
    return this.httpClient.delete<boolean>(this.API + "/excluir/" + id);
  }

  contarRegistro(filtro: MarcaFiltro): Observable<number> {
    return this.httpClient.post<number>(this.API + "/contarRegistros", filtro)
  }

  contarPaginas(filtro: MarcaFiltro): Observable<number> {
    return this.httpClient.post<number>(this.API + "/totalPaginas", filtro)
  }
}
