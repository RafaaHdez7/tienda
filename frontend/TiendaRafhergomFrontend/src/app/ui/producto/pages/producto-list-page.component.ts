import { DataTableColumn } from 'src/app//shared/components';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Subscription } from 'rxjs/internal/Subscription';
import { Producto } from 'src/app//shared/model';
import { ProductoService } from 'src/app//shared/services';
import { AddProductoDialogComponent } from '../components/add-producto-dialog/add-producto-dialog.component';

@Component({
  selector: 'app-producto-list-page',
  templateUrl: './producto-list-page.component.html',
  providers: [MessageService, DialogService, ConfirmationService],
})
export class ProductoListPageComponent implements OnInit, OnDestroy {

  private subscriptions = new Subscription();

  loadingDataTable: boolean;

  loadingEditDialog: boolean;

  loadingDelete: boolean;

  dataKey = "id";

  productos: Producto[] = [];

  selectedProductos: Producto[] = [];

  private refDialog: any;

  columns: DataTableColumn[] = [
    { field: 'id', header: 'Id' },
    { field: 'nombreProducto', header: 'nombreProducto' },
    { field: 'descripcion', header: 'descripcion' },
    { field: 'categoriaId', header: 'categoriaId' },
    { field: 'stockDisponible', header: 'stockDisponible' },
    { field: 'precio', header: 'precio' },
  ];

  constructor(
    private productoService: ProductoService,
    private messageService: MessageService,
    private dialogService: DialogService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.getProductoViews();
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  handleRefreshData() {
    this.getProductoViews();
  }

  showAddDialog() {
    this.refDialog = this.dialogService.open(AddProductoDialogComponent, {
      header: 'New Producto',
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
      this.refDialog.onClose.subscribe((addedProducto: any) => {
        if (addedProducto) {
          this.onCreate();
        }
      })
    );
  }

  showEditDialog(selectedRow: Producto) {
    this.loadingEditDialog = true;
    this.subscriptions.add(
      this.productoService
        .get(selectedRow.id)
        .subscribe({
          next: (producto: Producto) => {
            this.refDialog = this.dialogService.open(
              AddProductoDialogComponent,
              {
                header: 'editProducto',
                style: { 'min-width': '80vw', 'min-height': '30vh' },
                modal: true,
                contentStyle: {
                  'padding-bottom': '50px',
                },
                data: {
                  productoToEdit: producto,
                  saveOnAccept: true,
                  isEdit: true
                },
              }
            );
            this.subscriptions.add(
              this.refDialog.onClose.subscribe((updatedProducto: any) => {
                if (updatedProducto) {
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

  handleDeleteEvent(selectedRow: Producto) {
      this.confirmationService.confirm({
        message: 'Would you want to delete the selected Producto?',
        header: 'Confirm',
        icon: 'pi pi-question-circle',
        accept: () => {
          this.loadingDelete = true;
          this.subscriptions.add(
            this.productoService
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
      detail: 'Producto has been saved correctly',
      life: 10000,
    });
    this.getProductoViews();
  }

  private onDeleted(): void {
    this.messageService.clear();
    this.scrollToTop();
    this.messageService.add({
      severity: 'success',
      detail: 'Producto has been deleted correctly',
      life: 10000,
    });
    this.getProductoViews();
  }

  private getProductoViews() {
    this.selectedProductos = [];
    this.loadingDataTable = true;

    this.subscriptions.add(
      this.productoService
        .getViews()
        .subscribe({
          next: (respProductoView: Producto[]) => {
            this.productos = respProductoView;
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
