import { NgModule } from '@angular/core';
import { PedidoRoutingModule } from './pedido-routing.module';
import { PedidoComponentsModule } from './components/pedido-components.module';
import { PedidoPagesModule } from './pages/pedido-pages.module';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [
    PedidoRoutingModule,
    PedidoComponentsModule,
    PedidoPagesModule,
    HttpClientModule
  ],
  declarations: [],
})
export class PedidoModule {}
