import { Injectable } from '@angular/core';
import moment from 'moment';
import { PageableFilter } from 'src/app//shared/model';
import { DataTableColumn, DataTableType } from './data-table-column';

@Injectable()
export class DataTableFilterUtilsService {
  prepareFilters(
    pageableFilter: PageableFilter,
    columns: DataTableColumn[],
    appliedFilters: any
  ) {
    this.removeFilters(pageableFilter, columns);
    columns.forEach((column) => {
      if (
        appliedFilters &&
        appliedFilters[column.field] &&
        appliedFilters[column.field][0].value
      ) {
        switch (column.type) {
          case DataTableType.Date: {
            this.setFilterDateFromValue(
              pageableFilter,
              column.field,
              appliedFilters[column.field][0].value
            );
            this.setFilterDateToValue(
              pageableFilter,
              column.field,
              appliedFilters[column.field][0].value
            );
            break;
          }
          case DataTableType.Select: {
            pageableFilter[column.field + 'Id'] =
              appliedFilters[column.field][0].value;
            break;
          }
          default: {
            pageableFilter[column.field] =
              appliedFilters[column.field][0].value;
            break;
          }
        }
      }
    });
  }

  removeFilters(pageableFilter: PageableFilter, columns: DataTableColumn[]) {
    columns.forEach((column) => {
      delete pageableFilter[column.field];
      delete pageableFilter[column.field + 'From'];
      delete pageableFilter[column.field + 'To'];
      delete pageableFilter[column.field + 'Id'];
    });
  }

  private setFilterDateFromValue(
    pageableFilter: PageableFilter,
    field: string,
    filterValue: any
  ) {
    const filterFieldName = field + 'From';
    if (filterValue.dateFrom) {
      pageableFilter[filterFieldName] = moment(filterValue.dateFrom).format(
        'YYYY-MM-DDTHH:mm:ss'
      );
    }
  }

  private setFilterDateToValue(
    pageableFilter: PageableFilter,
    field: string,
    filterValue: any
  ) {
    const filterFieldName = field + 'To';
    if (filterValue.dateTo) {
      pageableFilter[filterFieldName] = moment(filterValue.dateTo).format(
        'YYYY-MM-DDTHH:mm:ss'
      );
    }
  }
}
