import { Component, OnInit } from '@angular/core';
import { Marca } from '../../shared/model/marca';
import { MarcaFiltro } from '../../shared/model/filtros/marca.filtro';
import { MarcaService } from '../../shared/service/marca.service';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-marca-cadastrar-listar',
  templateUrl: './marca-cadastrar-listar.component.html',
  styleUrl: './marca-cadastrar-listar.component.scss'
})
export class MarcaCadastrarListarComponent implements OnInit {

  public marca: Marca = new Marca();
  public filtro: MarcaFiltro = new MarcaFiltro();
  public marcas: Array<Marca> = new Array();
  public qtdeRegistros: number;
  public totalPaginas: number;
  public registroInicial: number;
  public registroFinal: number;
  public pesquisouComFiltro: boolean;

  constructor(private marcaService: MarcaService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.filtro.pagina = 1;
    this.filtro.limite = 5;
    this.pesquisar();
    this.contarRegistros();
  }

  public pesquisar() {
    this.marcaService.consultarComFiltro(this.filtro).subscribe(
      resultado => {
        this.marcas = resultado;
      },
      erro => {
        console.error('Erro ao consultar marcas', erro);
      }
    )
  }

  contarRegistros() {
		this.marcaService.contarRegistro(this.filtro).subscribe(
      resultado => {
        this.qtdeRegistros = resultado;
        this.contarPaginas();
      },
      erro => {
        console.error('Erro ao contar marcas: ', erro);
      }
    )
	}

  contarPaginas() {
		this.marcaService.contarPaginas(this.filtro).subscribe(
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
    this.filtro = new MarcaFiltro();
    this.filtro.limite = 5;
  }

  excluir(marca: Marca) {
    Swal.fire({
      title: 'Deseja realmente excluir a marca?',
      text: 'Essa ação não poderá ser desfeita!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Confirmar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if(result.value) {
        this.marcaService.excluir(marca.id).subscribe(
          (resposta) => {
            Swal.fire('Marca excluída com sucesso!', '', 'success');
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
    this.marca.nome = this.filtro.nome;
    this.marcaService.salvar(this.marca).subscribe(
      resposta => {
        Swal.fire('Marca cadastrada com sucesso!', '', 'success');
      },
      erro => {
        Swal.fire('Erro ao cadastrar marca', erro, 'error');
      }
    )
    this.limpar();
    this.pesquisarClick();
  }

  trocarEditMode(marca: Marca) {
    marca.editMode = !marca.editMode;
  }

  atualizar(marca: Marca) {
    this.marcaService.atualizar(marca).subscribe(
      resposta => {
        Swal.fire('Marca atualizada com sucesso!', '', 'success');
        marca.editMode = false;
        this.pesquisar();
      },
      erro => {
        Swal.fire('Erro ao atualizar marca', erro, 'error');
      }
    )
  }

  cancelarEdicao(marca: Marca) {
    marca.editMode = false;
    this.pesquisar();
  }

  trackByMarca(index: number, marca: Marca): number {
    return marca.id;
  }
}
