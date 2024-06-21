import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CarrinhoListagemComponent } from './carrinho-listagem/carrinho-listagem.component';
import { CarrinhoCadastrarComponent } from './carrinho-cadastrar/carrinho-cadastrar.component';
import { CarrinhoIniciarComponent } from './carrinho-iniciar/carrinho-iniciar.component';
import { CarrinhoFinalizarComponent } from './carrinho-finalizar/carrinho-finalizar.component';

const routes: Routes = [
  { path: '', component: CarrinhoIniciarComponent },
  { path: 'listar', component: CarrinhoListagemComponent },
  { path: 'cadastrar', component: CarrinhoCadastrarComponent },
  { path: 'finalizar', component: CarrinhoFinalizarComponent } 
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CarrinhoRoutingModule { }
