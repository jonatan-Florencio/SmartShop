import { Component, OnInit } from '@angular/core';
import { Item } from '../../shared/model/item';
import { ItemService } from '../../shared/service/item.service';
import { Produto } from '../../shared/model/produto';
import { ProdutoService } from '../../shared/service/produto.service';
import { ProdutoFiltro } from '../../shared/model/filtros/produto.filtro';
import Swal from 'sweetalert2';
import { CarrinhoService } from '../../shared/service/carrinho.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-item-registrar',
  templateUrl: './item-registrar.component.html',
  styleUrl: './item-registrar.component.scss'
})
export class ItemRegistrarComponent implements OnInit {

  public item: Item = new Item();
  public produto: Produto = new Produto();
  public idCarrinho: number;
  public codigoDeBarras: string;
  public itens: Array<Item> = new Array();

  constructor(private itemService: ItemService, private produtoService: ProdutoService, private carrinhoService: CarrinhoService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.idCarrinho = Number(this.route.snapshot.paramMap.get('idCarrinho'));
    this.item.idCarrinho = this.idCarrinho;
  }

  public pesquisarProduto() {
    this.produtoService.buscarPorCodigo(this.codigoDeBarras).subscribe(
      resultado => {
        this.item.produto = resultado;
        this.item.preco = resultado.preco;
        this.registrar();
      },
      erro => {
        console.error('Erro ao buscar produto', erro);
      }
    )
  }

  atualizarTotalCarrinho(item: Item) {
    this.carrinhoService.atualizarTotal(this.item).subscribe(
      resposta => {
        if (resposta) {
          console.log('Total do carrinho atualizado com sucesso');
        } else {
          console.error('Erro ao atualizar total do carrinho');
        }
      },
      erro => {
        console.error('Erro ao atualizar total do carrinho', erro);
        Swal.fire('Erro ao atualizar total do carrinho', erro.message, 'error');
      }
    );
  }

  registrar() {
    this.itemService.salvar(this.item).subscribe(
      resposta => {
        Swal.fire('Produto adicionado com sucesso!', '', 'success');
      },
      erro => {
        Swal.fire('Erro ao adicionar produto', erro, 'error');
      }
    );
    this.atualizarTotalCarrinho(this.item);
    this.limpar();
    this.pesquisar();
  }

  public pesquisar() {
    this.itemService.consultarTodos(this.idCarrinho).subscribe(
      resultado => {
        this.itens = resultado;
      },
      erro => {
        console.error('Erro ao consultar produtos do carrinho', erro);
      }
    )
  }

  public limpar() {
    this.codigoDeBarras = '';
    this.produto = new Produto();
    this.item = new Item();
    this.item.idCarrinho = this.idCarrinho;
  }
}
