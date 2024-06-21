import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MarcaCadastrarListarComponent } from './marca-cadastrar-listar/marca-cadastrar-listar.component';

const routes: Routes = [
  { path: '', component: MarcaCadastrarListarComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MarcaRoutingModule { }
