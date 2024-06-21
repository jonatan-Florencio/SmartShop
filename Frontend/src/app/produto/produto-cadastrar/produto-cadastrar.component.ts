import { Component, OnInit } from '@angular/core';
import { Produto } from '../../shared/model/produto';
import { Categoria } from '../../shared/model/categoria';
import { Marca } from '../../shared/model/marca';
import { ProdutoService } from '../../shared/service/produto.service';
import { CategoriaService } from '../../shared/service/categoria.service';
import { MarcaService } from '../../shared/service/marca.service';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-produto-cadastrar',
  templateUrl: './produto-cadastrar.component.html',
  styleUrl: './produto-cadastrar.component.scss'
})
export class ProdutoCadastrarComponent implements OnInit {

  public produto: Produto = new Produto();
  public idProduto: number;
  public categorias: Array<Categoria> = new Array();
  public marcas: Array<Marca> = new Array();

  constructor(private produtoService: ProdutoService, private categoriaService: CategoriaService, private marcaService: MarcaService,
              private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
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

    this.route.params.subscribe((params) => {
      this.idProduto = params['id'];
      if(this.idProduto) {
        this.buscar();
      }
    })
  }

  salvar(): void {
    if(this.idProduto) {
      this.atualizar();
    } else {
      this.cadastrar();
    }
  }

  cadastrar() {
    this.produtoService.salvar(this.produto).subscribe(
      resposta => {
        Swal.fire('Produto cadastrado com sucesso!', '', 'success');
      },
      erro => {
        Swal.fire('Erro ao cadastrar produto', erro, 'error');
      }
    )
  }

  atualizar(): void {
    this.produtoService.atualizar(this.produto).subscribe(
      (resposta) => {
        Swal.fire('Produto atualizado com sucesso!', '', 'success');
      },
      (erro) => {
        Swal.fire('Erro ao atualizar o produto: ' + erro, 'error');
        console.log(erro);
      }
    )
  }

  buscar(): void {
    this.produtoService.buscar(this.idProduto).subscribe(
      (produto) => {
        this.produto = produto;
      },
      (erro) => {
        Swal.fire('Erro ao buscar o produto!', erro, 'error');
      }
   )
  }

  voltar(): void {
      this.router.navigate(['/produto/listar']);
  }

  public compareById(r1: any, r2: any): boolean {
    return r1 && r2 ? r1.id === r2.id : r1 === r2;
  }
}
