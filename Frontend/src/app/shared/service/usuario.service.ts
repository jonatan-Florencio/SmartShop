import { Injectable } from "@angular/core";
import { Usuario } from '../model/usuario';
import { HttpClient } from '@angular/common/http';
import { Observable } from "rxjs";
import { UsuarioFiltro } from "../model/filtros/usuario.filtro";
import { Bloco } from "../model/bloco";
import { Apartamento } from "../model/apartamento";

@Injectable({
  providedIn: 'root'
})
export class UsuarioService{

  private readonly API = 'http://localhost:8080/SmartShop/rest/usuario';

  constructor(
    private httpClient: HttpClient
  ) { }

  consultarTodos(): Observable<Array<Usuario>> {
    return this.httpClient.get<Array<Usuario>>(this.API + '/todos');
  }

  salvar(usuario: Usuario): Observable<Usuario> {
    return this.httpClient.post<Usuario>(this.API, usuario);
  }

  atualizar(usuario: Usuario): Observable<boolean> {
    return this.httpClient.put<boolean>(this.API + "/alterar", usuario);
  }

  buscar(id: number): Observable<Usuario> {
    return this.httpClient.get<Usuario>(`${this.API}/buscar/${id}`);
  }

  buscarCpf(cpf: string): Observable<Usuario> {
    return this.httpClient.get<Usuario>(this.API + "/buscarcpf/" + cpf);
  }

  excluir(id: number): Observable<boolean> {
    return this.httpClient.delete<boolean>(this.API + "/excluir/" + id);
  }

  consultarComFiltro(filtro: UsuarioFiltro): Observable<Array<Usuario>> {
    return this.httpClient.post<Array<Usuario>>(`${this.API}/filtrar`, filtro);
  }

  contarRegistro(filtro: UsuarioFiltro): Observable<number> {
    return this.httpClient.post<number>(this.API + "/contarRegistros", filtro);
  }

  contarPaginas(filtro: UsuarioFiltro): Observable<number> {
    return this.httpClient.post<number>(this.API + "/totalPaginas", filtro);
  }

  consultarBlocoPorNome(nome: string): Observable<Bloco> {
    return this.httpClient.get<Bloco>(`${this.API}/bloco/nome/${nome}`);
  }

  consultarApartamentoPorNumero(numero: number): Observable<Apartamento> {
    return this.httpClient.get<Apartamento>(`${this.API}/apartamento/numero/${numero}`);
  }
}
