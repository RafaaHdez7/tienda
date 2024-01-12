import { NgModule } from '@angular/core';
import { UsuarioRoutingModule } from './usuario-routing.module';
import { UsuarioComponentsModule } from './components/usuario-components.module';
import { UsuarioPagesModule } from './pages/usuario-pages.module';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [
    UsuarioRoutingModule,
    UsuarioComponentsModule,
    UsuarioPagesModule,
    HttpClientModule
  ],
  declarations: [],
})
export class UsuarioModule {}
