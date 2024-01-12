import { DataTableColumn } from 'src/app//shared/components';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Subscription } from 'rxjs/internal/Subscription';
import { Usuario } from 'src/app//shared/model';
import { UsuarioService } from 'src/app/shared/services';
import { AddUsuarioDialogComponent } from '../components/add-usuario-dialog/add-usuario-dialog.component';

@Component({
  selector: 'app-usuario-list-page',
  templateUrl: './usuario-list-page.component.html',
  providers: [MessageService, DialogService, ConfirmationService],
})
export class UsuarioListPageComponent implements OnInit, OnDestroy {

  private subscriptions = new Subscription();

  loadingDataTable: boolean;

  loadingEditDialog: boolean;

  loadingDelete: boolean;

  dataKey = "id";

  usuarios: Usuario[] = [];

  selectedUsuarios: Usuario[] = [];

  private refDialog: any;

  columns: DataTableColumn[] = [
    { field: 'id', header: 'Id' },
    { field: 'nombre', header: 'nombre' },
    { field: 'contrasena', header: 'contrasena' },
    { field: 'rol', header: 'Rol' },

  ];

  constructor(
    private usuarioService: UsuarioService,
    private messageService: MessageService,
    private dialogService: DialogService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.getUsuarioViews();
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  handleRefreshData() {
    this.getUsuarioViews();
  }

  showAddDialog() {
    this.refDialog = this.dialogService.open(AddUsuarioDialogComponent, {
      header: 'New Usuario',
      style: { 'min-width': '80vw', 'min-height': '30vh' },
      modal: true,
      contentStyle: {
        'padding-bottom': '50px',
      },
      data: {
        saveOnAccept: true,
        isEdit: false
      },
    });
    this.subscriptions.add(
      this.refDialog.onClose.subscribe((addedUsuario: any) => {
        if (addedUsuario) {
          this.onCreate();
        }
      })
    );
  }

  showEditDialog(selectedRow: Usuario) {
    this.loadingEditDialog = true;
    this.subscriptions.add(
      this.usuarioService
        .get(selectedRow.id)
        .subscribe({
          next: (usuario: Usuario) => {
            this.refDialog = this.dialogService.open(
              AddUsuarioDialogComponent,
              {
                header: 'editar Usuario',
                style: { 'min-width': '80vw', 'min-height': '30vh' },
                modal: true,
                contentStyle: {
                  'padding-bottom': '50px',
                },
                data: {
                  usuarioToEdit: usuario,
                  saveOnAccept: true,
                  isEdit: true
                },
              }
            );
            this.subscriptions.add(
              this.refDialog.onClose.subscribe((updatedUsuario: any) => {
                if (updatedUsuario) {
                  this.onCreate();
                }
              })
            );
          },
          error: () => {
            this.showErrorMessage(
              'Unknown problem editing AtcStucture'
            );
          },
        })
        .add(() => {
          this.loadingEditDialog = false;
        })
    );
  }

  handleDeleteEvent(selectedRow: Usuario) {
      this.confirmationService.confirm({
        message: 'Would you want to delete the selected Usuario?',
        header: 'Confirm',
        icon: 'pi pi-question-circle',
        accept: () => {
          this.loadingDelete = true;
          this.subscriptions.add(
            this.usuarioService
              .delete(selectedRow.id)
              .subscribe({
                next: () => {
                  this.onDeleted();
                },
                error: (_: any) => {
                  this.showErrorMessage(
                    'Could not be deleted the selected ActStructure'
                  );
                },
              })
              .add(() => (this.loadingDelete = false))
          );
        },
      });
  }

  private onCreate(): void {
    this.messageService.clear();
    this.scrollToTop();
    this.messageService.add({
      severity: 'success',
      detail: 'Usuario has been saved correctly',
      life: 10000,
    });
    this.getUsuarioViews();
  }

  private onDeleted(): void {
    this.messageService.clear();
    this.scrollToTop();
    this.messageService.add({
      severity: 'success',
      detail: 'Usuario has been deleted correctly',
      life: 10000,
    });
    this.getUsuarioViews();
  }

  private getUsuarioViews() {
    this.selectedUsuarios = [];
    this.loadingDataTable = true;

    this.subscriptions.add(
      this.usuarioService
        .getViews()
        .subscribe({
          next: (respUsuarioView: Usuario[]) => {
            this.usuarios = respUsuarioView;
          },
          error: () => {
            this.showErrorMessage(
              'There have been an unexpected problem during the consult.'
            );
          },
        })
        .add(() => {
          this.loadingDataTable = false;
          history.replaceState('', '');
        })
    );
  }

  private showErrorMessage(errorMessage: string): void {
    this.messageService.clear();
    this.messageService.add({
      severity: 'error',
      detail: errorMessage,
      life: 10000,
    });
    this.scrollToTop();
  }

  private scrollToTop() {
    window.scroll({
      top: 0,
      left: 0,
    });
  }

}
