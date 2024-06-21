import { Component, OnInit } from '@angular/core';
import { Carrinho } from '../../shared/model/carrinho';
import Swal from 'sweetalert2';
import { CarrinhoService } from '../../shared/service/carrinho.service';
import { ActivatedRoute, Router } from '@angular/router';
import { UsuarioService } from '../../shared/service/usuario.service';
import { Usuario } from '../../shared/model/usuario';

@Component({
  selector: 'app-carrinho-cadastrar',
  templateUrl: './carrinho-cadastrar.component.html',
  styleUrl: './carrinho-cadastrar.component.scss'
})
export class CarrinhoCadastrarComponent implements OnInit {

  public carrinho: Carrinho = new Carrinho();
  public idCarrinho: number;
  public usuario: Usuario = new Usuario();

  constructor(private carrinhoService: CarrinhoService, private usuarioService: UsuarioService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.iniciarCarrinho();
  }

  registrarItem(idCarrinho: number) {
    this.router.navigate(['/item/cadastrar/', idCarrinho]);
  }

  salvar() {
    this.carrinhoService.salvar(this.carrinho).subscribe(
      resposta => {
        this.registrarItem(resposta.id);
      },
      erro => {
        Swal.fire('Erro ao iniciar compra', erro, 'error');
      }
    )
  }

  verificarCpf(cpf: string) {
    this.usuarioService.buscarCpf(cpf).subscribe(
      resultado => {
        if(resultado != null) {
          this.usuario = resultado;
          this.carrinho.cliente = this.usuario;
          this.salvar();
        } else {
          Swal.fire({
            title: 'CPF não cadastrado',
            text: 'Deseja cadastrar o CPF?',
            showCancelButton: true,
            confirmButtonText: 'SIM',
            cancelButtonText: 'NÃO'
          }).then((result) => {
            if (result.isConfirmed) {
              this.router.navigate(['/usuario/usuario-cadastro']);
            } else {
              this.salvar();
            }
          } )
        }
      },
      erro => {
        Swal.fire('Erro ao buscar cpf', erro.error.mensagem, 'error');
      }
    );
  }

  async informarCpf() {
    const { value: cpf } = await Swal.fire({
      title: 'Informe o CPF',
      input: 'text',
      inputPlaceholder: 'Digite o CPF',
      showCancelButton: true,
      // confirmButtonColor: '#3085d6',
      // cancelButtonColor: '#d33',
      confirmButtonText: 'Confirmar',
      cancelButtonText: 'Cancelar'
    });

    if (cpf) {
      this.verificarCpf(cpf);
    } else {
      this.salvar();
    }
  }

  iniciarCarrinho() {
    Swal.fire({
      title: 'Deseja informar o CPF?',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Sim',
      cancelButtonText: 'Não'
    }).then((result) => {
      if (result.isConfirmed) {
        this.informarCpf();
      } else {
        this.salvar();
      }
    });
  }

  // iniciarCarrinho() {
  //   Swal.fire({
  //     title: 'Deseja informar o CPF?',
  //     showCancelButton: true,
  //     confirmButtonText: 'SIM',
  //     cancelButtonText: 'NÃO'
  //   }).then((result) => {
  //     if(result.value) {
  //       this.solicitarCpf();
  //     } else {
  //       this.salvar();
  //     }
  //   })
  // }
}
