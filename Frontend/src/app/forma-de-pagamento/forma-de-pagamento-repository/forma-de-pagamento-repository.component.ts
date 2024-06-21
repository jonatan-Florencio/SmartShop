import { FormaDePagamentoService } from './../../shared/service/formaDePagamento.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormaDePagamento } from '../../shared/model/formaDePagamento';

@Component({
  selector: 'app-forma-de-pagamento-repository',
  templateUrl: './forma-de-pagamento-repository.component.html',
  styleUrls: ['./forma-de-pagamento-repository.component.scss']
})
export class FormaDePagamentoRepositoryComponent implements OnInit {
  public formaDePagamentos: FormaDePagamento[] = [];
  public formaDePagamento: FormaDePagamento = new FormaDePagamento();
  public tipoFormaPagamento = ['CRÉDITO', 'DÉBITO', 'PIX'];

  constructor(
    private formaDePagamentoService: FormaDePagamentoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.consultarTodos();
  }

  public consultarTodos(): void {
    this.formaDePagamentoService.consultarTodos().subscribe(
      (resultado: FormaDePagamento[]) => {
        this.formaDePagamentos = resultado;
      },
      erro => {
        console.error('Erro ao consultar todas as formas de pagamento', erro);
      }
    );
  }

  public buscarPorId(id: number): void {
    this.formaDePagamentoService.buscar(id).subscribe(
      (resultado: FormaDePagamento) => {
        this.formaDePagamento = resultado;
      },
      erro => {
        console.error('Erro ao buscar forma de pagamento por id', erro);
      }
    );
  }
}
