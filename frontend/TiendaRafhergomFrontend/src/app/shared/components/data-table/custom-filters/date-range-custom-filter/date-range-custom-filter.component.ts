import { Component, Input } from '@angular/core';
import moment from 'moment';
import { FilterMetadata } from 'primeng/api';
import { Table } from 'primeng/table';

@Component({
  selector: 'app-date-range-custom-filter',
  templateUrl: './date-range-custom-filter.component.html',
})
export class DateRangeCustomFilterComponent {
  @Input() field: string;

  constructor(public dt: Table) {}

  dateFrom: Date;

  dateTo: Date;

  onModelChange(_: any) {
    const filterMetadata = this.dt.filters[this.field] as FilterMetadata[];
    let fromMoment = null;
    if (this.dateFrom) {
      fromMoment = moment(this.dateFrom).startOf('day');
    }

    let toMoment = null;
    if (this.dateTo) {
      toMoment = moment(this.dateTo).endOf('day');
    }

    if (!fromMoment && !toMoment) {
      filterMetadata[0].value = null;
    } else {
      filterMetadata[0].value = {
        dateFrom: fromMoment ? fromMoment.toDate() : null,
        dateTo: toMoment ? toMoment.toDate() : null,
      };
    }
  }

  initFieldFilterConstraint() {
    this.dateFrom = null!;
    this.dateTo = null!;
    this.dt.filters[this.field] = [
      { value: null, matchMode: 'dateRange', operator: 'and' },
    ];
  }
}
