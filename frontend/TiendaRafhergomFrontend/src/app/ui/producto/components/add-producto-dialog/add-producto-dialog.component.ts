import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Subscription } from 'rxjs/internal/Subscription';

import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Producto } from 'src/app/shared/model';
import { ProductoService } from 'src/app/shared/services';

@Component({
  selector: 'app-add-producto-dialog',
  templateUrl: './add-producto-dialog.component.html'
})

export class AddProductoDialogComponent implements OnInit, OnDestroy {
  private subscriptions = new Subscription();

  loadingAcceptAddProducto: boolean = false;

  isEdit: boolean;
  saveOnAccept: boolean;

  idControl = new FormControl('', [
    Validators.required,
  ]);

  nombreProductoControl = new FormControl('', [
    Validators.maxLength(25)
  ]);

  descripcionControl = new FormControl('', [
    Validators.maxLength(100)
  ]);

  stockDisponibleControl = new FormControl('', [
    Validators.maxLength(20)
  ]);

  categoriaIdControl = new FormControl('', [
    Validators.maxLength(20)
  ]);

  precioControl = new FormControl('', [
    Validators.required
  ]);

  productoToEdit: Producto;

  overlayOptions: {
    styleClass: 'w-full';
    contentStyleClass: 'w-full';
  };

  constructor(
    private dynamicDialogRef: DynamicDialogRef,
    private config: DynamicDialogConfig,
    private messageService: MessageService,
    private productoService: ProductoService
  ) {
     // Inicialización de las propiedades en el constructor
    this.isEdit = this.config.data.isEdit;
    this.saveOnAccept = this.config.data.saveOnAccept;
    this.productoToEdit = this.config.data.productoToEdit;
  }

  ngOnInit(): void {
    this.saveOnAccept = this.config.data.saveOnAccept;
    this.productoToEdit = this.config.data.productoToEdit;
    if (this.productoToEdit) {
      this.idControl.setValue(this.productoToEdit.id);
      this.nombreProductoControl.setValue(this.productoToEdit.nombreProducto);
      this.descripcionControl.setValue(this.productoToEdit.descripcion);
      this.precioControl.setValue(this.productoToEdit.precio);
      this.stockDisponibleControl.setValue(this.productoToEdit.stockDisponible);
      this.categoriaIdControl.setValue(this.productoToEdit.categoriaId);

    }
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  handleCreateItemEvent() {
    if (this.isValid()) {
      const addedProducto: Producto = {
        id: this.idControl.value!,
        nombreProducto: this.nombreProductoControl.value!,
        descripcion: this.descripcionControl.value!,
        categoriaId: this.categoriaIdControl.value!,
        stockDisponible: this.stockDisponibleControl.value!,
        precio: this.precioControl.value!
      };

      this.loadingAcceptAddProducto = true;
      if (!this.saveOnAccept) {
        this.dynamicDialogRef.close(addedProducto);
      } else {
        this.subscriptions.add(
          this.productoService
            .create(addedProducto, this.isEdit)
            .subscribe({
              next: (respAddedProducto: Producto) => {
                this.dynamicDialogRef.close(respAddedProducto);
              },
              error: (_: any) => {
                if (_.status === 400) {
                  this.showErrorMessage(
                    _.error
                  );
                }else{
                  this.showErrorMessage(
                    'Problems saving the Producto'
                  );
                }
              },
            })
            .add(() => (this.loadingAcceptAddProducto = false))
        );
      }
    }
  }

  // ===================== VALIDACIONES ===================== //
  isValid(): boolean {
    return (
      this.idControl.valid &&
      this.nombreProductoControl.valid &&
      this.descripcionControl.valid &&
      this.categoriaIdControl.valid &&
      this.stockDisponibleControl.valid &&
      this.precioControl.valid
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
