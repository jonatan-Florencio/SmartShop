import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ItemRoutingModule } from './item-routing.module';
import { FormsModule } from '@angular/forms';
import { ItemRegistrarComponent } from './item-registrar/item-registrar.component';


@NgModule({
  declarations: [
    ItemRegistrarComponent
  ],
  imports: [
    CommonModule,
    ItemRoutingModule,
    FormsModule
  ]
})
export class ItemModule { }
