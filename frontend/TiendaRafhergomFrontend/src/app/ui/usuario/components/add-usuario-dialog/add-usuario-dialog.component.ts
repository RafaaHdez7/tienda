import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Subscription } from 'rxjs/internal/Subscription';

import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Usuario } from 'src/app/shared/model';
import { UsuarioService } from 'src/app/shared/services';

@Component({
  selector: 'app-add-usuario-dialog',
  templateUrl: './add-usuario-dialog.component.html'
})

export class AddUsuarioDialogComponent implements OnInit, OnDestroy {
  private subscriptions = new Subscription();

  loadingAcceptAddUsuario: boolean = false;

  isEdit: boolean;
  saveOnAccept: boolean;

  idControl = new FormControl('', [
    Validators.required,
  ]);

  nombreControl = new FormControl('', [
    Validators.maxLength(25)
  ]);

  contrasenaControl = new FormControl('', [
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

  usuarioToEdit: Usuario;

  overlayOptions: {
    styleClass: 'w-full';
    contentStyleClass: 'w-full';
  };

  constructor(
    private dynamicDialogRef: DynamicDialogRef,
    private config: DynamicDialogConfig,
    private messageService: MessageService,
    private usuarioService: UsuarioService
  ) {
     // Inicialización de las propiedades en el constructor
    this.isEdit = this.config.data.isEdit;
    this.saveOnAccept = this.config.data.saveOnAccept;
    this.usuarioToEdit = this.config.data.usuarioToEdit;
  }

  ngOnInit(): void {
    this.saveOnAccept = this.config.data.saveOnAccept;
    this.usuarioToEdit = this.config.data.usuarioToEdit;
    if (this.usuarioToEdit) {
      this.idControl.setValue(this.usuarioToEdit.id);
      this.nombreControl.setValue(this.usuarioToEdit.nombre);
      this.contrasenaControl.setValue(this.usuarioToEdit.contrasena);  //TODO
      this.rolControl.setValue(this.usuarioToEdit.rol);

    }
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  handleCreateItemEvent() {
    if (this.isValid()) {
      const addedUsuario: Usuario = {
        id: this.idControl.value!,
        nombre: this.nombreControl.value!,
        contrasena: this.contrasenaControl.value!,
        rol: this.rolControl.value!
      };

      this.loadingAcceptAddUsuario = true;
      if (!this.saveOnAccept) {
        this.dynamicDialogRef.close(addedUsuario);
      } else {
        this.subscriptions.add(
          this.usuarioService
            .create(addedUsuario, this.isEdit)
            .subscribe({
              next: (respAddedUsuario: Usuario) => {
                this.dynamicDialogRef.close(respAddedUsuario);
              },
              error: (_: any) => {
                if (_.status === 400) {
                  this.showErrorMessage(
                    _.error
                  );
                }else{
                  this.showErrorMessage(
                    'Problems saving the Usuario'
                  );
                }
              },
            })
            .add(() => (this.loadingAcceptAddUsuario = false))
        );
      }
    }
  }

  // ===================== VALIDACIONES ===================== //
  isValid(): boolean {
    return (
      this.idControl.valid &&
      this.nombreControl.valid &&
      this.contrasenaControl.valid &&
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
