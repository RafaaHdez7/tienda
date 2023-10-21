import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { TooltipModule } from 'primeng/tooltip';

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CalendarModule } from 'primeng/calendar';
import { DividerModule } from 'primeng/divider';
import { DropdownModule } from 'primeng/dropdown';
import { CheckboxModule } from 'primeng/checkbox';
import { DateRangeCustomFilterComponent } from './custom-filters/date-range-custom-filter/date-range-custom-filter.component';
import { SelectCustomFilterComponent } from './custom-filters/select-custom-filter/select-custom-filter.component';
import { DataTableTemplateDirective } from './data-table-template.directive';
import { DataTableComponent } from './data-table.component';

@NgModule({
  imports: [
    CommonModule,
    TableModule,
    TooltipModule,
    ButtonModule,
    DropdownModule,
    CalendarModule,
    FormsModule,
    ReactiveFormsModule,
    DividerModule,
    CheckboxModule
  ],
  declarations: [
    DataTableComponent,
    DateRangeCustomFilterComponent,
    SelectCustomFilterComponent,
    DataTableTemplateDirective,
  ],
  exports: [DataTableComponent, DataTableTemplateDirective],
})
export class DataTableModule {}
