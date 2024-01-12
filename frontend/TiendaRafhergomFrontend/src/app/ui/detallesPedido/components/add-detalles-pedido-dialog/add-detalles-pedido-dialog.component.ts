import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Subscription } from 'rxjs/internal/Subscription';

import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { DetallesPedido } from 'src/app/shared/model';
import { DetallesPedidoService } from 'src/app/shared/services';

@Component({
  selector: 'app-add-detalles-pedido-dialog',
  templateUrl: './add-detalles-pedido-dialog.component.html'
})

export class AddDetallesPedidoDialogComponent implements OnInit, OnDestroy {
  private subscriptions = new Subscription();

  loadingAcceptAddDetallesPedido: boolean = false;

  isEdit: boolean;
  saveOnAccept: boolean;

  idControl = new FormControl('', [
    Validators.required,
  ]);

  pedidoIdControl = new FormControl('', [
    Validators.maxLength(25)
  ]);

  productoIdControl = new FormControl('', [
    Validators.maxLength(100)
  ]);

  precioUnitarioControl = new FormControl('', [
    Validators.maxLength(100)
  ]);

  cantidadControl = new FormControl('', [
    Validators.maxLength(20)
  ]);

  detallesPedidoToEdit: DetallesPedido;

  overlayOptions: {
    styleClass: 'w-full';
    contentStyleClass: 'w-full';
  };

  constructor(
    private dynamicDialogRef: DynamicDialogRef,
    private config: DynamicDialogConfig,
    private messageService: MessageService,
    private detallesPedidoService: DetallesPedidoService
  ) {
     // Inicialización de las propiedades en el constructor
    this.isEdit = this.config.data.isEdit;
    this.saveOnAccept = this.config.data.saveOnAccept;
    this.detallesPedidoToEdit = this.config.data.detallesPedidoToEdit;
  }

  ngOnInit(): void {
    this.saveOnAccept = this.config.data.saveOnAccept;
    this.detallesPedidoToEdit = this.config.data.detallesPedidoToEdit;
    if (this.detallesPedidoToEdit) {
      this.idControl.setValue(this.detallesPedidoToEdit.id);
      this.pedidoIdControl.setValue(this.detallesPedidoToEdit.pedidoId);
      this.productoIdControl.setValue(this.detallesPedidoToEdit.productoId);
      this.cantidadControl.setValue(this.detallesPedidoToEdit.cantidad);
      this.precioUnitarioControl.setValue(this.detallesPedidoToEdit.precioUnitario);

    }
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  handleCreateItemEvent() {
    if (this.isValid()) {
      const addedDetallesPedido: DetallesPedido = {
        id: this.idControl.value!,
        pedidoId: this.pedidoIdControl.value!,
        productoId: this.productoIdControl.value!,
        cantidad: this.cantidadControl.value!,
        precioUnitario: this.precioUnitarioControl.value!,
      };

      this.loadingAcceptAddDetallesPedido = true;
      if (!this.saveOnAccept) {
        this.dynamicDialogRef.close(addedDetallesPedido);
      } else {
        this.subscriptions.add(
          this.detallesPedidoService
            .create(addedDetallesPedido, this.isEdit)
            .subscribe({
              next: (respAddedDetallesPedido: DetallesPedido) => {
                this.dynamicDialogRef.close(respAddedDetallesPedido);
              },
              error: (_: any) => {
                if (_.status === 400) {
                  this.showErrorMessage(
                    _.error
                  );
                }else{
                  this.showErrorMessage(
                    'Problems saving the DetallesPedido'
                  );
                }
              },
            })
            .add(() => (this.loadingAcceptAddDetallesPedido = false))
        );
      }
    }
  }

  // ===================== VALIDACIONES ===================== //
  isValid(): boolean {
    return (
      this.idControl.valid &&
      this.pedidoIdControl.valid &&
      this.productoIdControl.valid &&
      this.cantidadControl.valid &&
      this.precioUnitarioControl.valid
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
