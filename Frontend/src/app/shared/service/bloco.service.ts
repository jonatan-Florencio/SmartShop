import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Bloco } from '../model/bloco';

@Injectable({
  providedIn: 'root'
})
export class BlocoService {
  private readonly API = 'http://localhost:8080/SmartShop/rest/bloco';

  constructor(private httpClient: HttpClient) {}

  consultarPorNome(nome: string): Observable<Bloco> {
    return this.httpClient.get<Bloco>(`${this.API}/buscarPorNome/${nome}`);
  }

  consultarTodos(): Observable<Array<Bloco>> {
    return this.httpClient.get<Array<Bloco>>(this.API + '/todos');
  }
}
