import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../shared/service/usuario.service';
import { Usuario } from '../../shared/model/usuario';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { ApartamentoService } from '../../shared/service/apartamento.service';
import { BlocoService } from '../../shared/service/bloco.service';
import { Apartamento } from '../../shared/model/apartamento';
import { Bloco } from '../../shared/model/bloco';

@Component({
  selector: 'app-usuario-cadastro',
  templateUrl: './usuario-cadastro.component.html',
  styleUrls: ['./usuario-cadastro.component.scss']
})
export class UsuarioCadastroComponent implements OnInit {
  public usuario: Usuario = new Usuario();
  public idUsuario: number;
  public tiposUsuario: string[] = ['ADMINISTRADOR', 'CLIENTE'];
  public apartamentos: Array<Apartamento> = [];
  public blocos: Array<Bloco> = [];

  constructor(
    private usuarioService: UsuarioService,
    private router: Router,
    private route: ActivatedRoute,
    private apartamentoService: ApartamentoService,
    private blocoService: BlocoService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.idUsuario = params['id'];
      if (this.idUsuario) {
        this.carregarUsuario(this.idUsuario);
      }
    });

    this.apartamentoService.consultarTodos().subscribe(
      resultado => {
        this.apartamentos = resultado;
      },
      erro => {
        console.log('Erro ao buscar apartamentos', erro);
      }
    );

    this.blocoService.consultarTodos().subscribe(
      resultado => {
        this.blocos = resultado;
      },
      erro => {
        console.log('Erro ao buscar blocos', erro);
      }
    );
  }

  carregarUsuario(id: number): void {
    this.usuarioService.buscar(id).subscribe(
      (usuario: Usuario) => {
        this.usuario = usuario;
      },
      erro => {
        Swal.fire('Erro', 'Não foi possível carregar os dados do usuário', 'error');
      }
    );
  }

  salvar(): void {
    if (this.idUsuario) {
      this.usuarioService.atualizar(this.usuario).subscribe(
        () => {
          Swal.fire('Sucesso', 'Usuário atualizado com sucesso', 'success');
          this.router.navigate(['/usuario/usuario-listagem']);
        },
        erro => {
          Swal.fire('Erro', 'Não foi possível atualizar o usuário', 'error');
        }
      );
    } else {
      this.usuarioService.salvar(this.usuario).subscribe(
        () => {
          Swal.fire('Sucesso', 'Usuário cadastrado com sucesso', 'success');
          this.router.navigate(['/usuario/usuario-listagem']);
        },
        erro => {
          Swal.fire('Erro', 'Não foi possível cadastrar o usuário', 'error');
        }
      );
    }
  }

  voltar(): void {
    this.router.navigate(['/usuario/usuario-listagem']);
  }
}
