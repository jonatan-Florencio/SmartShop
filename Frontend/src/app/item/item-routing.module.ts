import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ItemRegistrarComponent } from './item-registrar/item-registrar.component';

const routes: Routes = [
  { path: 'cadastrar/:idCarrinho', component: ItemRegistrarComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ItemRoutingModule { }
