import { Component, OnInit } from '@angular/core';
import { Carrinho } from '../../shared/model/carrinho';
import { CarrinhoFiltro } from '../../shared/model/filtros/carrinho.filtro';
import { CarrinhoService } from '../../shared/service/carrinho.service';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-carrinho-listagem',
  templateUrl: './carrinho-listagem.component.html',
  styleUrl: './carrinho-listagem.component.scss'
})

export class CarrinhoListagemComponent implements OnInit {

  public carrinhos: Array<Carrinho> = new Array();
  public carrinho: Carrinho = new Carrinho();
  public filtro: CarrinhoFiltro = new CarrinhoFiltro();
  // public formasDePagamento: Array<FormaDePagamento> = new Array();
  public qtdeRegistros: number;
  public totalPaginas: number;
  public registroInicial: number;
  public registroFinal: number;
  public pesquisouComFiltro: boolean;

  constructor(private carrinhoService: CarrinhoService, private router: Router, private route: ActivatedRoute) {}

    ngOnInit(): void {

      this.filtro.pagina = 1;
      this.filtro.limite = 5;
      this.pesquisar();
      this.contarRegistros();

      // this.formaDePagamentoService.consultarTodas().subscribe(
      //     resultado => {
      //       this.formasDePagamento = resultado;
      //     },
      //     erro => {
      //       console.log('Erro ao buscar Formas de pagamento' + erro)
      //     }
      //   );
    }

    public pesquisar() {
      this.carrinhoService.consultarComFiltro(this.filtro).subscribe(
        resultado => {
          this.carrinhos = resultado;
        },
        erro => {
          console.error('Erro ao consultar carrinhos', erro);
        }
      )
    }

    contarRegistros() {
      this.carrinhoService.contarRegistro(this.filtro).subscribe(
        resultado => {
          this.qtdeRegistros = resultado;
          this.contarPaginas();
        },
        erro => {
          console.error('Erro ao contar carrinhos: ', erro);
        }
      )
    }

    contarPaginas() {
      this.carrinhoService.contarPaginas(this.filtro).subscribe(
        resultado => {
          this.totalPaginas = resultado;
          this.mostrarRegistros();
        },
        erro => {
          Swal.fire('Erro ao contar páginas', erro.error.mensagem, 'error');
        }
      )
    }

    mostrarRegistros() {
      if(this.filtro.pagina == 1){
        this.registroInicial = 1;
      } else {
        this.registroInicial = 1 + ((this.filtro.pagina - 1) * this.filtro.limite);
      }
      if(this.filtro.pagina == this.totalPaginas) {
        this.registroFinal = this.qtdeRegistros;
      } else {
        this.registroFinal = this.filtro.pagina * this.filtro.limite;
      }
    }

    public pesquisarClick() {
      this.filtro.pagina = 1;
      this.pesquisar();
      this.contarRegistros();
    }

    atualizarPaginacao() {
      this.contarRegistros();
      this.pesquisar();
    }

    public limpar() {
      this.filtro = new CarrinhoFiltro();
      this.filtro.limite = 5;
    }

    editar(idCarrinho: number) {
      this.router.navigate(['/carrinho/cadastrar/', idCarrinho]);
    }

    excluir(carrinho: Carrinho) {
      Swal.fire({
        title: 'Deseja realmente excluir o carrinho?',
        text: 'Essa ação não poderá ser desfeita!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Confirmar',
        cancelButtonText: 'Cancelar'
      }).then((result) => {
        if(result.value) {
          this.carrinhoService.excluir(carrinho.id).subscribe(
            (resposta) => {
              Swal.fire('carrinho excluído com sucesso!', '', 'success');
            },
            (erro) => {
              Swal.fire(erro.error.mensagem, '', 'error');
              console.log(erro);
            }
          )
        }
      })
    }

    consultarPaginaAnterior() {
      this.filtro.pagina--;
      this.contarRegistros();
      this.pesquisar();
    }

    consultarProximaPagina() {
      this.filtro.pagina++;
      this.contarRegistros();
      this.pesquisar();
    }
}
