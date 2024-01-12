import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DetallesPedidoListPageComponent } from './pages/detalles-pedido-list-page.component';

const routes: Routes = [
  {
    path: '',
    component: DetallesPedidoListPageComponent,
  },

  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DetallesPedidoRoutingModule {}
