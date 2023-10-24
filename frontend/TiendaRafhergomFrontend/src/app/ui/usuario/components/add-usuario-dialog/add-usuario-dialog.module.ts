import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MessagesModule } from 'primeng/messages';
import { AddUsuarioDialogComponent } from './add-usuario-dialog.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ButtonModule,
    InputTextModule,
    MessagesModule
  ],
  declarations: [AddUsuarioDialogComponent],
  exports: [AddUsuarioDialogComponent],
})
export class AddUsuarioDialogModule {}
