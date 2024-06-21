import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CategoriaRoutingModule } from './categoria-routing.module';
import { FormsModule } from '@angular/forms';
import { CategoriaCadastrarListarComponent } from './categoria-cadastrar-listar/categoria-cadastrar-listar.component';


@NgModule({
  declarations: [
    CategoriaCadastrarListarComponent
  ],
  imports: [
    CommonModule,
    CategoriaRoutingModule,
    FormsModule
  ]
})
export class CategoriaModule { }
