import { NgModule } from '@angular/core';
import { DetallesPedidoRoutingModule } from './detalles-pedido-routing.module';
import { DetallesPedidoComponentsModule } from './components/detalles-pedido-components.module';
import { DetallesPedidoPagesModule } from './pages/detalles-pedido-pages.module';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [
    DetallesPedidoRoutingModule,
    DetallesPedidoComponentsModule,
    DetallesPedidoPagesModule,
    HttpClientModule
  ],
  declarations: [],
})
export class DetallesPedidoModule {}
