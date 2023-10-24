import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Subscription } from 'rxjs/internal/Subscription';

import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Categoria } from 'src/app/shared/model';
import { CategoriaService } from 'src/app/shared/services';

@Component({
  selector: 'app-add-categoria-dialog',
  templateUrl: './add-categoria-dialog.component.html'
})

export class AddCategoriaDialogComponent implements OnInit, OnDestroy {
  private subscriptions = new Subscription();

  loadingAcceptAddCategoria: boolean = false;

  isEdit: boolean;
  saveOnAccept: boolean;

  idControl = new FormControl('', [
    Validators.required,
  ]);

  nombreCategoriaControl = new FormControl('', [
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

  rolControl = new FormControl('', [
    Validators.required
  ]);

  categoriaToEdit: Categoria;

  overlayOptions: {
    styleClass: 'w-full';
    contentStyleClass: 'w-full';
  };

  constructor(
    private dynamicDialogRef: DynamicDialogRef,
    private config: DynamicDialogConfig,
    private messageService: MessageService,
    private categoriaService: CategoriaService
  ) {
     // Inicialización de las propiedades en el constructor
    this.isEdit = this.config.data.isEdit;
    this.saveOnAccept = this.config.data.saveOnAccept;
    this.categoriaToEdit = this.config.data.categoriaToEdit;
  }

  ngOnInit(): void {
    this.saveOnAccept = this.config.data.saveOnAccept;
    this.categoriaToEdit = this.config.data.categoriaToEdit;
    if (this.categoriaToEdit) {
      this.idControl.setValue(this.categoriaToEdit.id);
      this.nombreCategoriaControl.setValue(this.categoriaToEdit.nombreCategoria);
      this.descripcionControl.setValue(this.categoriaToEdit.descripcion);

    }
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  handleCreateItemEvent() {
    if (this.isValid()) {
      const addedCategoria: Categoria = {
        id: this.idControl.value!,
        nombreCategoria: this.nombreCategoriaControl.value!,
        descripcion: this.descripcionControl.value!,
      };

      this.loadingAcceptAddCategoria = true;
      if (!this.saveOnAccept) {
        this.dynamicDialogRef.close(addedCategoria);
      } else {
        this.subscriptions.add(
          this.categoriaService
            .create(addedCategoria, this.isEdit)
            .subscribe({
              next: (respAddedCategoria: Categoria) => {
                this.dynamicDialogRef.close(respAddedCategoria);
              },
              error: (_: any) => {
                if (_.status === 400) {
                  this.showErrorMessage(
                    _.error
                  );
                }else{
                  this.showErrorMessage(
                    'Problems saving the Categoria'
                  );
                }
              },
            })
            .add(() => (this.loadingAcceptAddCategoria = false))
        );
      }
    }
  }

  // ===================== VALIDACIONES ===================== //
  isValid(): boolean {
    return (
      this.idControl.valid &&
      this.nombreCategoriaControl.valid &&
      this.descripcionControl.valid &&
      this.rolControl.valid
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
