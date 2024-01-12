import { DataTableColumn } from 'src/app//shared/components';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Subscription } from 'rxjs/internal/Subscription';
import { Pedido } from 'src/app//shared/model';
import { PedidoService } from 'src/app/shared/services';
import { AddPedidoDialogComponent } from '../components/add-pedido-dialog/add-pedido-dialog.component';

@Component({
  selector: 'app-pedido-list-page',
  templateUrl: './pedido-list-page.component.html',
  providers: [MessageService, DialogService, ConfirmationService],
})
export class PedidoListPageComponent implements OnInit, OnDestroy {

  private subscriptions = new Subscription();

  loadingDataTable: boolean;

  loadingEditDialog: boolean;

  loadingDelete: boolean;

  dataKey = "id";

  pedidos: Pedido[] = [];

  selectedPedidos: Pedido[] = [];

  private refDialog: any;

  columns: DataTableColumn[] = [
    { field: 'id', header: 'Id' },
    { field: 'usuarioId', header: 'usuarioId' },
    { field: 'usuarioNombre', header: 'usuarioNombre' },
    { field: 'estadoPedido', header: 'estadoPedido' },
    { field: 'fechaHora', header: 'fechaHora' },

  ];

  constructor(
    private pedidoService: PedidoService,
    private messageService: MessageService,
    private dialogService: DialogService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.getPedidoViews();
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  handleRefreshData() {
    this.getPedidoViews();
  }

  showAddDialog() {
    this.refDialog = this.dialogService.open(AddPedidoDialogComponent, {
      header: 'New Pedido',
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
      this.refDialog.onClose.subscribe((addedPedido: any) => {
        if (addedPedido) {
          this.onCreate();
        }
      })
    );
  }

  showEditDialog(selectedRow: Pedido) {
    this.loadingEditDialog = true;
    this.subscriptions.add(
      this.pedidoService
        .get(selectedRow.id)
        .subscribe({
          next: (pedido: Pedido) => {
            this.refDialog = this.dialogService.open(
              AddPedidoDialogComponent,
              {
                header: 'editar Pedido',
                style: { 'min-width': '80vw', 'min-height': '30vh' },
                modal: true,
                contentStyle: {
                  'padding-bottom': '50px',
                },
                data: {
                  pedidoToEdit: pedido,
                  saveOnAccept: true,
                  isEdit: true
                },
              }
            );
            this.subscriptions.add(
              this.refDialog.onClose.subscribe((updatedPedido: any) => {
                if (updatedPedido) {
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

  handleDeleteEvent(selectedRow: Pedido) {
      this.confirmationService.confirm({
        message: 'Would you want to delete the selected Pedido?',
        header: 'Confirm',
        icon: 'pi pi-question-circle',
        accept: () => {
          this.loadingDelete = true;
          this.subscriptions.add(
            this.pedidoService
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
      detail: 'Pedido has been saved correctly',
      life: 10000,
    });
    this.getPedidoViews();
  }

  private onDeleted(): void {
    this.messageService.clear();
    this.scrollToTop();
    this.messageService.add({
      severity: 'success',
      detail: 'Pedido has been deleted correctly',
      life: 10000,
    });
    this.getPedidoViews();
  }

  private getPedidoViews() {
    this.selectedPedidos = [];
    this.loadingDataTable = true;

    this.subscriptions.add(
      this.pedidoService
        .getViews()
        .subscribe({
          next: (respPedidoView: Pedido[]) => {
            this.pedidos = respPedidoView;
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
