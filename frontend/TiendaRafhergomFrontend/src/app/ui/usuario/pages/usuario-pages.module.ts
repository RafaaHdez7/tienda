import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedComponentsModule } from 'src/app//shared/components';
import { UsuarioService } from 'src/app//shared/services';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogService } from 'primeng/dynamicdialog';
import { MessagesModule } from 'primeng/messages';
import { UsuarioListPageComponent } from './usuario-list-page.component';

@NgModule({
  imports: [
    ReactiveFormsModule,
    MessagesModule,
    ConfirmDialogModule,
    SharedComponentsModule,
  ],
  declarations: [UsuarioListPageComponent],
  providers: [
    UsuarioService,
    MessageService,
    DialogService,
    ConfirmationService,
  ],
})
export class UsuarioPagesModule {}
