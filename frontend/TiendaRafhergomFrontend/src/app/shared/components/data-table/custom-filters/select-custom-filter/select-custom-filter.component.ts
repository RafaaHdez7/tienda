import { Component, Input } from '@angular/core';
import { FilterMetadata } from 'primeng/api';
import { Table } from 'primeng/table';

@Component({
  selector: 'app-select-custom-filter',
  templateUrl: './select-custom-filter.component.html',
})
export class SelectCustomFilterComponent {
  @Input() field: string;

  @Input() filterField?: string;

  @Input() filterLabel: string;

  @Input() filterOptions: any[] = [];

  @Input() badge: boolean;

  constructor(public dt: Table) {}

  selected: any;

  onModelChange(_: any) {
    const filterMetadata = this.dt.filters[this.field] as FilterMetadata[];
    if (this.selected) {
      filterMetadata[0].value =
        this.selected[this.filterField ? this.filterField : 'id'];
    }
  }

  initFieldFilterConstraint() {
    this.selected = null;
    this.dt.filters[this.field] = [
      { value: null, matchMode: 'select', operator: 'and' },
    ];
  }
}
