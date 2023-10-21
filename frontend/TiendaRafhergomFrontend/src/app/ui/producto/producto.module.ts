import { NgModule } from '@angular/core';
import { ProductoRoutingModule } from './producto-routing.module';
import { ProductoComponentsModule } from './components/producto-components.module';
import { ProductoPagesModule } from './pages/producto-pages.module';

@NgModule({
  imports: [
    ProductoRoutingModule,
    ProductoComponentsModule,
    ProductoPagesModule
  ],
  declarations: [],
})
export class ProductoModule {}
