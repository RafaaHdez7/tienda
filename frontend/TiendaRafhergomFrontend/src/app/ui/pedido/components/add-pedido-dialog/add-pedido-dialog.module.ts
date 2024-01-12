import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MessagesModule } from 'primeng/messages';
import { AddPedidoDialogComponent } from './add-pedido-dialog.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ButtonModule,
    InputTextModule,
    MessagesModule
  ],
  declarations: [AddPedidoDialogComponent],
  exports: [AddPedidoDialogComponent],
})
export class AddPedidoDialogModule {}
