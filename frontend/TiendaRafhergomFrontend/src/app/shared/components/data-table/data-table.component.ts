import { FilterMetadata, FilterService, SortMeta } from 'primeng/api';
import { ColumnFilter, Table } from 'primeng/table';

import {
  AfterContentInit,
  Component,
  ContentChildren,
  EventEmitter,
  Input,
  OnInit,
  Output,
  QueryList,
  TemplateRef,
  ViewChild,
  ViewChildren,
  ViewEncapsulation,
} from '@angular/core';

import { PageableFilter } from 'src/app/shared/model';
import { DateRangeCustomFilterComponent } from './custom-filters/date-range-custom-filter/date-range-custom-filter.component';
import { SelectCustomFilterComponent } from './custom-filters/select-custom-filter/select-custom-filter.component';
import { DataTableColumn, DataTableType } from './data-table-column';
import { DataTableTemplateDirective } from './data-table-template.directive';
import { DataTableFilterUtilsService } from './data-table-filter-utils.service';
import { DataTableActionButton } from './data-table-action-button';
import moment from 'moment';

const RECORDS_PER_PAGE = 10;

@Component({
  selector: 'app-data-table',
  templateUrl: './data-table.component.html',
  styleUrls: ['./data-table.component.css'],
  encapsulation: ViewEncapsulation.None,
  providers: [DataTableFilterUtilsService],
})
export class DataTableComponent implements OnInit, AfterContentInit {
  @ViewChild(Table) pTable: Table;

  @ViewChildren(ColumnFilter) columnsFilter: QueryList<ColumnFilter>;

  @ViewChildren(DateRangeCustomFilterComponent)
  dateRangeCustomFilters: QueryList<DateRangeCustomFilterComponent>;

  @ViewChildren(SelectCustomFilterComponent)
  selectCustomFilters: QueryList<SelectCustomFilterComponent>;

  /**
   * Listado de valores a mostrar
   */
  @Input() value: any[] = [];

  /**
   * Indica si los datos de la tabla se están cargando.
   */
  @Input() loading: boolean;

  /**
   * Indica si los datos de la tabla se están exportando.
   */
  @Input() loadingExport: boolean;

  /**
   * Indica el modo de expansión de fila. Los posibles valores son 'single' y 'multiple'
   */
  @Input() rowExpandMode: string = 'single';

  /**
   * Indica si se permite expandir las filas de la tabla
   */
  @Input() rowExpandable: boolean = false;

  /**
   * Fila seleccionada en modo simple o un listado de valores en modo múltiple..
   */
  @Input() selected: any;

  /**
   * Definición de las columnas a mostrar
   */
  @Input() columns: DataTableColumn[];

  /**
   * Número de registros totales. Por defecto, longitud del listado de valores definidos.
   */
  @Input() totalRecords: number = 0;

  /**
   * Especifica el modo de selección, los valores válidos son "none", "single" y "multiple"
   */
  @Input() selectionMode: string = 'single';

  /**
   * Propiedad para identificar de forma única un registro en los datos. Por defecto, la propiedad es 'id'
   */
  @Input() dataKey: string = 'id';

  /**
   * Especifica el modo de ordenación, los valores válidos son "none", "single", y "multiple"
   */
  @Input() sortMode: string = 'multiple';

  /**
   * Indica si los datos, al ordenar o paginar, serán obtenidos desde una llamada API.
   * Por defecto, esta propiedad tiene valor 'false' en cuyo caso se debe establecer la propiedad (onLazyLoadData)
   */
  @Input() lazy: boolean = true;

  /**
   * Listado con los datos que no serán seleccionables. La comprobación se realizará a través de la propiedad 'dataKey'
   */
  @Input() unselectableRows: any[] = [];

  /**
   * Indica si se muestra (true) o no (false) la paginacíon de los datos. Por defecto, esta propiedad tiene el valor 'true'
   */
  @Input() paginator: boolean = true;

  /**
   * Indica si es posible aplicar filtros en las columnas (true) o no (false). Por defecto, esta propiedad tiene el valor 'true'
   */
  @Input() filterable: boolean = true;

  /**
   * Indica si se debe mostrar la opción de cambiar el tamaño de página
   */
  @Input() showRowsPerPageOptions: boolean = true;

  /**
   * Indica si los datos de la tabla pueden ser exportados
   */
  @Input() exportable: boolean = true;

  /**
   * Indica el nombre del fichero csv de exportación de datos cuando se realiza desde memoria
   */
  @Input() exportFilename: string = 'download';

  /**
   * Indica si el botón de refrescar datos está visible (true) o no (false). Por defecto, el valor es true
   */
  @Input() showRefreshData: boolean = true;

  @Input() loadingDetails: boolean;

  @Input() loadingEdit: boolean;

  @Input() loadingDelete: boolean;

  @Input() showDetailsButton: boolean = true;

  @Input() showEditButton: boolean = true;

  @Input() showDeleteButton: boolean = true;

  @Input() detailsButtonLabel: string = 'Details';

  @Input() editButtonLabel: string = 'Edit record';

  @Input() deleteButtonLabel: string = 'Remove record';

  @Input() enableDetailsButtonFn?: (selected: any) => boolean;

  @Input() enableEditButtonFn?: (selected: any) => boolean;

  @Input() enableDeleteButtonFn?: (selected: any) => boolean;

  @Input() rowStyleClassFn?: (row: any) => string | undefined;

  @Input() actionButtons: DataTableActionButton[] = [];

  /**
   * Evento que será llamado cuando se ordene o paginen los datos y la propiedad 'lazy' tenga valor 'true'.
   */
  @Output() onLazyLoadData: EventEmitter<any> = new EventEmitter();

  /**
   * Evento que será llamado cuando se modifique el elemento seleccionado
   */
  @Output() selectedChange: EventEmitter<any[]> = new EventEmitter<any[]>();

  /**
   * Evento que será llamado cuando se seleccione una fila
   */
  @Output() onRowSelect: EventEmitter<any> = new EventEmitter();

  /**
   * Evento que será llamado cuando se deseleccione una fila
   */
  @Output() onRowUnselect: EventEmitter<any> = new EventEmitter();

  /**
   * Evento que será llamado cuando se pulse el checkbox de seleccinar todo
   */
  @Output() onHeaderCheckboxToggle: EventEmitter<any> = new EventEmitter();

  /**
   * Evento que será llamado cuando se expanda una fila de la tabla
   */
  @Output() onRowExpand: EventEmitter<any> = new EventEmitter();

  /**
   * Evento que será llamado cuando se deseleccionen todos los registros seleccionados
   */
  @Output() onUnselectRows: EventEmitter<any> = new EventEmitter();

  /**
   * Evento que será llamado cuando se realice la exportación de datos
   */
  @Output() onExportData: EventEmitter<any> = new EventEmitter();

  /**
   * Evento que será llamado cuando se refresquen los datos y la tabla no sea lazy
   */
  @Output() onRefreshData: EventEmitter<any> = new EventEmitter();

  /**
   * Evento que será llamado cuando se pulse el botón de ver detalles de registro
   */
  @Output() onRowDetails: EventEmitter<any> = new EventEmitter();

  /**
   * Evento que será llamado cuando se pulse el botón de edición de registro
   */
  @Output() onRowEdit: EventEmitter<any> = new EventEmitter();

  /**
   * Evento que será llamado cuando se pulse el botón de borrado de registos
   */
  @Output() onDeleteRows: EventEmitter<any> = new EventEmitter();

  private _templateMap: Map<string, TemplateRef<any>> = new Map<
    string,
    TemplateRef<any>
  >();

  allRowsSelected: boolean = false;

  appliedFilters: boolean = false;

  @ContentChildren(DataTableTemplateDirective)
  _templates: QueryList<DataTableTemplateDirective>;

  pageableFilter: PageableFilter = {
    numPag: 1,
    pageSize: 10,
    fieldOrder: '',
  };

  showTooltipDelay = 500;

  constructor(
    private filterService: FilterService,
    private dataTableFilterUtilsService: DataTableFilterUtilsService
  ) {
    this.isRowSelectable = this.isRowSelectable.bind(this);
  }

  ngOnInit(): void {
    this.filterService.register(
      'dateRange',
      (value: any, filter: any): boolean => {
        if (!filter) {
          return true;
        }

        if (value === undefined || value === null) {
          return false;
        }

        if (!filter.dateFrom && filter.dateTo) {
          return moment(value).isBefore(moment(filter.dateTo));
        } else if (filter.dateFrom && !filter.dateTo) {
          return moment(value).isAfter(moment(filter.dateFrom));
        } else {
          return moment(value).isBetween(
            moment(filter.dateFrom),
            moment(filter.dateTo)
          );
        }
      }
    );

    this.filterService.register(
      'select',
      (value: any, filter: any): boolean => {
        if (!filter) {
          return true;
        }

        if (!value) {
          return false;
        }

        return value.toString() === filter;
      }
    );
  }

  ngAfterContentInit(): void {
    for (const template of this._templates.toArray()) {
      this._templateMap.set(template.name, template.template);
    }
    this.totalRecords = this.value ? this.value.length : 0;
  }

  getTemplateRef(name: string): any {
    return this._templateMap.get(name);
  }

  handleRefreshDataEvent() {
    if (this.lazy) {
      this.onLazyLoadData.emit(this.pageableFilter);
    } else {
      this.onRefreshData.emit();
    }
  }

  rowStyleClass(row: any): string | undefined {
    if (this.rowStyleClassFn) {
      return this.rowStyleClassFn(row);
    }
    return undefined;
  }

  isDetailsButtonEnabled(): boolean {
    if (this.loadingEdit && this.loadingDelete) {
      return false;
    }

    if (this.enableDetailsButtonFn) {
      return this.enableDetailsButtonFn(this.selected);
    }

    if (this.selected && this.selected.length === 1) {
      return true;
    }
    return false;
  }

  isEditButtonEnabled(): boolean {
    if (this.loadingDetails && this.loadingDelete) {
      return false;
    }

    if (this.enableEditButtonFn) {
      return this.enableEditButtonFn(this.selected);
    }

    if (this.selectionMode === 'multiple' && this.selected && this.selected.length === 1) {
      return true;
    }

    if (this.selectionMode === 'single' && this.selected && this.selected.length !== 0) {
      return true;
    }

    return false;
  }

  isDeleteButtonEnabled(): boolean {
    if (this.loadingDetails && this.loadingEdit) {
      return false;
    }
    if (this.enableDeleteButtonFn) {
      return this.enableDeleteButtonFn(this.selected);
    }

    if (this.selected && this.selected.length !== 0) {
      return true;
    }
    return false;
  }

  handleRowDetailsEvent() {
    if (this.isDetailsButtonEnabled()) {
      this.onRowDetails.emit(this.selected);
    }
  }

  handleRowEditEvent() {
    if (this.isEditButtonEnabled()) {
      this.onRowEdit.emit(this.selected);
    }
  }

  handleDeleteRowsEvent() {
    if (this.isDeleteButtonEnabled()) {
      this.onDeleteRows.emit(this.selected);
    }
  }

  handleDeleteFiltersEvent() {
    this.allRowsSelected = false;
    this.appliedFilters = false;
    if (this.columnsFilter) {
      this.columnsFilter.forEach((columnFilter) => {
        columnFilter.initFieldFilterConstraint();
      });
    }

    if (this.dateRangeCustomFilters) {
      this.dateRangeCustomFilters.forEach((dateRangeCustomFilter) => {
        dateRangeCustomFilter.initFieldFilterConstraint();
      });
    }

    if (this.selectCustomFilters) {
      this.selectCustomFilters.forEach((selectCustomFilter) => {
        selectCustomFilter.initFieldFilterConstraint();
      });
    }

    if (this.lazy) {
      this.dataTableFilterUtilsService.removeFilters(
        this.pageableFilter,
        this.columns
      );
      this.onLazyLoadData.emit(this.pageableFilter);
    } else {
      this.pTable?._filter();
    }
  }

  handleSortChangeEvent(event: any) {
    this.allRowsSelected = false;
    this.pageableFilter.fieldOrder = '';
    this.pageableFilter.numPag = 1;
    if (event.multisortmeta) {
      const fieldOrders: string[] = [];
      event.multisortmeta.forEach((element: SortMeta) => {
        fieldOrders.push((element.order === -1 ? '-' : '') + element.field);
      });
      this.pageableFilter.fieldOrder = fieldOrders.join(',');
    } else {
      this.pageableFilter.fieldOrder =
        (event.order === -1 ? '-' : '') + event.field;
    }
    this.onLazyLoadData.emit(this.pageableFilter);
  }

  handleFilterChangeEvent(event: any) {
    this.allRowsSelected = false;
    this.selected = [];
    this.dataTableFilterUtilsService.prepareFilters(
      this.pageableFilter,
      this.columns,
      event.filters
    );
    this.appliedFilters = this.columns.some((column: DataTableColumn) =>
      this.hasFieldFilterApplied(column.field)
    );
    this.onLazyLoadData.emit(this.pageableFilter);
  }

  private hasFieldFilterApplied(field: string): boolean {
    if (this.pTable?.filters) {
      let fieldFilter = this.pTable.filters[field];
      if (fieldFilter) {
        if (Array.isArray(fieldFilter)) {
          return !this.pTable.isFilterBlank(
            (<FilterMetadata[]>fieldFilter)[0].value
          );
        } else {
          return !this.pTable.isFilterBlank(fieldFilter.value);
        }
      }
    }

    return false;
  }

  existFilterableColumns(): boolean {
    return this.columns.some((column) => !column.filterableColumnDisabled);
  }

  handlePageChangeEvent(event: any) {
    this.allRowsSelected = false;
    this.pageableFilter.numPag = 1 + event.first / RECORDS_PER_PAGE;
    this.pageableFilter.pageSize = event.rows;
    this.onLazyLoadData.emit(this.pageableFilter);
  }

  handleSelectionChangeEvent(event: any) {
    this.selectedChange.emit(this.pTable.selection);
  }

  handleUnselectRowsEvent() {
    this.allRowsSelected = false;
    this.selected = [];
    this.selectedChange.emit(this.selected);
    this.onUnselectRows.emit();
  }

  isRowSelected(keyValue: string): boolean {
    if (!this.selected) {
      return false;
    }
    if (Array.isArray(this.selected)) {
      return this.selected.some((row) => row[this.dataKey] === keyValue);
    }
    return this.selected[this.dataKey] === keyValue;
  }

  isRowSelectable(event: any): boolean {
    if (this.unselectableRows) {
      return !this.unselectableRows.find(
        (unselectableRow) =>
          unselectableRow[this.dataKey] === event.data[this.dataKey]
      );
    }
    return true;
  }

  isUnselectableRow(item: any): boolean {
    return this.unselectableRows.some(
      (unselectableRow) => unselectableRow[this.dataKey] === item[this.dataKey]
    );
  }

  handleRowSelectEvent(event: any) {
    this.allRowsSelected = false;
    this.onRowSelect.emit(event);
  }

  handleRowUnselectEvent(event: any) {
    this.allRowsSelected = false;
    this.onRowUnselect.emit(event);
  }

  handleHeaderCheckboxToggleEvent(event: any) {
    this.onHeaderCheckboxToggle.emit(event);
  }

  handleRowExpandEvent(event: any) {
    this.onRowExpand.emit(event);
  }

  handleExportData() {
    if (this.lazy) {
      this.onExportData.emit({
        allRowsSelected: this.allRowsSelected,
        selectedRows: this.selected,
      });
    } else {
      this.pTable.exportCSV({ selectionOnly: !this.allRowsSelected });
    }
  }

  getItemValue(field: string, item: any): any {
    return field.split('.').reduce((p, prop) => {
      if (!p) {
        return undefined;
      }
      return p[prop];
    }, item);
  }

  isTextColumnType(column: DataTableColumn): boolean {
    return !column.type || DataTableType.Text === column.type;
  }

  isDateColumnType(column: DataTableColumn): boolean {
    return DataTableType.Date === column.type;
  }

  isSelectColumnType(column: DataTableColumn): boolean {
    return DataTableType.Select === column.type;
  }

  isBooleanColumnType(column: DataTableColumn): boolean {
    return DataTableType.Boolean === column.type;
  }

  isDateFilterVisible(column: DataTableColumn): boolean {
    return this.isDateColumnType(column);
  }

  isSelectFilterVisible(column: DataTableColumn): boolean {
    return (
      this.isSelectColumnType(column) && column.filterOptions !== undefined
    );
  }

  getFilterType(column: DataTableColumn): string {
    if (this.isDateColumnType(column)) {
      return 'date';
    }
    return 'text';
  }

  handleSelectAllRows() {
    this.allRowsSelected = !this.allRowsSelected;
  }

  isActionButtonsSeparatorVisible(): boolean {
    let visible = this.selectionMode !== 'none';
    visible =
      visible &&
      (this.showDetailsButton ||
        this.showEditButton ||
        this.showDeleteButton ||
        this.actionButtons.length > 0);
    visible =
      visible &&
      (this.exportable ||
        (this.filterable && this.existFilterableColumns()) ||
        this.showRefreshData ||
        this.selectedRowsInfo !== '');
    return visible;
  }

  get paginatorFirst(): number {
    if (history.state?.data?.numPag) {
      this.pageableFilter.numPag = history.state?.data?.numPag;
    }
    return (
      (this.pageableFilter.numPag > 0 ? this.pageableFilter.numPag - 1 : 0) *
      RECORDS_PER_PAGE
    );
  }

  get totalColumns(): number {
    return (
      this.columns.length +
      (this.selectionMode === 'multiple' ? 1 : 0) +
      (this.selectionMode === 'single' ? 1 : 0) +
      (this.rowExpandable ? 1 : 0)
    );
  }

  get numberRowsByPage(): number {
    return RECORDS_PER_PAGE;
  }

  get selectedRowsInfo(): string {
    if (Array.isArray(this.selected)) {
      if (!this.value || this.value.length === 0) {
        this.selected = [];
      }

      if (this.selected.length && !this.allRowsSelected) {
        return (
          'Seleccionados ' +
          this.selected.length +
          ' de ' +
          this.getTotalRecords() +
          ' registros'
        );
      } else if (this.selected.length && this.allRowsSelected) {
        return 'Seleccionados los ' + this.getTotalRecords() + ' registros';
      }
    }

    return '';
  }

  get selectAllRowsLabel(): string {
    return 'Seleccionar los ' + this.getTotalRecords() + '  registros';
  }

  private getTotalRecords() {
    return this.totalRecords ? this.totalRecords : this.pTable?.totalRecords;
  }
}
