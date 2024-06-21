import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdmInicioComponent } from './adm-inicio/adm-inicio.component';

const routes: Routes = [
  { path: '', component: AdmInicioComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdministracaoRoutingModule { }
