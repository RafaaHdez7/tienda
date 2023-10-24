import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AdminModule } from './@admin/pages/admin.module';
import { PublicModule } from './@public/pages/public.module';

import { AppRoutingModule } from './app-routing.module';
import { SharedComponentsModule } from './shared/components';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { AccordionModule } from 'primeng/accordion';
import { ButtonModule } from 'primeng/button';
import { CalendarModule } from 'primeng/calendar';
import { CardModule } from 'primeng/card';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { FieldsetModule } from 'primeng/fieldset';
import { MenubarModule } from 'primeng/menubar';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { PanelModule } from 'primeng/panel';
import { SlideMenuModule } from 'primeng/slidemenu';
import { TableModule } from 'primeng/table';
import { TieredMenuModule } from 'primeng/tieredmenu';
import { ToastModule } from 'primeng/toast';
@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    CardModule,
    SharedComponentsModule,
    CalendarModule,
    FormsModule,
    ButtonModule,
    AccordionModule,
    AdminModule,
    PublicModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ToastModule,
    TieredMenuModule,
    TableModule,
    SlideMenuModule,
    PanelModule,
    OverlayPanelModule,
    MenubarModule,
    FieldsetModule,
    DropdownModule,
    ConfirmDialogModule,
    DialogModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
