import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Subscription } from 'rxjs/internal/Subscription';

import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Pedido } from 'src/app/shared/model';
import { PedidoService } from 'src/app/shared/services';

@Component({
  selector: 'app-add-pedido-dialog',
  templateUrl: './add-pedido-dialog.component.html'
})

export class AddPedidoDialogComponent implements OnInit, OnDestroy {
  private subscriptions = new Subscription();

  loadingAcceptAddPedido: boolean = false;

  isEdit: boolean;
  saveOnAccept: boolean;

  idControl = new FormControl('', [
    Validators.required,
  ]);

  usuarioIdControl = new FormControl('', [
    Validators.maxLength(25)
  ]);

  usuarioNombreControl = new FormControl('', [
    Validators.maxLength(100)
  ]);

  fechaHoraControl = new FormControl('', [
    Validators.maxLength(100)
  ]);

  estadoPedidoControl = new FormControl('', [
    Validators.maxLength(20)
  ]);

  pedidoToEdit: Pedido;

  overlayOptions: {
    styleClass: 'w-full';
    contentStyleClass: 'w-full';
  };

  constructor(
    private dynamicDialogRef: DynamicDialogRef,
    private config: DynamicDialogConfig,
    private messageService: MessageService,
    private pedidoService: PedidoService
  ) {
     // Inicialización de las propiedades en el constructor
    this.isEdit = this.config.data.isEdit;
    this.saveOnAccept = this.config.data.saveOnAccept;
    this.pedidoToEdit = this.config.data.pedidoToEdit;
  }

  ngOnInit(): void {
    this.saveOnAccept = this.config.data.saveOnAccept;
    this.pedidoToEdit = this.config.data.pedidoToEdit;
    if (this.pedidoToEdit) {
      this.idControl.setValue(this.pedidoToEdit.id);
      this.usuarioIdControl.setValue(this.pedidoToEdit.usuarioId);
      this.usuarioNombreControl.setValue(this.pedidoToEdit.usuarioNombre);
      this.estadoPedidoControl.setValue(this.pedidoToEdit.estadoPedido);
      this.fechaHoraControl.setValue(this.pedidoToEdit.fechaHora);

    }
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  handleCreateItemEvent() {
    if (this.isValid()) {
      const addedPedido: Pedido = {
        id: this.idControl.value!,
        usuarioId: this.usuarioIdControl.value!,
        usuarioNombre: this.usuarioNombreControl.value!,
        estadoPedido: this.estadoPedidoControl.value!,
        fechaHora: this.fechaHoraControl.value!,
      };

      this.loadingAcceptAddPedido = true;
      if (!this.saveOnAccept) {
        this.dynamicDialogRef.close(addedPedido);
      } else {
        this.subscriptions.add(
          this.pedidoService
            .create(addedPedido, this.isEdit)
            .subscribe({
              next: (respAddedPedido: Pedido) => {
                this.dynamicDialogRef.close(respAddedPedido);
              },
              error: (_: any) => {
                if (_.status === 400) {
                  this.showErrorMessage(
                    _.error
                  );
                }else{
                  this.showErrorMessage(
                    'Problems saving the Pedido'
                  );
                }
              },
            })
            .add(() => (this.loadingAcceptAddPedido = false))
        );
      }
    }
  }

  // ===================== VALIDACIONES ===================== //
  isValid(): boolean {
    return (
      this.idControl.valid &&
      this.usuarioIdControl.valid &&
      this.usuarioNombreControl.valid &&
      this.estadoPedidoControl.valid &&
      this.fechaHoraControl.valid
    );
  }

  private showErrorMessage(errorMessage: string): void {
    this.messageService.clear();
    this.messageService.add({
      severity: 'error',
      detail: errorMessage,
      life: 5000,
    });
    document
      .querySelector('#dialog-content')
      ?.scrollIntoView({ behavior: 'smooth', block: 'start' });
  }

}
