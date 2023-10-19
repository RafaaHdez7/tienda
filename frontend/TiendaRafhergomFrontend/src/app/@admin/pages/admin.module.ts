//  Angular Configs
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

//  Routing
import { AdminRoutingModule } from './admin-routing.module';

//  Componentes de la Aplicacion
import { AdminComponent } from './admin/admin.component';
import { TitleComponent } from '@admin-core/components/title/title.component';
import { HeaderComponent } from '@admin-core/components/header/header.component';
import { SidebarComponent } from '@admin-core/components/sidebar/sidebar.component';


@NgModule({
  declarations: [
    AdminComponent,
    TitleComponent,
    HeaderComponent,
    SidebarComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule
  ]
})
export class AdminModule { }
