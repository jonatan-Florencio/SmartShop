import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Item } from '../model/item';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  constructor(private httpClient: HttpClient) { }

  private readonly API: string = 'http://localhost:8080/rest/item';

  salvar(item: Item) {
    return this.httpClient.post<Item>(this.API, item)
  }

  consultarTodos(idCarrinho: number): Observable<Array<Item>> {
    return this.httpClient.get<Array<Item>>(this.API + '/todos' + idCarrinho);
  }
}
