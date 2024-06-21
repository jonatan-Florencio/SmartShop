import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MarcaRoutingModule } from './marca-routing.module';
import { MarcaCadastrarListarComponent } from './marca-cadastrar-listar/marca-cadastrar-listar.component';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    MarcaCadastrarListarComponent
  ],
  imports: [
    CommonModule,
    MarcaRoutingModule,
    FormsModule
  ]
})
export class MarcaModule { }
