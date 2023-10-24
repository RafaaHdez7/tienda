import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MessagesModule } from 'primeng/messages';
import { AddCategoriaDialogComponent } from './add-categoria-dialog.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ButtonModule,
    InputTextModule,
    MessagesModule
  ],
  declarations: [AddCategoriaDialogComponent],
  exports: [AddCategoriaDialogComponent],
})
export class AddCategoriaDialogModule {}
