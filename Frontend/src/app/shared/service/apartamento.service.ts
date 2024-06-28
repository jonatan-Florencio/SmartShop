import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Apartamento } from '../model/apartamento';

@Injectable({
  providedIn: 'root'
})
export class ApartamentoService {
  private readonly API = 'http://localhost:8080/rest/apartamento';

  constructor(private httpClient: HttpClient) {}

  consultarPorNumero(numero: number): Observable<Apartamento> {
    return this.httpClient.get<Apartamento>(`${this.API}/buscarPorNumero/${numero}`);
  }

  consultarTodos(): Observable<Array<Apartamento>> {
    return this.httpClient.get<Array<Apartamento>>(this.API + '/todos');
  }
}
