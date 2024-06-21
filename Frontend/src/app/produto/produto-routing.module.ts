import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProdutoListagemComponent } from './produto-listagem/produto-listagem.component';
import { ProdutoCadastrarComponent } from './produto-cadastrar/produto-cadastrar.component';

const routes: Routes = [
  { path: "listar", component: ProdutoListagemComponent},
  { path: "cadastrar", component: ProdutoCadastrarComponent},
  { path: "cadastrar/:id", component: ProdutoCadastrarComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProdutoRoutingModule { }
