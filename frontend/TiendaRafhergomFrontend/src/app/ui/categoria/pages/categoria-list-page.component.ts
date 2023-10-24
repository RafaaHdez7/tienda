import { DataTableColumn } from 'src/app//shared/components';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Subscription } from 'rxjs/internal/Subscription';
import { Categoria } from 'src/app//shared/model';
import { CategoriaService } from 'src/app/shared/services';
import { AddCategoriaDialogComponent } from '../components/add-categoria-dialog/add-categoria-dialog.component';

@Component({
  selector: 'app-categoria-list-page',
  templateUrl: './categoria-list-page.component.html',
  providers: [MessageService, DialogService, ConfirmationService],
})
export class CategoriaListPageComponent implements OnInit, OnDestroy {

  private subscriptions = new Subscription();

  loadingDataTable: boolean;

  loadingEditDialog: boolean;

  loadingDelete: boolean;

  dataKey = "id";

  categorias: Categoria[] = [];

  selectedCategorias: Categoria[] = [];

  private refDialog: any;

  columns: DataTableColumn[] = [
    { field: 'id', header: 'Id' },
    { field: 'nombreCategoria', header: 'nombreCategoria' },
    { field: 'descripcion', header: 'descripcion' },

  ];

  constructor(
    private categoriaService: CategoriaService,
    private messageService: MessageService,
    private dialogService: DialogService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.getCategoriaViews();
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  handleRefreshData() {
    this.getCategoriaViews();
  }

  showAddDialog() {
    this.refDialog = this.dialogService.open(AddCategoriaDialogComponent, {
      header: 'New Categoria',
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
      this.refDialog.onClose.subscribe((addedCategoria: any) => {
        if (addedCategoria) {
          this.onCreate();
        }
      })
    );
  }

  showEditDialog(selectedRow: Categoria) {
    this.loadingEditDialog = true;
    this.subscriptions.add(
      this.categoriaService
        .get(selectedRow.id)
        .subscribe({
          next: (categoria: Categoria) => {
            this.refDialog = this.dialogService.open(
              AddCategoriaDialogComponent,
              {
                header: 'editar Categoria',
                style: { 'min-width': '80vw', 'min-height': '30vh' },
                modal: true,
                contentStyle: {
                  'padding-bottom': '50px',
                },
                data: {
                  categoriaToEdit: categoria,
                  saveOnAccept: true,
                  isEdit: true
                },
              }
            );
            this.subscriptions.add(
              this.refDialog.onClose.subscribe((updatedCategoria: any) => {
                if (updatedCategoria) {
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

  handleDeleteEvent(selectedRow: Categoria) {
      this.confirmationService.confirm({
        message: 'Would you want to delete the selected Categoria?',
        header: 'Confirm',
        icon: 'pi pi-question-circle',
        accept: () => {
          this.loadingDelete = true;
          this.subscriptions.add(
            this.categoriaService
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
      detail: 'Categoria has been saved correctly',
      life: 10000,
    });
    this.getCategoriaViews();
  }

  private onDeleted(): void {
    this.messageService.clear();
    this.scrollToTop();
    this.messageService.add({
      severity: 'success',
      detail: 'Categoria has been deleted correctly',
      life: 10000,
    });
    this.getCategoriaViews();
  }

  private getCategoriaViews() {
    this.selectedCategorias = [];
    this.loadingDataTable = true;

    this.subscriptions.add(
      this.categoriaService
        .getViews()
        .subscribe({
          next: (respCategoriaView: Categoria[]) => {
            this.categorias = respCategoriaView;
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
