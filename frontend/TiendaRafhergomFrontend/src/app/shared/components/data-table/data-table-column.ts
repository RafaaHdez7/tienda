export enum DataTableType {
  Text = 'text',
  Date = 'date',
  Select = 'select',
  Boolean = 'boolean'
}

export enum DataTableFilterType {
  Text = 'text',
  DateRange = 'dateRange',
  Select = 'select',
}

export interface DataTableColumn {
  /**
   * Propiedad de la que se obtiene el valor
   */
  field: string;
  /**
   * Texto de la cabecera de la columna
   */
  header: string;

  /**
   * Anchura de la columna
   */
  headerWidth?: string;

  /**
   * Anchura máxima que puede alcanzar la celda
   */
  maxWidth?: string;

  /**
   * Tipo de columna. Por defecto DataTableType.Text
   */
  type?: DataTableType;

  /**
   * Indica si a la columna se le debe aplicar el estilo de badge
   */
  badge?: boolean;

  /**
   * Formato aplicado en caso de tratarse de una columna de tipo fecha
   */
  dateFormat?: string;

  /**
   * Indica si la ordenación por la columna está habilitada (false) o no (true)
   */
  sortableColumnDisabled?: boolean;

  /**
   * Propiedad por que se realiza la ordenación. Por defecto el mismo campo que 'field'
   */
  fieldOrder?: string;

  /**
   * Indica si el filtro por la columna está habilitada (false) o no (true)
   */
  filterableColumnDisabled?: boolean;

  filterField?: string;

  filterOptions?: any[];
}
