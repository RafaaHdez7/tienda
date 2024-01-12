import { DataTableColumn } from 'src/app//shared/components';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Subscription } from 'rxjs/internal/Subscription';
import { DetallesPedido } from 'src/app//shared/model';
import { DetallesPedidoService } from 'src/app/shared/services';
import { AddDetallesPedidoDialogComponent } from '../components/add-detalles-pedido-dialog/add-detalles-pedido-dialog.component';

@Component({
  selector: 'app-detalles-pedido-list-page',
  templateUrl: './detalles-pedido-list-page.component.html',
  providers: [MessageService, DialogService, ConfirmationService],
})
export class DetallesPedidoListPageComponent implements OnInit, OnDestroy {

  private subscriptions = new Subscription();

  loadingDataTable: boolean;

  loadingEditDialog: boolean;

  loadingDelete: boolean;

  dataKey = "id";

  detallesPedidos: DetallesPedido[] = [];

  selectedDetallesPedidos: DetallesPedido[] = [];

  private refDialog: any;

  columns: DataTableColumn[] = [
    { field: 'id', header: 'Id' },
    { field: 'pedidoId', header: 'pedidoId' },
    { field: 'productoId', header: 'productoId' },
    { field: 'cantidad', header: 'cantidad' },
    { field: 'precioUnitario', header: 'precioUnitario' },

  ];

  constructor(
    private detallesPedidoService: DetallesPedidoService,
    private messageService: MessageService,
    private dialogService: DialogService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.getDetallesPedidoViews();
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  handleRefreshData() {
    this.getDetallesPedidoViews();
  }

  showAddDialog() {
    this.refDialog = this.dialogService.open(AddDetallesPedidoDialogComponent, {
      header: 'New DetallesPedido',
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
      this.refDialog.onClose.subscribe((addedDetallesPedido: any) => {
        if (addedDetallesPedido) {
          this.onCreate();
        }
      })
    );
  }

  showEditDialog(selectedRow: DetallesPedido) {
    this.loadingEditDialog = true;
    this.subscriptions.add(
      this.detallesPedidoService
        .get(selectedRow.id)
        .subscribe({
          next: (detallesPedido: DetallesPedido) => {
            this.refDialog = this.dialogService.open(
              AddDetallesPedidoDialogComponent,
              {
                header: 'editar DetallesPedido',
                style: { 'min-width': '80vw', 'min-height': '30vh' },
                modal: true,
                contentStyle: {
                  'padding-bottom': '50px',
                },
                data: {
                  detallesPedidoToEdit: detallesPedido,
                  saveOnAccept: true,
                  isEdit: true
                },
              }
            );
            this.subscriptions.add(
              this.refDialog.onClose.subscribe((updatedDetallesPedido: any) => {
                if (updatedDetallesPedido) {
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

  handleDeleteEvent(selectedRow: DetallesPedido) {
      this.confirmationService.confirm({
        message: 'Would you want to delete the selected DetallesPedido?',
        header: 'Confirm',
        icon: 'pi pi-question-circle',
        accept: () => {
          this.loadingDelete = true;
          this.subscriptions.add(
            this.detallesPedidoService
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
      detail: 'DetallesPedido has been saved correctly',
      life: 10000,
    });
    this.getDetallesPedidoViews();
  }

  private onDeleted(): void {
    this.messageService.clear();
    this.scrollToTop();
    this.messageService.add({
      severity: 'success',
      detail: 'DetallesPedido has been deleted correctly',
      life: 10000,
    });
    this.getDetallesPedidoViews();
  }

  private getDetallesPedidoViews() {
    this.selectedDetallesPedidos = [];
    this.loadingDataTable = true;

    this.subscriptions.add(
      this.detallesPedidoService
        .getViews()
        .subscribe({
          next: (respDetallesPedidoView: DetallesPedido[]) => {
            this.detallesPedidos = respDetallesPedidoView;
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
