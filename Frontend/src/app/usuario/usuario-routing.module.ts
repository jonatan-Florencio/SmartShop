import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsuarioListagemComponent } from './usuario-listagem/usuario-listagem.component';
import { UsuarioCadastroComponent } from './usuario-cadastro/usuario-cadastro.component';

const routes: Routes = [
  { path: '', redirectTo: 'usuario-listagem', pathMatch: 'full' },
  { path: 'usuario-listagem', component: UsuarioListagemComponent },
  { path: 'usuario-cadastro', component: UsuarioCadastroComponent },
  { path: 'usuario-cadastro/:id', component: UsuarioCadastroComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsuarioRoutingModule {}
