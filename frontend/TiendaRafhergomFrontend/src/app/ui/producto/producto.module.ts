import { NgModule } from '@angular/core';
import { ProductoRoutingModule } from './producto-routing.module';
import { ProductoComponentsModule } from './components/producto-components.module';
import { ProductoPagesModule } from './pages/producto-pages.module';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [
    ProductoRoutingModule,
    ProductoComponentsModule,
    ProductoPagesModule,
    HttpClientModule
  ],
  declarations: [],
})
export class ProductoModule {}
