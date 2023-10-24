import { NgModule } from '@angular/core';
import { CategoriaRoutingModule } from './categoria-routing.module';
import { CategoriaComponentsModule } from './components/categoria-components.module';
import { CategoriaPagesModule } from './pages/categoria-pages.module';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [
    CategoriaRoutingModule,
    CategoriaComponentsModule,
    CategoriaPagesModule,
    HttpClientModule
  ],
  declarations: [],
})
export class CategoriaModule {}
