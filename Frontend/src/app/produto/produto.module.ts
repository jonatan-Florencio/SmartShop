import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProdutoRoutingModule } from './produto-routing.module';
import { ProdutoListagemComponent } from './produto-listagem/produto-listagem.component';
import { ProdutoCadastrarComponent } from './produto-cadastrar/produto-cadastrar.component';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    ProdutoListagemComponent,
    ProdutoCadastrarComponent
  ],
  imports: [
    CommonModule,
    ProdutoRoutingModule,
    FormsModule
  ]
})
export class ProdutoModule { }
