import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoriaCadastrarListarComponent } from './categoria-cadastrar-listar/categoria-cadastrar-listar.component';

const routes: Routes = [
  { path: '', component: CategoriaCadastrarListarComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CategoriaRoutingModule { }
