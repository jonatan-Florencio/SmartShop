import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CarrinhoRoutingModule } from './carrinho-routing.module';
import { CarrinhoListagemComponent } from './carrinho-listagem/carrinho-listagem.component';
import { FormsModule } from '@angular/forms';
import { CarrinhoIniciarComponent } from './carrinho-iniciar/carrinho-iniciar.component';
import { CarrinhoCadastrarComponent } from './carrinho-cadastrar/carrinho-cadastrar.component';
import { CarrinhoFinalizarComponent } from './carrinho-finalizar/carrinho-finalizar.component';

@NgModule({
  declarations: [
    CarrinhoListagemComponent,
    CarrinhoIniciarComponent,
    CarrinhoCadastrarComponent,
    CarrinhoFinalizarComponent
  ],
  imports: [
    CommonModule,
    CarrinhoRoutingModule,
    FormsModule
  ]
})
export class CarrinhoModule { }
