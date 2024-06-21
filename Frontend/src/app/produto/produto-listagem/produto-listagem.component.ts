import { ProdutoService } from './../../shared/service/produto.service';
import { Component, OnInit } from '@angular/core';
import { Produto } from '../../shared/model/produto';
import { ProdutoFiltro } from '../../shared/model/filtros/produto.filtro';
import { Categoria } from '../../shared/model/categoria';
import { Marca } from '../../shared/model/marca';
import { CategoriaService } from '../../shared/service/categoria.service';
import { MarcaService } from '../../shared/service/marca.service';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-produto-listagem',
  templateUrl: './produto-listagem.component.html',
  styleUrl: './produto-listagem.component.scss'
})

export class ProdutoListagemComponent implements OnInit {

  public produtos: Array<Produto> = new Array();
  public produto: Produto = new Produto();
  public filtro: ProdutoFiltro = new ProdutoFiltro();
  public categorias: Array<Categoria> = new Array();
  public marcas: Array<Marca> = new Array();
  public qtdeRegistros: number;
  public totalPaginas: number;
  public registroInicial: number;
  public registroFinal: number;
  public pesquisouComFiltro: boolean;


  constructor(private produtoService: ProdutoService, private categoriaService: CategoriaService, private marcaService: MarcaService,
              private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {

    this.filtro.pagina = 1;
    this.filtro.limite = 5;
    this.pesquisar();
    this.contarRegistros();

    this.categoriaService.consultarTodas().subscribe(
        resultado => {
          this.categorias = resultado;
        },
        erro => {
          console.log('Erro ao buscar categorias' + erro)
        }
      );

      this.marcaService.consultarTodas().subscribe(
        resultado => {
          this.marcas = resultado;
        },
        erro => {
          console.log('Erro ao buscar marcas' + erro)
        }
      );
  }

  public pesquisar() {
    this.produtoService.consultarComFiltro(this.filtro).subscribe(
      resultado => {
        this.produtos = resultado;
      },
      erro => {
        console.error('Erro ao consultar produtos', erro);
      }
    )
  }

  contarRegistros() {
		this.produtoService.contarRegistro(this.filtro).subscribe(
      resultado => {
        this.qtdeRegistros = resultado;
        this.contarPaginas();
      },
      erro => {
        console.error('Erro ao contar produtos: ', erro);
      }
    )
	}

  contarPaginas() {
		this.produtoService.contarPaginas(this.filtro).subscribe(
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
    this.filtro = new ProdutoFiltro();
    this.filtro.limite = 5;
  }

  editar(idProduto: number) {
    this.router.navigate(['/produto/cadastrar/', idProduto]);
  }

  excluir(produto: Produto) {
    Swal.fire({
      title: 'Deseja realmente excluir o produto?',
      text: 'Essa ação não poderá ser desfeita!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Confirmar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if(result.value) {
        this.produtoService.excluir(produto.id).subscribe(
          (resposta) => {
            Swal.fire('Produto excluído com sucesso!', '', 'success');
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
