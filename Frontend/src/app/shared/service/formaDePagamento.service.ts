import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { FormaDePagamento } from "../model/formaDePagamento";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class FormaDePagamentoService{

  private readonly API = 'http://localhost:8080/SmartShop/rest/formaPagamento';

  constructor(
    private httpClient: HttpClient
  ) { }

  consultarTodos(): Observable<Array<FormaDePagamento>> {
    return this.httpClient.get<Array<FormaDePagamento>>(this.API + '/todos')
  }

  buscar(id: number): Observable<FormaDePagamento> {
    return this.httpClient.get<FormaDePagamento>(`${this.API}/buscar/${id}`);
  }

}