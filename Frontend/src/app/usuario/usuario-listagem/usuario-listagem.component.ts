import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../shared/model/usuario';
import { UsuarioService } from '../../shared/service/usuario.service';
import { UsuarioFiltro } from '../../shared/model/filtros/usuario.filtro';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { ApartamentoService } from '../../shared/service/apartamento.service';
import { BlocoService } from '../../shared/service/bloco.service';
import { Apartamento } from '../../shared/model/apartamento';
import { Bloco } from '../../shared/model/bloco';

@Component({
  selector: 'app-usuario-listagem',
  templateUrl: './usuario-listagem.component.html',
  styleUrls: ['./usuario-listagem.component.scss']
})
export class UsuarioListagemComponent implements OnInit {
  public usuarios: Usuario[] = [];
  public apartamentos: Array<Apartamento> = new Array();
  public blocos: Array<Bloco> = new Array();
  public usuario: Usuario = new Usuario();
  public filtro: UsuarioFiltro = new UsuarioFiltro();
  public totalPaginas: number;
  public registroInicial: number;
  public registroFinal: number;
  public qtdeRegistros: number;
  public tipoUsuario = ['ADMINISTRADOR', 'CLIENTE'];

  constructor(
    private usuarioService: UsuarioService,
    private apartamentoService: ApartamentoService,
    private blocoService: BlocoService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.filtro.pagina = 1;
    this.filtro.limite = 5;

    this.consultarTodos();

    this.apartamentoService.consultarTodos().subscribe(
      resultado => {
        this.apartamentos = resultado;
      },
      erro => {
        console.log('Erro ao buscar apartamenos' + erro)
      }
    );

    this.blocoService.consultarTodos().subscribe(
      resultado => {
        this.blocos = resultado;
      },
      erro => {
        console.log('Erro ao buscar blocos' + erro)
      }
    );
  }

  public limparFiltro(campo: string): void {
    (this.filtro as any)[campo] = null;
    this.filtro.pagina = 1;
    this.pesquisar();
  }

  public async pesquisar(): Promise<void> {
    this.usuarioService.consultarComFiltro(this.filtro).subscribe(
      (resultado: Usuario[]) => {
        this.usuarios = resultado;
        this.mostrarRegistros();
      },
      erro => {
        console.error('Erro ao consultar usuários', erro);
      }
    );
  }

  private consultarTodos(): void {
    this.usuarioService.consultarTodos().subscribe(
      (resultado: Usuario[]) => {
        this.usuarios = resultado;
        this.qtdeRegistros = resultado.length;
        this.contarPaginas();
        this.mostrarRegistros();
      },
      erro => {
        Swal.fire('Erro ao consultar todos os usuários', erro, 'error');
      }
    );
  }

  contarRegistros(): void {
      this.usuarioService.contarRegistro(this.filtro).subscribe(
        (resultado: number) => {
          this.qtdeRegistros = resultado;
          this.contarPaginas();
        },
        erro => {
          console.error('Erro ao contar usuários: ', erro);
        }
      );
  }

  contarPaginas(): void {
    this.totalPaginas = Math.ceil(this.qtdeRegistros / this.filtro.limite);
    this.mostrarRegistros();
  }

  mostrarRegistros(): void {
    this.registroInicial = (this.filtro.pagina - 1) * this.filtro.limite + 1;
    this.registroFinal = this.filtro.pagina * this.filtro.limite;
    if (this.registroFinal > this.qtdeRegistros) {
      this.registroFinal = this.qtdeRegistros;
    }
  }

  public pesquisarClick(): void {
    this.filtro.pagina = 1;
    this.pesquisar();
  }

  atualizarPaginacao(): void {
    this.contarRegistros();
    this.pesquisar();
  }

  public limpar(): void {
    this.filtro = new UsuarioFiltro();
    this.filtro.limite = 5;
    this.consultarTodos();
  }

  excluir(usuario: Usuario): void {
    Swal.fire({
      title: 'Deseja realmente excluir o usuário?',
      text: 'Essa ação não poderá ser desfeita!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Confirmar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.value) {
        this.usuarioService.excluir(usuario.id).subscribe(
          (resposta) => {
            Swal.fire('Usuário excluído com sucesso!', '', 'success');
            this.consultarTodos();
          },
          (erro) => {
            Swal.fire(erro.error.mensagem, '', 'error');
            console.log(erro);
          }
        );
      }
    });
  }

  consultarPaginaAnterior(): void {
    if (this.filtro.pagina > 1) {
      this.filtro.pagina--;
      this.pesquisar();
    }
  }

  consultarProximaPagina(): void {
    if (this.filtro.pagina < this.totalPaginas) {
      this.filtro.pagina++;
      this.pesquisar();
    }
  }

  editar(usuario: Usuario): void {
    console.log('Editando usuário com ID:', usuario.id);
    this.router.navigate(['usuario/usuario-cadastro/' + usuario.id]);
  }

  cadastrar(): void {
    this.router.navigate(['usuario/usuario-cadastro']);
  }

  trackByUsuario(index: number, usuario: Usuario): number {
    return usuario.id;
  }
}
