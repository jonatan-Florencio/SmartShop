import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./carrinho/carrinho.module').then(m => m .CarrinhoModule)
  },
  {
    path: 'adm',
    loadChildren: () => import('./administracao/administracao.module').then(m => m .AdministracaoModule)
  },
  {
    path: 'apartamento',
    loadChildren: () => import('./apartamento/apartamento.module').then(m => m .ApartamentoModule)
  },
  {
    path: 'bloco',
    loadChildren: () => import('./bloco/bloco.module').then(m => m .BlocoModule)
  },
  {
    path: 'carrinho',
    loadChildren: () => import('./carrinho/carrinho.module').then(m => m .CarrinhoModule)
  },
  {
    path: 'categoria',
    loadChildren: () => import('./categoria/categoria.module').then(m => m .CategoriaModule)
  },
  {
    path: 'forma-de-pagamento',
    loadChildren: () => import('./forma-de-pagamento/forma-de-pagamento.module').then(m => m .FormaDePagamentoModule)
  },
  {
    path: 'item',
    loadChildren: () => import('./item/item.module').then(m => m .ItemModule)
  },
  {
    path: 'marca',
    loadChildren: () => import('./marca/marca.module').then(m => m .MarcaModule)
  },
  {
    path: 'produto',
    loadChildren: () => import('./produto/produto.module').then(m => m .ProdutoModule)
  },
  {
    path: 'usuario',
    loadChildren: () => import('./usuario/usuario.module').then(m => m .UsuarioModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
