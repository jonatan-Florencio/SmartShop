import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdministracaoRoutingModule } from './administracao-routing.module';
import { AdmInicioComponent } from './adm-inicio/adm-inicio.component';


@NgModule({
  declarations: [
    AdmInicioComponent
  ],
  imports: [
    CommonModule,
    AdministracaoRoutingModule
  ]
})
export class AdministracaoModule { }
