import { Categoria } from './../../shared/model/categoria';
import { CategoriaFiltro } from './../../shared/model/filtros/categoria.filtro';
import { Component, OnInit } from '@angular/core';
import { CategoriaService } from '../../shared/service/categoria.service';
import Swal from 'sweetalert2';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-categoria-cadastrar-listar',
  templateUrl: './categoria-cadastrar-listar.component.html',
  styleUrl: './categoria-cadastrar-listar.component.scss'
})
export class CategoriaCadastrarListarComponent implements OnInit {

  public categoria: Categoria = new Categoria();
  public filtro: CategoriaFiltro = new CategoriaFiltro();
  public categorias: Array<Categoria> = new Array();
  public qtdeRegistros: number;
  public totalPaginas: number;
  public registroInicial: number;
  public registroFinal: number;
  public pesquisouComFiltro: boolean;

  constructor(private categoriaService: CategoriaService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.filtro.pagina = 1;
    this.filtro.limite = 5;
    this.pesquisar();
    this.contarRegistros();
  }

  public pesquisar() {
    this.categoriaService.consultarComFiltro(this.filtro).subscribe(
      resultado => {
        this.categorias = resultado;
      },
      erro => {
        console.error('Erro ao consultar categorias', erro);
      }
    )
  }

  contarRegistros() {
		this.categoriaService.contarRegistro(this.filtro).subscribe(
      resultado => {
        this.qtdeRegistros = resultado;
        this.contarPaginas();
      },
      erro => {
        console.error('Erro ao contar categorias: ', erro);
      }
    )
	}

  contarPaginas() {
		this.categoriaService.contarPaginas(this.filtro).subscribe(
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
    this.filtro = new CategoriaFiltro();
    this.filtro.limite = 5;
  }

  excluir(categoria: Categoria) {
    Swal.fire({
      title: 'Deseja realmente excluir a categoria?',
      text: 'Essa ação não poderá ser desfeita!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Confirmar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if(result.value) {
        this.categoriaService.excluir(categoria.id).subscribe(
          (resposta) => {
            Swal.fire('Categoria excluída com sucesso!', '', 'success');
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

  salvar() {
    this.categoria.nome = this.filtro.nome;
    this.categoriaService.salvar(this.categoria).subscribe(
      resposta => {
        Swal.fire('Categoria cadastrada com sucesso!', '', 'success');
      },
      erro => {
        Swal.fire('Erro ao cadastrar categoria', erro, 'error');
      }
    )
    this.limpar();
    this.pesquisar();
  }

  trocarEditMode(categoria: Categoria) {
    categoria.editMode = !categoria.editMode;
  }

  atualizar(categoria: Categoria) {
    this.categoriaService.atualizar(categoria).subscribe(
      resposta => {
        Swal.fire('Categoria atualizada com sucesso!', '', 'success');
        categoria.editMode = false;
        this.pesquisar();
      },
      erro => {
        Swal.fire('Erro ao atualizar categoria', erro, 'error');
      }
    )
  }

  cancelarEdicao(categoria: Categoria) {
    categoria.editMode = false;
    this.pesquisar();
  }

  trackByCategoria(index: number, categoria: Categoria): number {
    return categoria.id;
  }
}