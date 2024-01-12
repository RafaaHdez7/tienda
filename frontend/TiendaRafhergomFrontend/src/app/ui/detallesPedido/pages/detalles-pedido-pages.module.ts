import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedComponentsModule } from 'src/app//shared/components';
import { DetallesPedidoService } from 'src/app//shared/services';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogService } from 'primeng/dynamicdialog';
import { MessagesModule } from 'primeng/messages';
import { DetallesPedidoListPageComponent } from './detalles-pedido-list-page.component';

@NgModule({
  imports: [
    ReactiveFormsModule,
    MessagesModule,
    ConfirmDialogModule,
    SharedComponentsModule,
  ],
  declarations: [DetallesPedidoListPageComponent],
  providers: [
    DetallesPedidoService,
    MessageService,
    DialogService,
    ConfirmationService,
  ],
})
export class DetallesPedidoPagesModule {}
