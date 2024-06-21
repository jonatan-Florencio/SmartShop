import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UsuarioRoutingModule } from './usuario-routing.module';
import { UsuarioListagemComponent } from './usuario-listagem/usuario-listagem.component';
import { UsuarioCadastroComponent } from './usuario-cadastro/usuario-cadastro.component';

@NgModule({
  declarations: [
    UsuarioListagemComponent,
    UsuarioCadastroComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    UsuarioRoutingModule
  ]
})
export class UsuarioModule { }
