import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PublicRoutingModule } from './public-routing.module';
import { PublicComponent } from './public/public.component';
import { HeaderComponent } from '@shop-core/components/header/header.component';
import { FooterComponent } from '@shop-core/components/footer/footer.component';
import { NavbarComponent } from '@shop-core/components/navbar/navbar.component';


@NgModule({
  declarations: [
    PublicComponent,
    HeaderComponent,
    FooterComponent,
    NavbarComponent
  ],
  imports: [
    CommonModule,
    PublicRoutingModule
  ]
})
export class PublicModule { }
