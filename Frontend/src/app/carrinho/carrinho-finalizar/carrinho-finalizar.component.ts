import { Component, OnInit } from '@angular/core';
import { Carrinho } from '../../shared/model/carrinho';
import { CarrinhoService } from '../../shared/service/carrinho.service';
import { FormaDePagamentoService } from '../../shared/service/formaDePagamento.service';
import { FormaDePagamento } from '../../shared/model/formaDePagamento';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-carrinho-finalizar',
  templateUrl: './carrinho-finalizar.component.html',
  styleUrls: ['./carrinho-finalizar.component.scss']
})
export class CarrinhoFinalizarComponent implements OnInit {
  public carrinho: Carrinho = new Carrinho();
  public formasDePagamento: Array<FormaDePagamento> = new Array();
  public formaSelecionada: FormaDePagamento = new FormaDePagamento();


  constructor(
    private carrinhoService: CarrinhoService,
    private formaDePagamentoService: FormaDePagamentoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.formaDePagamentoService.consultarTodos().subscribe(
      resultado => {
        this.formasDePagamento = resultado;
      }, erro => {
        console.error('Erro ao consultar formas de pagamento', erro);
      }
    );
  }

  finalizarCompra(): void {
    this.carrinho.formaDePagamento = this.formaSelecionada;
    this.carrinhoService.atualizarFormaPagamento(this.carrinho).subscribe(
      resposta => {
        Swal.fire('Compra finalizada com sucesso!', '', 'success');
        this.router.navigate(['/']);
      },
      erro => {
        Swal.fire('Erro ao finalizar compra', erro.message, 'error');
      }
    );
  }
}
